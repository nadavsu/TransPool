package api.loaders;

import data.generated.TransPool;

public class RouteLoader extends ClassLoader {
    public RouteLoader(TransPool data) {
        super(data);
    }

    public void load() {
        System.out.println("Loading route...");

    }
}
