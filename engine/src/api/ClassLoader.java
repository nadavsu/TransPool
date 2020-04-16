package api;

import jaxb.generated.TransPool;

import java.util.ArrayList;
import java.util.List;

public abstract class ClassLoader implements Loader {
    protected List<Loader> classesToLoad;
    protected TransPool data;

    public ClassLoader(TransPool data) {
        this.data = data;
        classesToLoad = new ArrayList<>();

    }

    @Override
    public void load() {
        try {
            classesToLoad.forEach(Loader::load);
        } catch (NullPointerException e) {
            System.out.println("Null pointer.");
        }
    }
}
