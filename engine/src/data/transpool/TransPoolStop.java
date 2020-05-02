package data.transpool;

public class TransPoolStop {
    private int x;
    private int y;
    private String name;

    public TransPoolStop(data.jaxb.Stop JAXBStop) {
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
