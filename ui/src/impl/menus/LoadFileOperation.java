package impl.menus;

import java.util.Scanner;

public class LoadFileOperation extends Operation {

    public LoadFileOperation(String title) {
        super(title);
    }

    @Override
    public void execute() {
        Scanner in = new Scanner(System.in);
        show();
        System.out.println("Enter your file address: ");
    }
}
