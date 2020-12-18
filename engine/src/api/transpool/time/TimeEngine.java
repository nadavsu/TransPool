package api.transpool.time;

import api.transpool.time.component.Updatable;
import api.transpool.time.component.TimeDay;
import api.transpool.time.component.TimeInterval;

import java.util.List;

public interface TimeEngine {
    void incrementTime(TimeInterval interval, List<Updatable> updatables);
    void decrementTime(TimeInterval interval, List<Updatable> updatables);
    void incrementTime(TimeInterval interval);
    void decrementTime(TimeInterval interval);
    TimeDay getCurrentTime();
}
