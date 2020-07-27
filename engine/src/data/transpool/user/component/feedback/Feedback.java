package data.transpool.user.component.feedback;

public class Feedback {
    private int feedbackerID;
    private String feedbackerName;
    private int rating;
    private String comment;

    public Feedback(int feedbackerID, String feedbackerName, int rating, String comment) {
        this.feedbackerID = feedbackerID;
        this.feedbackerName = feedbackerName;
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

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFeedbackerName() {
        return feedbackerName;
    }
    
    public void setFeedbackerName(String feedbackerName) {
        this.feedbackerName = feedbackerName;
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
