package api.menus;
import exceptions.data.StopNotFoundException;
import exceptions.data.time.InvalidHoursException;
import exceptions.data.time.InvalidMinutesException;
import exceptions.data.time.InvalidTimeException;

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
        String riderName;
        boolean isContinuous;

        int hour, min;
        boolean isValid;

        do {
            try {
                System.out.print("Enter your name: ");
                riderName = sc.nextLine();

                System.out.print("Enter your departure source: ");
                stopSource = sc.nextLine();

                System.out.print("Enter your arrival destination: ");
                stopDestination = sc.nextLine();

                System.out.print("Enter your departure time in the format HH MM: ");
                hour = sc.nextInt();
                min = sc.nextInt();
                validateTime(hour, min);

                System.out.println("Do you want your trip to be continuous? [y/n]: ");
                sc.next();
                isContinuous = sc.nextLine().toLowerCase().equals("y");

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
                sc.next();
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

