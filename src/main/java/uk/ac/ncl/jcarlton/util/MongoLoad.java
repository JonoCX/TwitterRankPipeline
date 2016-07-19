package uk.ac.ncl.jcarlton.util;

/**
 * Created by Jonathan on 19-Jul-16.
 */

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.simple.JSONObject;


public class MongoLoad {

    private static MongoClient client;
    private static MongoDatabase db;


    public MongoLoad() {
        client = new MongoClient();
        db = client.getDatabase("twitter-rank-pipeline");
    }

    /**
     * Import a single instance of json into the local
     * mongo db collection
     * @param json  single instance of a tweet
     */
    public void importJson(JSONObject json) {
        Document doc = Document.parse(json.toString());
        db.getCollection("tweets-processed").insertOne(doc);
    }

    /**
     * Import a single classified instance of json into
     * the processed mongo db collection
     * @param json  single instance of a classified tweet
     */
    public void importJsonClassified(JSONObject json) {
        Document doc = Document.parse(json.toString());
        db.getCollection("tweets-processed").insertOne(doc);
    }
}
