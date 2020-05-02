package data.transpool;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Timer;

public class TransPoolTripRequest implements TripRequest {
    private static int IDGenerator = 20000;
    private int ID;
    private String riderName;
    private String source;
    private String destination;
    private Time timeOfDeparture;
    private boolean isContinuous;

    public TransPoolTripRequest(String riderName, String source, String destination, Time timeOfDeparture, boolean isContinuous) {
        this.ID = IDGenerator++;
        this.riderName = riderName;
        this.source = source;
        this.destination = destination;
        this.timeOfDeparture = timeOfDeparture;
        this.isContinuous = isContinuous;
    }

    @Override
    public String getRequesterName() {
        return riderName;
    }

    @Override
    public String getSource() {
        return source;
    }

    @Override
    public String getDestination() {
        return destination;
    }

    @Override
    public Time getTimeOfDeparture() {
        return timeOfDeparture;
    }

    @Override
    public boolean isContinuous() {
        return isContinuous;
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public String toString() {
        String requestString = "";
        requestString += "------Trip Request------\n";
        requestString += "Trip request ID: " + ID + "\n";
        requestString += "Rider name: " + riderName + "\n";
        requestString += "Source: " + source + "\n";
        requestString += "Destination: " + destination + "\n";
        requestString += "Time of departure: " + timeOfDeparture + "\n";
        requestString += "Continuous ride? " + isContinuous + "\n";

        return requestString;
    }
}
