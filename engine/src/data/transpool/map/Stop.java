package data.transpool.map;

import javafx.util.Pair;

public class Stop {

    private Pair<Coordinates, String> stop;

    public Stop(Coordinates coordinates, String name) {
        stop = new Pair<>(coordinates, name);
    }

    public Pair<Coordinates, String> getStop() {
        return stop;
    }

    public void setStop(Coordinates coordinates, String name) {
        this.stop = new Pair<>(coordinates, name);
    }

    public void setStop(Pair<Coordinates, String> stop) {
        this.stop = stop;
    }

    public Coordinates getCoordinates() {
        return stop.getKey();
    }

    public String getName() {
        return stop.getValue();
    }

    @Override
    public String toString() {
        return "Stop{" +
                "stop=" + stop +
                '}';
    }
}
