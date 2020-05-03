package data.transpool.trips;

import data.transpool.user.TransPoolRider;

public class TransPoolTripRequest extends Trip {
    private static int IDGenerator = 20000;
    private String source;
    private String destination;
    private Time timeOfDeparture;
    private boolean isContinuous;
    private boolean isMatched;

    public TransPoolTripRequest(String transpoolRider, String source, String destination, Time timeOfDeparture, boolean isContinuous) {
        this.ID = IDGenerator++;
        this.associatedAccount = new TransPoolRider(transpoolRider);
        this.source = source;
        this.destination = destination;
        this.timeOfDeparture = timeOfDeparture;
        this.isContinuous = isContinuous;
        this.isMatched = false;
    }

    public boolean isMatched() {
        return isMatched;
    }

    public TransPoolRider getTranspoolRider() {
        return (TransPoolRider) associatedAccount;
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

    public void setMatched(boolean isMatched) {
        this.isMatched = isMatched;
    }

    public String toString() {
        String requestString = "";
        if (!isMatched) {
            requestString += "------Trip Request------\n";
        }
        requestString += "Trip request ID: " + ID + "\n";
        requestString += "Rider name: " + associatedAccount + "\n";
        requestString += "Source: " + source + "\n";
        requestString += "Destination: " + destination + "\n";
        requestString += "Time of departure: " + timeOfDeparture + "\n";
        requestString += "Continuous ride? " + isContinuous + "\n";

        return requestString;
    }

    public String getDryInfoAsString() {
        String requestString = "";
        requestString += "Name of requester: " + associatedAccount + "\n";
        requestString += "Stop source: " + source + "\n";
        requestString += "Stop destination: " + destination + "\n";
        requestString += "Time of departure: " + timeOfDeparture + "\n";

        return requestString;
    }
}
