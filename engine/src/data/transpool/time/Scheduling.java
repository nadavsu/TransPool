package data.transpool.time;

import exception.data.InvalidDayStartException;
import exception.data.TransPoolDataException;
import javafx.beans.property.*;

import java.time.LocalTime;
import java.util.Objects;

public class Scheduling {
    private ObjectProperty<TimeDay> departureTime;
    private ObjectProperty<Recurrence> recurrences;

    public Scheduling(int dayStart, LocalTime departureTime, Recurrence recurrences) throws TransPoolDataException {
        this.departureTime = new SimpleObjectProperty<>(new TimeDay(departureTime, dayStart));
        this.recurrences = new SimpleObjectProperty<>(recurrences);
    }

    public Scheduling(data.jaxb.Scheduling JAXBScheduling) throws TransPoolDataException {
        recurrences = new SimpleObjectProperty<>();
        LocalTime time = LocalTime.of(JAXBScheduling.getHourStart(), 0);
        departureTime = new SimpleObjectProperty<>(new TimeDay(time, JAXBScheduling.getDayStart()));

        setRecurrences(JAXBScheduling.getRecurrences());
    }

    public Scheduling(Scheduling other) {
        recurrences = new SimpleObjectProperty<>(other.getRecurrences());
        departureTime = new SimpleObjectProperty<>(new TimeDay(other.departureTime.get()));
    }

    private void setRecurrences(String recurrences) {
        switch (recurrences) {
            case "Daily":
                this.recurrences.set(Recurrence.DAILY);
                break;
            case "Bi-daily":
                this.recurrences.set(Recurrence.BI_DAILY);
                break;
            case "Weekly":
                this.recurrences.set(Recurrence.WEEKLY);
                break;
            case "Monthly":
                this.recurrences.set(Recurrence.MONTHLY);
                break;
            default:
                this.recurrences.set(Recurrence.ONE_TIME);
                break;
        }
    }

    public int getDayStart() {
        return departureTime.get().getDay();
    }

    public Recurrence getRecurrences() {
        return recurrences.get();
    }

    public ObjectProperty<Recurrence> recurrencesProperty() {
        return recurrences;
    }

    public TimeDay getDepartureTime() {
        return departureTime.get();
    }

    public ObjectProperty<TimeDay> departureTimeProperty() {
        return departureTime;
    }

    public void setDepartureTime(TimeDay departureTime) {
        this.departureTime.set(departureTime);
    }

    @Override
    public String toString() {
        return recurrences + " " + departureTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Scheduling)) return false;
        Scheduling that = (Scheduling) o;
        return Objects.equals(recurrences, that.recurrences) &&
                Objects.equals(departureTime, that.departureTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recurrences, departureTime);
    }
}
