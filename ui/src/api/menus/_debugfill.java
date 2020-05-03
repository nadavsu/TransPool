package api.menus;

import exceptions.data.StopNotFoundException;
import exceptions.data.TransPoolDataException;
import exceptions.data.time.InvalidTimeException;
import exceptions.file.TransPoolFileNotFoundException;

import javax.xml.bind.JAXBException;

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
        } catch (TransPoolDataException | JAXBException | TransPoolFileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
