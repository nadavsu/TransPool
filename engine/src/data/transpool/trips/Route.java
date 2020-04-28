package data.transpool.trips;

public class Route {
    private String path;

    public Route(String path) {
        this.path = path;
    }

    public Route(data.jaxb.Route route) {
        path = route.getPath();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Route: " + path;
    }
}
