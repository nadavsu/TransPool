package data.transpool.map;

import java.util.Objects;

public class Path {
    private String source;
    private String destination;
    private boolean isOneWay;
    private int length;
    private double fuelConsumption;
    private int maxSpeed;

    public Path(data.jaxb.Path JAXBPath) {
        this.source = JAXBPath.getFrom().trim();
        this.destination = JAXBPath.getTo().trim();
        this.isOneWay = JAXBPath.isOneWay();
        this.length = JAXBPath.getLength();
        this.fuelConsumption = JAXBPath.getFuelConsumption();
        this.maxSpeed = JAXBPath.getSpeedLimit();
    }

    public Path(Path other) {
        this.source = other.source;
        this.destination = other.destination;
        this.isOneWay = other.isOneWay;
        this.length = other.length;
        this.fuelConsumption = other.fuelConsumption;
        this.maxSpeed = other.maxSpeed;
    }

    public void swapDirection() throws RuntimeException {
        if (isOneWay) {
            System.out.println("Aw. Tried to swap the direction of a one way path :(");
            throw new RuntimeException();
        }
        String temp;
        temp = source;
        source = destination;
        destination = temp;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public boolean isOneWay() {
        return isOneWay;
    }

    public int getLength() {
        return length;
    }

    public double getFuelConsumption() {
        return fuelConsumption;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public int getPathTime() {
        return (60 * length) / maxSpeed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Path)) return false;
        Path that = (Path) o;

        return ((this.source.equals(that.source)
                && this.destination.equals(that.destination)) ||
                (this.source.equals(that.destination) && this.destination.equals(that.source)));
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, destination);
    }
}
