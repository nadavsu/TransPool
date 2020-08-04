package api.transpool.trip.request.component;

import api.transpool.time.component.TimeDay;

public interface TripRequest extends BasicTripRequest {
    TripRequestDTO getDetails();
    TimeDay getRequestTime();
    boolean isTimeOfArrival();
    boolean isContinuous();
}
