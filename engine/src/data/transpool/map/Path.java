package data.transpool.map;

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
        source = JAXBPath.getFrom();
        destination = JAXBPath.getTo();
        length = JAXBPath.getLength();
        fuelConsumption = JAXBPath.getFuelConsumption();
        speedLimit = JAXBPath.getSpeedLimit();
        isOneWay = JAXBPath.isOneWay();
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelConsumption(int fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    public int getSpeedLimit() {
        return speedLimit;
    }

    public void setSpeedLimit(int speedLimit) {
        this.speedLimit = speedLimit;
    }

    public boolean isOneWay() {
        return isOneWay;
    }

    public void setOneWay(boolean oneWay) {
        isOneWay = oneWay;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
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
}
