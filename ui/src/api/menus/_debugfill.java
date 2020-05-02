package api.menus;

import exceptions.data.StopNotFoundException;
import exceptions.data.time.InvalidTimeException;

public class _debugfill extends InputMenu {
    _debugfill(String menuName) {
        super(menuName);
    }

    @Override
    public void run()  {
        try {
            engine._debugfill();
            System.out.println("filled successfully.");
        } catch (InvalidTimeException | StopNotFoundException e) {
            System.out.println("fix yo time nigga or yo stops");
        }
    }
}
