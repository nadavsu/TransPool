package api.menus;
import api.exceptions.InvalidHoursException;
import api.exceptions.InvalidMinutesException;
import api.exceptions.InvalidTimeException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.Scanner;

public class NewTripRequestMenu extends UnoptionedMenu {

    public NewTripRequestMenu(String title) {
        super(title);
    }

    @Override
    public void execute() {
        title.show();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Scanner sc = new Scanner(System.in);
        String stopSource, stopDestination;

        int hour, min;
        boolean validTime;

        System.out.print("Enter your departure source: ");
        stopSource = sc.nextLine();

        System.out.print("Enter your arrival destination: ");
        stopDestination = sc.nextLine();

        do {
            try {
                System.out.print("Enter your departure time in the format HH MM: ");
                hour = sc.nextInt();
                min = sc.nextInt();
                isTimeValid(hour, min);

                printCreationMessage(stopSource, stopDestination, hour, min);
                //TODO: Send the data to the engine.
                validTime = true;
            } catch (InvalidTimeException e) {
                System.out.println(e);
                validTime = false;
            } catch (InputMismatchException e) {
                System.out.println("Time must only contain numbers!");
                sc.next();
                validTime = false;
            }
        } while (!validTime);

    }

    private void printCreationMessage(String stopSource, String stopDestination, int hour, int min) {
        System.out.print("Creating new trip request from " + stopSource + " to "
                + stopDestination + " at ");
        if (hour < 10) {
            System.out.print("0" + hour + ":");
        } else {
            System.out.print(hour + ":");
        }
        if (min < 10) {
            System.out.print("0" + min + "...");
        } else {
            System.out.print(min + "..." + "\r\n");
        }
    }

    private void isTimeValid(int hour, int min) throws InvalidTimeException {
        if (hour < 0 || hour >= 24) {
            throw new InvalidHoursException();
        }
        if (min < 0 || min >= 60) {
            throw new InvalidMinutesException();
        }
    }
}

