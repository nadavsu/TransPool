package data.transpool.trips;

import data.transpool.map.Stop;

public class TripRequest {
    private static int IDGenerator = 100000;
    private int ID;
    private Stop source;
    private Stop destination;
    private int hour;

    public TripRequest(Stop source, Stop destination, int hour) {
        this.ID = IDGenerator++;
        this.source = source;
        this.destination = destination;
        this.hour = hour;
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

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }
}
