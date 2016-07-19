package uk.ac.ncl.jcarlton.util;

/**
 * Created by Jonathan on 19-Jul-16.
 */

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
public class MongoLoad {

    private static MongoClient client;
    private static MongoDatabase db;

    private static  void setup() {
        client = new MongoClient();
        db = client.getDatabase("twitter-rank-pipeline");
    }

    public static void importJson(String json) {
        setup();
        Document doc = Document.parse(json);
        db.getCollection("tweets").insertOne(doc);
    }
}
