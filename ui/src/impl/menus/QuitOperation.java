package impl.menus;

public class QuitOperation extends Operation {

    public QuitOperation(String title) {
        super(title);
    }

    @Override
    public void execute() {
        System.out.println("Thank you for using Transpool!");
    }
}
