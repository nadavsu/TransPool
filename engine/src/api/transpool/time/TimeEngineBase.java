package api.transpool.time;

import api.transpool.time.component.Updatable;
import api.transpool.time.component.TimeDay;
import api.transpool.time.component.TimeInterval;

import java.util.List;

public class TimeEngineBase implements TimeEngine {
    public static TimeDay currentTime = new TimeDay();

    public TimeEngineBase() {
    }

    @Override
    public void incrementTime(TimeInterval interval, List<Updatable> updatables) {
        currentTime.plus(interval.getMinutes());
        updatables.forEach(Updatable::update);
    }

    @Override
    public void decrementTime(TimeInterval interval, List<Updatable> updatables) {
        currentTime.minus(interval.getMinutes());
        updatables.forEach(Updatable::update);
    }

    @Override
    public void incrementTime(TimeInterval interval) {
        currentTime.plus(interval.getMinutes());
    }

    @Override
    public void decrementTime(TimeInterval interval) {
        currentTime.minus(interval.getMinutes());
    }

    @Override
    public TimeDay getCurrentTime() {
        return currentTime;
    }
}
