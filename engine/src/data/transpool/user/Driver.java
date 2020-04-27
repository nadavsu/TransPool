package data.transpool.user;

public class Driver implements Account {
    String name;

    public Driver(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Driver Name: '" + name;
    }
}
