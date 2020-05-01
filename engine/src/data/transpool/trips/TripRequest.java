package data.transpool.trips;

import data.transpool.map.Stop;
import data.transpool.user.Rider;

public class TripRequest {
    private static int IDGenerator = 100000;
    private int ID;
    private Rider rider;
    private Stop source;
    private Stop destination;
    private Time timeOfDeparture;

    public TripRequest(Rider rider, Stop source, Stop destination, Time timeOfDeparture) {
        this.ID = IDGenerator++;
        this.rider = rider;
        this.source = source;
        this.destination = destination;
        this.timeOfDeparture = timeOfDeparture;
    }

    public TripRequest(TripRequest other) {
        this.ID = other.getID();
        this.rider = other.getRider();
        this.source = other.source;
        this.destination = other.destination;
        this.timeOfDeparture = other.timeOfDeparture;
    }

    public int getID() { return ID; }

    public Rider getRider() {
        return rider;
    }

    public void setRider(Rider rider) {
        this.rider = rider;
    }

    public Stop getSource() {
        return source;
    }

    public void setSource(Stop source) {
        this.source = source;
    }

    public Stop getDestination() {
        return destination;
    }

    public void setDestination(Stop destination) {
        this.destination = destination;
    }

    public Time getTimeOfDeparture() {
        return timeOfDeparture;
    }
    public void setTimeOfDeparture(Time timeOfDeparture) {
        this.timeOfDeparture = timeOfDeparture;
    }

    @Override
    public String toString() {
        return "ID: " + ID +
                "\n" + rider +
                "\nSource: " + source +
                "\nDestination: " + destination +
                "\nTime of departure: " + timeOfDeparture;
    }
}
