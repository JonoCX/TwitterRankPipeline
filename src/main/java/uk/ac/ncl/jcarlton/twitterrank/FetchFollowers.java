package uk.ac.ncl.jcarlton.twitterrank;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import uk.ac.ncl.jcarlton.util.Util;

import java.util.Map;

/**
 * Created by Jonathan on 19-Jul-16.
 */
public class FetchFollowers {

    /**
     * Fetch all users followers
     * Fetch an individuals follower list
     * Fetch a subset of followers (topics)
     */

    public static void fullSocialGraph() {
        Map<String, Integer> map = Util.fetchAllUsers();
        crawlForFollowers(map, "twitter-rank-pipeline", "socialgraph_full");
    }


    public static void topicalSocialGraph(int topic) {
        Map<String, Integer> map = Util.fetchAllUsers(topic);
        crawlForFollowers(map, "twitter-rank-pipeline", "socialgraph_topic_" + topic);
    }

    // TODO
    public static void individualUser(String userId) {}

    private static void crawlForFollowers(Map<String, Integer> map, String dbName,
                                         String collectionName, int... limit) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase(dbName);
        MongoCollection<BasicDBObject> collection = db.getCollection(collectionName, BasicDBObject.class);


    }
}
