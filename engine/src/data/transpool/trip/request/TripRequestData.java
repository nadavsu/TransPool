package data.transpool.trip.request;

import data.transpool.map.component.Stop;
import exception.data.InvalidDayStartException;
import exception.data.TransPoolDataException;
import javafx.beans.property.*;

import java.time.LocalTime;
import java.util.Objects;

public class TripRequestData extends BasicTripRequestData implements TripRequest {

    private IntegerProperty day;
    private SimpleObjectProperty<LocalTime> requestTime;
    private BooleanProperty isTimeOfArrival;
    private SimpleBooleanProperty isContinuous;

    public TripRequestData(String riderName, Stop sourceStop, Stop destinationStop, int day,
                           LocalTime requestTime, boolean isTimeOfArrival, boolean isContinuous) throws TransPoolDataException {
        super(riderName, sourceStop, destinationStop);
        this.day = new SimpleIntegerProperty();
        this.requestTime = new SimpleObjectProperty<>(requestTime);
        this.isTimeOfArrival = new SimpleBooleanProperty(isTimeOfArrival);
        this.isContinuous = new SimpleBooleanProperty(isContinuous);
        setDay(day);
    }


    @Override
    public boolean isTimeOfArrival() {
        return isTimeOfArrival.get();
    }

    @Override
    public LocalTime getRequestTime() {
        return requestTime.get();
    }

    @Override
    public int getDay() {
        return day.get();
    }

    @Override
    public IntegerProperty dayProperty() {
        return day;
    }

    private void setDay(int day) throws InvalidDayStartException {
        if (day < 1) {
            throw new InvalidDayStartException();
        }
        this.day.set(day);
    }

    @Override
    public boolean isContinuous() {
        return isContinuous.get();
    }

    @Override
    public SimpleBooleanProperty isContinuousProperty() {
        return isContinuous;
    }

    @Override
    public BooleanProperty isTimeOfArrivalProperty() {
        return isTimeOfArrival;
    }

    @Override
    public SimpleObjectProperty<LocalTime> requestTimeProperty() {
        return requestTime;
    }


    @Override
    public String toString() {
        return transpoolRider.get().getUsername() + " - " + transpoolRider.get().getID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TripRequestData)) return false;
        TripRequestData that = (TripRequestData) o;
        return requestID.get() == that.requestID.get();
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestID.get());
    }
}
