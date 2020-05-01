package data.transpool;

import exceptions.data.time.InvalidTimeException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransPoolTrip {
    private String owner;
    private int passengerCapacity;
    private List<String> route = new ArrayList<>();
    private int PPK;
    private Scheduling schedule;

    public TransPoolTrip(data.jaxb.TransPoolTrip JAXBTransPoolTrip) throws InvalidTimeException {
        this.owner = JAXBTransPoolTrip.getOwner();
        this.passengerCapacity = JAXBTransPoolTrip.getCapacity();
        this.PPK = JAXBTransPoolTrip.getPPK();
        this.schedule = new Scheduling(JAXBTransPoolTrip.getScheduling());
        initRouteList(JAXBTransPoolTrip);
    }

    private void initRouteList(data.jaxb.TransPoolTrip JAXBTransPoolTrip) {
        String[] routeArray = JAXBTransPoolTrip.getRoute().getPath().split(",");
        for (String str : routeArray) {
            route.add(str.trim());
        }
    }

    public String getOwner() {
        return owner;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public List<String> getRoute() {
        return route;
    }

    public int getPPK() {
        return PPK;
    }

    public Scheduling getSchedule() {
        return schedule;
    }
}
