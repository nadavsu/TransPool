package data.transpool.map.component;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.Objects;

public class Stop {
    private static int IDGenerator = 0;
    private int ID;
    private int x;
    private int y;
    private StringProperty name;

    public Stop(data.generated.Stop JAXBStop) {
        this.ID = IDGenerator++;
        this.x = JAXBStop.getX();
        this.y = JAXBStop.getY();
        this.name = new SimpleStringProperty(JAXBStop.getName().trim());
    }

    public Stop(Stop other) {
        this.ID = other.ID;
        this.x = other.x;
        this.y = other.y;
        this.name = new SimpleStringProperty(other.getName());
    }


    public int getID() {
        return ID;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public static void resetIDGenerator() {
        IDGenerator = 0;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stop)) return false;
        Stop stop = (Stop) o;
        return ID == stop.ID &&
                x == stop.x &&
                y == stop.y &&
                getName().equals(stop.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, x, y, getName());
    }
}
