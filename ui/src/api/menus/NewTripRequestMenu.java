package api.menus;
import api.TripEngine;
import exceptions.StopNotFoundException;
import exceptions.TransPoolFileNotLoadedException;
import exceptions.time.InvalidHoursException;
import exceptions.time.InvalidMinutesException;
import exceptions.time.InvalidTimeException;

import java.util.InputMismatchException;

public class NewTripRequestMenu extends InputMenu {
    private TripEngine engine;
    public NewTripRequestMenu(String title) {
        super(title);
        engine = new TripEngine();
    }


    @Override
    public void run() throws TransPoolFileNotLoadedException {
        if (!engine.isFileLoaded()) {
            throw new TransPoolFileNotLoadedException();
        }

        show();
        String stopSource, stopDestination;
        String riderName;
        boolean isContinuous;

        int hour, min;
        boolean isValid;

        do {
            try {
                riderName = getStringFromUser("Enter your name: ");
                stopSource = getStringFromUser("Enter your departure source: ");
                stopDestination = getStringFromUser("Enter your arrival destination: ");
                hour = getIntegerFromUser("Enter your departure time in the format HH MM: ");
                min = sc.nextInt();
                validateTime(hour, min);
                isContinuous = getBooleanFromUser("Do you want your trip to be continuous? [y/n]: ");

                System.out.println("Creating trip request...");
                engine.createNewTransPoolTripRequest(riderName, stopSource, stopDestination, hour, min, isContinuous);

                isValid = true;
                System.out.println("Trip created successfully!");
            } catch (InvalidTimeException e) {
                System.out.println(e.getMessage());
                isValid = false;
                sc.nextLine();
            } catch (StopNotFoundException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
                isValid = false;
            } catch (InputMismatchException e) {
                System.out.println("Time must only contain numbers!");
                sc.nextLine();
                isValid = false;
            }
        } while (!isValid);

    }

    public void validateTime(int hour, int min) throws InvalidTimeException {
        if (hour < 0 || hour > 23) {
            throw new InvalidHoursException();
        }
        if (min < 0 || min > 60) {
            throw  new InvalidMinutesException();
        }
    }

}

