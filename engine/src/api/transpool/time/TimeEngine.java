package api.transpool.time;

import api.transpool.time.component.Updatable;
import api.transpool.time.component.TimeDay;
import api.transpool.time.component.TimeInterval;

public interface TimeEngine {
    void incrementTime(TimeInterval interval, Updatable updatable);
    void decrementTime(TimeInterval interval, Updatable updatable);
    void incrementTime(TimeInterval interval);
    void decrementTime(TimeInterval interval);
    TimeDay getCurrentTime();
}
