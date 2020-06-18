package data.transpool.time;

public class DepartureArrivalTime {
    private TimeDay departureTime;
    private TimeDay arrivalTime;

    public DepartureArrivalTime(TimeDay departureTime, TimeDay arrivalTime) {
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public TimeDay getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(TimeDay departureTime) {
        this.departureTime = departureTime;
    }

    public TimeDay getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(TimeDay arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
