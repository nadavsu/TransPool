package api;

import jaxb.generated.TransPool;

public class PathsLoader extends ClassLoader {
    public PathsLoader(TransPool data) {
        super(data);
    }

    public void load() {
        System.out.println("Loading paths...");
    }
}
