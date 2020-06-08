package data.transpool.map;

import java.util.Objects;

public class Stop {
    private static int IDGenerator = 0;
    private int ID;
    private int x;
    private int y;
    private String name;

    public Stop(data.jaxb.Stop JAXBStop) {
        this.ID = IDGenerator++;
        this.x = JAXBStop.getX();
        this.y = JAXBStop.getY();
        this.name = JAXBStop.getName().trim();
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

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stop)) return false;
        Stop stop = (Stop) o;
        return ID == stop.ID &&
                x == stop.x &&
                y == stop.y &&
                name.equals(stop.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, x, y, name);
    }
}
