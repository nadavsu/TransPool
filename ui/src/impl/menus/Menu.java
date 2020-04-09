package impl.menus;

public abstract class Menu {

    protected MenuTitle title;

    Menu(String title) {
        this.title = new MenuTitle(title);
    }

    public abstract void execute();
    public abstract void show();

}
