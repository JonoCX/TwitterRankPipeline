package uk.ac.ncl.pipeline;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import uk.ac.ncl.cc.classifier.Category;
import uk.ac.ncl.cc.classifier.Classifier;
import uk.ac.ncl.jcarlton.twitterrank.FetchFollowers;
import uk.ac.ncl.jcarlton.util.MongoLoad;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Jonathan Carlton
 * @version 1.0
 */
public class TwitterRankPipeline {
    public TwitterRankPipeline() {}

    public void start(String jsonFileName) {
        JSONArray jsonFile = readInJson(jsonFileName);

        // load into mongo
        MongoLoad ml = new MongoLoad();
        for (Object o : jsonFile) {
            JSONObject tweet = (JSONObject) o;
            JSONObject cTweet = classifyTweet(tweet);
            ml.importJson(cTweet);
        }
    }

    /**
     *
     * Note: This can take a significant amount of time
     * due to the limitations of the Twitter API (rate limits).
     */
    public void beginFollowerHarvesting() {
        FetchFollowers.fullSocialGraph();
    }

    /**
     * Note: This can take a significant amount of time
     * due to the limitations of the Twitter API (rate limits).
     * @param topic
     */
    public void beginFollowerHarvesting(int topic) {
        FetchFollowers.topicalSocialGraph(topic);
    }

    private JSONArray readInJson(String jsonFileName) {
        JSONParser parser = new JSONParser();
        JSONArray arr = null;
        try {
            arr = (JSONArray) parser.parse(new FileReader(jsonFileName));
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return arr;
    }

    private JSONObject classifyTweet(JSONObject tweet) {
        try {
            Classifier classifier = Classifier.getInstance();
            String tweetText = (String) tweet.get("text");
            Category category = classifier.classify(tweetText);

            JSONObject classification = new JSONObject();
            classification.put("id", category.getPrediction());
            classification.put("key", category.toString());
            classification.put("label", category.getKey());
            tweet.put("classification", classification);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tweet;
    }

    private void produceRankedList() {

    }
}
