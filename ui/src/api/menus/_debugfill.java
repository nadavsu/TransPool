package api.menus;

import api.FileEngine;
import api.TripEngine;
import exceptions.StopNotFoundException;
import exceptions.file.TransPoolFileNotFoundException;
import exceptions.file.TransPoolFileDataException;
import exceptions.time.InvalidTimeException;

import javax.xml.bind.JAXBException;

public class _debugfill extends InputMenu {
    private TripEngine tripEngine;
    private FileEngine fileEngine;

    _debugfill(String menuName) {
        super(menuName);
        tripEngine = new TripEngine();
        fileEngine = new FileEngine("ex1-real.xml");
    }

    @Override
    public void run()  {
        try {
            fileEngine.loadFile();
            tripEngine._debugfill();
            System.out.println("filled successfully.");
        } catch (InvalidTimeException | StopNotFoundException e) {
            System.out.println("fix yo time nigga or yo stops");
        } catch (TransPoolFileDataException | JAXBException | TransPoolFileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
