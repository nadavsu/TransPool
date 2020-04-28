package data.transpool.map;

import java.util.Objects;

public class Stop {

    private Coordinates coordinates;
    private String name;

    public Stop(Coordinates coordinates, String name) {
        this.coordinates = coordinates;
        this.name = name;
    }

    public Stop(data.jaxb.Stop JAXBStop) {
        coordinates = new Coordinates(JAXBStop.getX(), JAXBStop.getY());
        name = JAXBStop.getName();
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
