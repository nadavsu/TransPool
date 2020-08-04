package api.transpool.user.component.feedback;

public class Feedback {
    private int feedbackerID;
    private String feedbackerName;
    private int rating;
    private String comment;

    public Feedback(Feedbacker feedbacker, int rating, String comment) {
        this.feedbackerID = feedbacker.getID();
        this.feedbackerName = feedbacker.getUsername();
        this.rating = rating;
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }
    
    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public String getFeedbackerName() {
        return feedbackerName;
    }

    public int getFeedbackerID() {
        return feedbackerID;
    }
    

    public void setFeedbackerID(int feedbackerID) {
        this.feedbackerID = feedbackerID;
    }

    @Override
    public String toString() {
        return feedbackerName + "\n" +
                "Rating: " + getRating() + "\n" +
                "Comment: " + getComment();
    }
}
