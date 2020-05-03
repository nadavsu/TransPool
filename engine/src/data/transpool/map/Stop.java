package data.transpool.map;

public class Stop {
    private int x;
    private int y;
    private String name;

    public Stop(data.jaxb.Stop JAXBStop) {
        this.x = JAXBStop.getX();
        this.y = JAXBStop.getY();
        this.name = JAXBStop.getName();
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
}
