package api.two;

import api.controller.Controller;
import data.transpool.TransPoolData;

public abstract class Engine2 {
    protected TransPoolData data;
    protected Controller controller;

    public Engine2(Controller controller) {
        this.controller = controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
    public TransPoolData getData() {
        return data;
    }

    public Controller getController() {
        return controller;
    }
}
