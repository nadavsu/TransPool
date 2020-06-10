package api.components;

import data.transpool.TransPoolData;
import data.transpool.trip.request.MatchedTripRequest;
import data.transpool.user.TransPoolDriver;
import javafx.collections.ObservableList;

public class FeedbackEngine {
    private TransPoolData data;
    private MatchedTripRequest feedbacker;
    private ObservableList<String> matchedRequestDrivers;
    private TransPoolDriver feedbackee;

    public FeedbackEngine(TransPoolData data, int feedbackerID) {
        this.data = data;
        this.feedbacker = data.getMatchedTripRequest(feedbackerID);
        //this.matchedRequestDrivers = FXCollections.observableArrayList(feedbacker.getTransPoolDriverNames());
    }
}
