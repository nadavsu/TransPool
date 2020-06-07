package data.transpool.trip.request;

import javafx.beans.property.*;

import java.time.LocalTime;
import java.util.Objects;

public class TripRequestData extends BasicTripRequestData implements TripRequest {

    private BooleanProperty isTimeOfArrival;
    private SimpleObjectProperty<LocalTime> requestTime;
    private SimpleBooleanProperty isContinuous;

    public TripRequestData(String riderName, String sourceStop, String destinationStop,
                           LocalTime requestTime, boolean isTimeOfArrival, boolean isContinuous) {
        super(riderName, sourceStop, destinationStop);
        this.isTimeOfArrival = new SimpleBooleanProperty(isTimeOfArrival);
        this.requestTime = new SimpleObjectProperty<>(requestTime);
        this.isContinuous = new SimpleBooleanProperty(isContinuous);
    }


    @Override
    public boolean isTimeOfArrival() {
        return isTimeOfArrival.get();
    }

    @Override
    public void setIsTimeOfArrival(boolean isTimeOfArrival) {
        this.isTimeOfArrival.set(isTimeOfArrival);
    }

    @Override
    public LocalTime getRequestTime() {
        return requestTime.get();
    }

    @Override
    public void setRequestTime(LocalTime requestTime) {
        this.requestTime.set(requestTime);
    }

    @Override
    public boolean isContinuous() {
        return isContinuous.get();
    }

    @Override
    public void setContinuous(boolean isContinuous) {
        this.isContinuous.set(isContinuous);
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
