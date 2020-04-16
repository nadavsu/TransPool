package api.menus;
import api.Engine;
import exceptions.data.time.InvalidTimeException;
import validators.Validator;

import java.util.InputMismatchException;
import java.util.Scanner;

public class NewTripRequestMenu extends UnoptionedMenu {

    public NewTripRequestMenu(String title) {
        super(title);
    }


    @Override
    public void run() {
        show();
        Scanner sc = new Scanner(System.in);
        String stopSource, stopDestination;

        int hour, min;
        boolean isValid;

        System.out.print("Enter your departure source: ");
        stopSource = sc.nextLine();

        System.out.print("Enter your arrival destination: ");
        stopDestination = sc.nextLine();

        do {
            try {
                Validator validator = new Validator();

                System.out.print("Enter your departure time in the format HH MM: ");
                hour = sc.nextInt();
                min = sc.nextInt();
                validator.validateTime(hour, min);

                printCreationMessage(stopSource, stopDestination, hour, min);
                Engine.getInstance().createNewTripRequest(stopSource, stopDestination, hour, min);

                isValid = true;
                System.out.println("Trip created successfully!");
            } catch (InvalidTimeException e) {
                System.out.println(e.getMessage());
                isValid = false;
            } catch (InputMismatchException e) {
                System.out.println("Time must only contain numbers!");
                sc.next();
                isValid = false;
            } catch (Exception e) {
                System.out.println("Trip could not be created. Reason unknown.");
                isValid = false;
            }
        } while (!isValid);

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
            System.out.print("0" + min + "...\r\n");
        } else {
            System.out.print(min + "...\r\n");
        }
    }
}

