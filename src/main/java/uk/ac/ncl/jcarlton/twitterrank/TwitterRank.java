package uk.ac.ncl.jcarlton.twitterrank;

import java.util.List;
import java.util.Map;

/**
 * The class used to assigned a topic-specific rank to
 * a particular user within the data set, depending on
 * several metrics.
 *
 * @author Jonathan Carlton
 */
public class TwitterRank {

    private String collectionName;
    private int topic;

    private Map<Long, Integer> occurrenceMap;
    private Map<Long, Double> normOccMap;

    public TwitterRank(String collectionName, int topic) {
        this.collectionName = collectionName;
        this.topic = topic;
    }

    public void start() {

    }

    private Map<Long, Map<Long, Double>> getTransitionProbability() {
        return null;
    }

    private double getSim(long i, long j) {
        return 0.0;
    }

    private double getET(long i) {
        return 0.0;
    }

    private double getTRt(double gamma, long userID) {
        return 0.0;
    }

    private Map<Long, List<Long>> getRelationships(String fileName) {
        return null;
    }

    private Map<Long, Double> normalize() {
        return null;
    }

    /**
     * SUPPORTING METHODS
     */

    private Map<Long, Integer> getOccurrences() {
        return null;
    }

    private Map<Long, Integer> sort(Map<Long, Integer> unsorted) {
        return null;
    }
}
