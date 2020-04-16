package api;

import jaxb.generated.TransPool;

public class StopsLoader extends ClassLoader {
    public StopsLoader(TransPool data) {
        super(data);
    }

    public void load() {
        System.out.println("Loading stops...");
    }
}
