package data.transpool.time;

import java.time.Duration;

public interface TimeEngine {
    void incrementTime(Duration interval);
    void decrementTime(Duration interval);
    TimeDay getTimeDay();
}
