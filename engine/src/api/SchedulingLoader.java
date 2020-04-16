package api;

import jaxb.generated.TransPool;

public class SchedulingLoader extends ClassLoader {
    public SchedulingLoader(TransPool data) {
        super(data);
    }

    public void load() {
        System.out.println("Loading schedule.");
    }
}
