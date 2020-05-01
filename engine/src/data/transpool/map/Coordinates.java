package data.transpool.map;

import exceptions.data.CoordinatesOutOfBoundsException;

import java.util.Objects;

public class Coordinates {
    private int x;
    private int y;

    public Coordinates(int x, int y) {
        this.y = y;
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) throws CoordinatesOutOfBoundsException {
        this.x = x;
    }

    public void setY(int y) throws CoordinatesOutOfBoundsException {
        this.y = y;
    }

    public boolean isInBoundsOf(int width, int length) {
        return x <= width && y <= length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinates)) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x &&
                y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
