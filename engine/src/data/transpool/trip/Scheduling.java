package data.transpool.trip;

import exception.time.InvalidTimeException;
import javafx.beans.property.*;

import javax.xml.bind.JAXB;
import java.time.LocalTime;

public class Scheduling {
    private IntegerProperty day;
    private StringProperty recurrences;
    private ObjectProperty<LocalTime> time;

    public Scheduling(data.jaxb.Scheduling JAXBScheduling) {
        day = new SimpleIntegerProperty();
        recurrences = new SimpleStringProperty();
        time = new SimpleObjectProperty<>(LocalTime.of(JAXBScheduling.getHourStart(), 0));

        setDay(JAXBScheduling.getDayStart());
        setRecurrences(JAXBScheduling.getRecurrences());
    }

    public int getDay() {
        return day.get();
    }

    public IntegerProperty dayProperty() {
        return day;
    }

    public void setDay(Integer day) {
        if (day == null || day == 0) {
            this.day.setValue(1);
        } else {
            this.day.set(day);
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

    public LocalTime getTime() {
        return time.get();
    }

    public ObjectProperty<LocalTime> timeProperty() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time.set(time);
    }

    @Override
    public String toString() {
        return recurrences + " on day " + day + " at time " + time;
    }
}
