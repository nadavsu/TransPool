package data.transpool.map.component;

public class PathDTO {

    //Source coordinates
    private int x1;
    private int y1;
    private String sourceName;

    //Destination coordinates
    private int x2;
    private int y2;
    private String destinationName;

    public PathDTO(Path path) {
        this.x1 = path.getSourceStop().getX();
        this.y1 = path.getSourceStop().getY();
        this.sourceName = path.getSourceName();

        this.x2 = path.getDestinationStop().getX();
        this.y2 = path.getDestinationStop().getY();
        this.destinationName = path.getDestinationName();
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public String getSourceName() {
        return sourceName;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    public String getDestinationName() {
        return destinationName;
    }
}
