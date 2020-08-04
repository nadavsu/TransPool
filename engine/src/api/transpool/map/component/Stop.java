package api.transpool.map.component;

import java.util.Objects;

public class Stop {
    private static int IDGenerator = 0;
    private int ID;
    private int x;
    private int y;
    private String name;

    public Stop(api.generated.Stop JAXBStop) {
        this.ID = IDGenerator++;
        this.x = JAXBStop.getX();
        this.y = JAXBStop.getY();
        this.name = JAXBStop.getName().trim();
    }

    public Stop(Stop other) {
        this.ID = other.ID;
        this.x = other.x;
        this.y = other.y;
        this.name = other.name;
    }


    public int getID() {
        return ID;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public StopDTO getDetails() {
        return new StopDTO(this);
    }

    public static void resetIDGenerator() {
        IDGenerator = 0;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stop)) return false;
        Stop stop = (Stop) o;
        return ID == stop.ID &&
                x == stop.x &&
                y == stop.y &&
                getName().equals(stop.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, x, y, getName());
    }
}
