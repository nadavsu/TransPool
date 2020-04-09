package impl.menus;

public abstract class Operation extends Menu {

    Operation(String title) {
        super(title);
    }

    public void show() {
        title.execute();
    }
}
