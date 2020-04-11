package trips;

public class Time {
    private int hours;
    private int mins;

    public Time(int hours, int mins) {      //TODO: round the time to the nearest 5 minutes here.
        this.hours = hours;
        this.mins = mins;
    }

    public int getHours() {
        return hours;
    }

    public int getMins() {
        return mins;
    }
}
