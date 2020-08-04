package api.transpool.time.component;


/**
 * An enum holding the recurrences with their names and values.
 */
public enum Recurrence {
    ONE_TIME("One time", 0),
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

    public static Recurrence getRecurrence(String recurrence) {
        switch (recurrence) {
            case "Daily" :
                return DAILY;
            case "Bi-daily":
                return BI_DAILY;
            case "Weekly":
                return WEEKLY;
            case "Monthly":
                return MONTHLY;
            default:
                return ONE_TIME;
        }
    }

    public int getValue() {
        return value;
    }

    /**
     * Checks if a recurrence is happening on a specific day.
     * @param day - The day to check if the recurrence is happening
     * @param dayStart - The offset of the recurrence - When the schedule has started.
     * @return
     */
    public boolean isOnDay(int day, int dayStart) {
        if (day < dayStart) {
            return false;
        } else if (this.equals(ONE_TIME)) {
            return day == dayStart;
        } else {
            return (day - dayStart) % value == 0;
        }
    }

    public int getNextRecurrenceDay(int day, Recurrence recurrenceType) {
        return day += recurrenceType.getValue();
    }

    /**
     * Gets the name of the Recurrence
     * @return - the name of the enum as a string.
     */
    @Override
    public String toString() {
        return name;
    }
}
