package data.transpool.trips;

public class Time {
    private int hours;
    private int mins;

    public Time(int hours, int mins) {
        this.hours = hours;
        this.mins = 0;
    }

    public int getHours() {
        return hours;
    }

    public int getMins() {
        return mins;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (hours < 10) {
            stringBuilder.append("0");
        }
        stringBuilder.append(hours + ":");
        if (mins < 10) {
            stringBuilder.append(0);
        }
        stringBuilder.append(mins);
        return stringBuilder.toString();
    }
}
