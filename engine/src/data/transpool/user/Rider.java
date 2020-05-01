package data.transpool.user;

public class Rider implements Account {
    private static int IDGenerator = 300000;
    private int ID;
    private String name;

    public Rider(String name) {
        this.ID = IDGenerator++;
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Rider name: " + name;
    }
}
