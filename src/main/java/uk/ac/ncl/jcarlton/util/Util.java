package uk.ac.ncl.jcarlton.util;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for misc methods to perform certain actions
 *
 *
 * @author Jonathan Carlton
 */
public class Util {

    /**
     * Fetches all of the users (their ids) in the tweet data set
     * stored on the local mongo client. It also computes the
     * number of occurrences that a user has within the data set.
     * 
     * @return  map of user ids -> number of occurrences
     */
    public Map<String, Integer> fetchAllUsers() {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("twitter-rank-pipeline");
        MongoCollection collection = db.getCollection("tweets");

        Map<String, Integer> map = new HashMap<String, Integer>();
        FindIterable<Document> iterable = collection.find();
        iterable.forEach(new Block<Document>() {
            public void apply(Document document) {
                Document user = (Document) document.get("user");
                String id = user.get("id").toString();
                if (map.containsKey(id)) map.computeIfPresent(id, (k, v) -> v + 1);
                else map.put(id, 1);
            }
        });

        return map;
    }
}
