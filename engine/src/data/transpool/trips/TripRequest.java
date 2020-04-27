package data.transpool.trips;

import data.transpool.map.Stop;

public class TripRequest {
    private static int IDGenerator = 100000;
    private int ID;
    private Stop source;
    private Stop destination;
    private Time timeOfDeparture;

    public TripRequest(Stop source, Stop destination, Time timeOfDeparture) {
        this.ID = IDGenerator++;
        this.source = source;
        this.destination = destination;
        this.timeOfDeparture = timeOfDeparture;
    }

    public int getID() { return ID; }

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
                "\nSource: " + source +
                "\nDestination: " + destination +
                "\nTime of departure: " + timeOfDeparture;
    }
}
