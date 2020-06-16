package data.transpool.trip;

import exception.TransPoolRunTimeException;
import exception.data.InvalidDayStartException;
import exception.data.TransPoolDataException;
import javafx.beans.property.*;

import java.time.LocalTime;
import java.util.Objects;

public class Scheduling {

    private IntegerProperty dayStart;
    private ObjectProperty<Recurrence> recurrences;
    private ObjectProperty<LocalTime> departureTime;

    public Scheduling(int dayStart, LocalTime departureTime, Recurrence recurrences) throws TransPoolDataException {
        this.departureTime = new SimpleObjectProperty<>(departureTime);
        this.dayStart = new SimpleIntegerProperty();
        this.recurrences = new SimpleObjectProperty<>(recurrences);
        setDayStart(dayStart);
    }

    public Scheduling(data.jaxb.Scheduling JAXBScheduling) throws TransPoolDataException {
        dayStart = new SimpleIntegerProperty();
        recurrences = new SimpleObjectProperty<>();
        departureTime = new SimpleObjectProperty<>(LocalTime.of(JAXBScheduling.getHourStart(), 0));

        setDayStart(JAXBScheduling.getDayStart());
        setRecurrences(JAXBScheduling.getRecurrences());
    }

    public Scheduling(Scheduling other) {
        dayStart = new SimpleIntegerProperty(other.getDayStart());
        recurrences = new SimpleObjectProperty<>(other.getRecurrences());
        departureTime = new SimpleObjectProperty<>(LocalTime.of(
                other.getDepartureTime().getHour(),
                other.getDepartureTime().getMinute()
        ));
    }

    public int getDayStart() {
        return dayStart.get();
    }

    public IntegerProperty dayStartProperty() {
        return dayStart;
    }

    public void setDayStart(Integer dayStart) throws InvalidDayStartException {
        if (dayStart == null) {
            dayStart = 1;
        }
        if (dayStart < 1) {
            throw new InvalidDayStartException();
        } else {
            this.dayStart.set(dayStart);
        }
    }

    public Recurrence getRecurrences() {
        return recurrences.get();
    }

    public ObjectProperty<Recurrence> recurrencesProperty() {
        return recurrences;
    }

    public void setRecurrences(String recurrences) {
        if (recurrences.equals("Daily")) {
            this.recurrences.set(Recurrence.DAILY);
        } else if (recurrences.equals("Bi-daily")) {
            this.recurrences.set(Recurrence.BI_DAILY);
        } else if (recurrences.equals("Weekly")) {
            this.recurrences.set(Recurrence.WEEKLY);
        } else if (recurrences.equals("Monthly")) {
            this.recurrences.set(Recurrence.MONTHLY);
        } else {
            this.recurrences.set(Recurrence.ONE_TIME);
        }
    }

    public LocalTime getDepartureTime() {
        return departureTime.get();
    }

    public ObjectProperty<LocalTime> departureTimeProperty() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime.set(departureTime);
    }

    @Override
    public String toString() {
        return recurrences + " on day " + dayStart + " at time " + departureTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Scheduling)) return false;
        Scheduling that = (Scheduling) o;
        return Objects.equals(dayStart, that.dayStart) &&
                Objects.equals(recurrences, that.recurrences) &&
                Objects.equals(departureTime, that.departureTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dayStart, recurrences, departureTime);
    }
}
