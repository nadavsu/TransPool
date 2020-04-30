package data.transpool.map;

import java.util.Objects;

public class Path {
    private String source;
    private String destination;
    private boolean isOneWay;
    private int length;
    private int fuelConsumption;
    private int speedLimit;

    public Path(String source, String destination, int length, boolean isOneWay, int fuelConsumption, int speedLimit) {
        this.length = length;
        this.fuelConsumption = fuelConsumption;
        this.speedLimit = speedLimit;
        this.isOneWay = isOneWay;
        this.source = source;
        this.destination = destination;
    }

    public Path(data.jaxb.Path JAXBPath) {
        this.source = JAXBPath.getFrom();
        this.destination = JAXBPath.getTo();
        this.length = JAXBPath.getLength();
        this.fuelConsumption = JAXBPath.getFuelConsumption();
        this.speedLimit = JAXBPath.getSpeedLimit();
        this.isOneWay = JAXBPath.isOneWay();
    }

    public Path(Path other) {
       this.source = other.source;
       this.destination = other.destination;
       this.length = other.length;
       this.fuelConsumption = other.fuelConsumption;
       this.speedLimit = other.speedLimit;
       this.isOneWay = other.isOneWay;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public int getLength() {
        return length;
    }

    public boolean isOneWay() {
        return isOneWay;
    }

    public int getFuelConsumption() {
        return fuelConsumption;
    }

    public int getSpeedLimit() {
        return speedLimit;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setFuelConsumption(int fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setSpeedLimit(int speedLimit) {
        this.speedLimit = speedLimit;
    }

    public void setOneWay(boolean oneWay) {
        isOneWay = oneWay;
    }

    @Override
    public String toString() {
        return "Path{" +
                "length=" + length +
                ", fuelConsumption=" + fuelConsumption +
                ", speedLimit=" + speedLimit +
                ", isOneWay=" + isOneWay +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Path)) return false;
        Path path = (Path) o;
        return isOneWay == path.isOneWay &&
                length == path.length &&
                fuelConsumption == path.fuelConsumption &&
                speedLimit == path.speedLimit &&
                source.equals(path.source) &&
                destination.equals(path.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, destination);
    }
}
