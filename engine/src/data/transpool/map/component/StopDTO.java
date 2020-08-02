package data.transpool.map.component;

public class StopDTO {
    private int x;
    private int y;
    private String stopName;

    public StopDTO(Stop stop) {
        this.x = stop.getX();
        this.y = stop.getY();
        this.stopName = stop.getName();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getStopName() {
        return stopName;
    }
}
