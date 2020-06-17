package data.transpool.time;

public enum Recurrence {
    ONE_TIME("OneTime", 0),
    DAILY("Daily",1),
    BI_DAILY("Bi-daily", 2),
    WEEKLY("Weekly", 7),
    MONTHLY("Monthly", 30);

    private String name;
    private int value;

    Recurrence(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public boolean isOnDay(int day, int dayStart) {
        if (day < dayStart) {
            return false;
        } else if (this.equals(ONE_TIME)) {
            return day == dayStart;
        } else {
            return (day - dayStart) % value == 0;
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
