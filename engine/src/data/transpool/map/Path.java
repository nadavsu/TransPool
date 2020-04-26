package data.transpool.map;

public class Path {
    private int length;
    private int fuelConsumption;
    private int speedLimit;
    private boolean isOneWay;
    private String source;
    private String destination;

    public Path(int length, int fuelConsumption, int speedLimit, boolean isOneWay, String source, String destination) {
        this.length = length;
        this.fuelConsumption = fuelConsumption;
        this.speedLimit = speedLimit;
        this.isOneWay = isOneWay;
        this.source = source;
        this.destination = destination;
    }

    public Path(data.generated.Path JAXBPath) {
        length = JAXBPath.getLength();
        fuelConsumption = JAXBPath.getFuelConsumption();
        speedLimit = JAXBPath.getSpeedLimit();
        isOneWay = JAXBPath.isOneWay();
        source = JAXBPath.getFrom();
        destination = JAXBPath.getTo();
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

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
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
