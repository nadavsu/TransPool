package data.transpool.map;

import java.util.Objects;

public class Stop {

    private static int IDGenerator = 0;
    private int ID;
    private Coordinates coordinates;
    private String name;

    public Stop(Coordinates coordinates, String name) {
        this.ID = IDGenerator++;
        this.coordinates = coordinates;
        this.name = name;
    }

    public Stop(data.jaxb.Stop JAXBStop) {
        ID = IDGenerator++;
        coordinates = new Coordinates(JAXBStop.getX(), JAXBStop.getY());
        name = JAXBStop.getName();
    }

    public int getID() {
        return ID;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public String getName() {
        return name;
    }

    public boolean isInBoundsOf(int width, int length) {
        return coordinates.isInBoundsOf(width, length);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stop stop = (Stop) o;
        return name.equals(stop.name) || coordinates.equals(stop.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
