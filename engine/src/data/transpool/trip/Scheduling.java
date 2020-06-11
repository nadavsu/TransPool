package data.transpool.trip;

import exception.data.InvalidDayStartException;
import exception.data.TransPoolDataException;
import javafx.beans.property.*;

import java.time.LocalTime;

public class Scheduling {

    private IntegerProperty dayStart;
    private StringProperty recurrences;
    private ObjectProperty<LocalTime> departureTime;

    public Scheduling(int dayStart, LocalTime departureTime, String recurrences) throws TransPoolDataException {
        this.departureTime = new SimpleObjectProperty<>(departureTime);
        this.dayStart = new SimpleIntegerProperty();
        this.recurrences = new SimpleStringProperty();

        setDayStart(dayStart);
        setRecurrences(recurrences);
    }

    public Scheduling(data.jaxb.Scheduling JAXBScheduling) throws TransPoolDataException {
        dayStart = new SimpleIntegerProperty();
        recurrences = new SimpleStringProperty();
        departureTime = new SimpleObjectProperty<>(LocalTime.of(JAXBScheduling.getHourStart(), 0));

        setDayStart(JAXBScheduling.getDayStart());
        setRecurrences(JAXBScheduling.getRecurrences());
    }

    public Scheduling(Scheduling other) {
        dayStart = new SimpleIntegerProperty(other.getDayStart());
        recurrences = new SimpleStringProperty(other.getRecurrences());
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

    public String getRecurrences() {
        return recurrences.get();
    }

    public StringProperty recurrencesProperty() {
        return recurrences;
    }

    public void setRecurrences(String recurrences) {
        if (recurrences == null || recurrences.equals("")) {
            this.recurrences.set("One time");
        } else {
            this.recurrences.set(recurrences);
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
}
