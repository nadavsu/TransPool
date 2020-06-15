package data.transpool.map.component;

import java.time.LocalTime;

public class RouteStop extends Stop {
    private LocalTime timeAtStop;

    public RouteStop(data.jaxb.Stop JAXBStop, LocalTime timeAtStop) {
        super(JAXBStop);
        this.timeAtStop = timeAtStop;
    }

    public RouteStop(Stop other, LocalTime timeAtStop) {
        super(other);
        this.timeAtStop = timeAtStop;
    }

    public LocalTime getTimeAtStop() {
        return timeAtStop;
    }
}
