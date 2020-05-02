package data.transpool;

public class TransPoolTripRequest {
    private static int IDGenerator = 20000;
    private int ID;
    private String requesterName;
    private String source;
    private String destination;
    private Time timeOfDeparture;
    private boolean isContinuous;
    private boolean isMatched;

    public TransPoolTripRequest(String requesterName, String source, String destination, Time timeOfDeparture, boolean isContinuous) {
        this.ID = IDGenerator++;
        this.requesterName = requesterName;
        this.source = source;
        this.destination = destination;
        this.timeOfDeparture = timeOfDeparture;
        this.isContinuous = isContinuous;
        this.isMatched = false;
    }

    public boolean isMatched() {
        return isMatched;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public Time getTimeOfDeparture() {
        return timeOfDeparture;
    }

    public boolean isContinuous() {
        return isContinuous;
    }

    public int getID() {
        return ID;
    }

    public String toString() {
        String requestString = "";
        if (!isMatched) {
            requestString += "------Trip Request------\n";
        }
        requestString += "Trip request ID: " + ID + "\n";
        requestString += "Rider name: " + requesterName + "\n";
        requestString += "Source: " + source + "\n";
        requestString += "Destination: " + destination + "\n";
        requestString += "Time of departure: " + timeOfDeparture + "\n";
        requestString += "Continuous ride? " + isContinuous + "\n";

        return requestString;
    }

    public String getDryInfoAsString() {
        String requestString = "";
        requestString += "Name of requester: " + requesterName + "\n";
        requestString += "Stop source: " + source + "\n";
        requestString += "Stop destination: " + destination + "\n";
        requestString += "Time of departure: " + timeOfDeparture + "\n";

        return requestString;
    }
}
