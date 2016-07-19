package uk.ac.ncl.jcarlton.twitterrank;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import twitter4j.IDs;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import uk.ac.ncl.jcarlton.util.Util;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jonathan on 19-Jul-16.
 */
public class FetchFollowers {


    public static void fullSocialGraph() {
        Map<String, Integer> map = Util.fetchAllUsers();
        crawlForFollowers(map, "twitter-rank-pipeline", "socialgraph_full", 0);
    }


    public static void topicalSocialGraph(int topic) {
        Map<String, Integer> map = Util.fetchAllUsers(topic);
        crawlForFollowers(map, "twitter-rank-pipeline", "socialgraph_topic_" + topic, topic);
    }

    // TODO
    public static void individualUser(String userId) {}

    private static void crawlForFollowers(Map<String, Integer> map, String dbName,
                                         String collectionName, int classiferId, int... limit) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase(dbName);
        MongoCollection<BasicDBObject> collection = db.getCollection(collectionName, BasicDBObject.class);

        Twitter twitter = TwitterSetup.getInstance();

        int counter = 0;
        for (Map.Entry<String, Integer> m : map.entrySet()) {
            if (counter == limit[0]) break;
            else {
                IDs ids;
                long cursor = -1;
                long currentId = Long.parseLong(m.getKey());
                BasicDBList followers = new BasicDBList();

                try {
                    do {
                        ids = twitter.getFollowersIDs(currentId, cursor);
                        for (Object f_id : ids.getIDs())
                            followers.add(f_id);
                    } while ((cursor = ids.getNextCursor()) != 0);

                    BasicDBObject dbObject = new BasicDBObject();
                    dbObject.put("user_id", currentId);
                    dbObject.put("occurrences_in_dataset", m.getValue());

                    if (classiferId == 0) dbObject.put("classifier_id", Util.fetchClassification(currentId, client));
                    else dbObject.put("classifier_id", classiferId);

                    dbObject.put("followers", followers);
                    collection.insertOne(dbObject);
                    counter++;
                } catch (TwitterException e) {
                    if (e.getErrorCode() == 88) {
                        System.out.println("----- Thread Sleep -----");
                        try {
                            TimeUnit.MINUTES.sleep(15);
                        } catch (InterruptedException ie) {
                            System.out.println("----- Interrupted Sleep -----");
                            ie.printStackTrace();
                        }
                        continue;
                    } else if (e.getErrorCode() == 34) {
                        System.out.println("------ User doesn't exist / Private account ------");
                        continue;
                    } else {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
