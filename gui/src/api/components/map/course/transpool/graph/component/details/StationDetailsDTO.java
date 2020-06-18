package api.components.map.course.transpool.graph.component.details;

import data.transpool.user.TransPoolDriver;

import java.util.List;

/*
Dummy container to hold information needed to be shown upon clicking a station
 */
public class StationDetailsDTO {

    private String name;
    private int x;
    private int y;
    private List<TransPoolDriver> drives;

    public StationDetailsDTO(List<TransPoolDriver> drives) {
        this.drives = drives;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<TransPoolDriver> getDrives() {
        return drives;
    }

    public void addDriver(TransPoolDriver driver) {
        drives.add(driver);
    }

    public void clear() {
        drives.clear();
    }
}
