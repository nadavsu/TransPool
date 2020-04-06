package trips;//TODO: Add setHours and setMins input validation? Maybe do it in user input class.

public class Time {
    private int hours;
    private int mins;

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMins() {
        return mins;
    }

    public void setMins(int mins) {
        this.mins = mins;
    }
}
