package api.menus;

public class Option extends Menu {

    Option(String menuName) {
        super(menuName);
    }

    @Override
    public void run() {
        show();
    }

    @Override
    public void show() {
        System.out.println(menuName);
    }
}
