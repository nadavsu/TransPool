package api.menu;

import api.MatchingEngine;
import data.transpool.trip.TransPoolTripRequest;
import exception.NoMatchesFoundException;
import exception.NoOptionsToShowException;
import exception.TransPoolFileNotLoadedException;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * FindAMatch menu takes gets the TransPoolTripRequests list from the TransPoolData class and displays it to the user
 * as options in the OptionedMenu's options list.
 * The user then chooses an an option from the option in the console and the engine tries to find possible matches
 * for the chosen option.
 */
public class FindAMatchMenu extends OptionedMenu {

    private MatchingEngine engine;

    public FindAMatchMenu(String title) {
        super(title);
        engine = new MatchingEngine();
    }

    @Override
    public void run() throws TransPoolFileNotLoadedException {
        if (!engine.isFileLoaded()) {
            throw new TransPoolFileNotLoadedException();
        }

        List<TransPoolTripRequest> allRequests = new ArrayList<>(engine.getAllTransPoolTripRequests());
        for (TransPoolTripRequest request : allRequests) {
            this.addOption(new Option(request.toString()));
        }

        if (options.isEmpty()) {
            throw new NoOptionsToShowException();
        }

        show();
        getOptionFromUser();
        int maxMatches = getMaxMatches();

        //Finding the possible matches by the chosen TP trip's ID.
        try {
            engine.findPossibleMatches(allRequests.get(chosenOption).getID(), maxMatches);
            OptionedMenu chooseAMatchMenu = new ChooseAMatchMenu("Here is a list of possible matches for your ride", engine);
            chooseAMatchMenu.run();
        } catch (NoMatchesFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            options.clear();
        }

    }

    private int getMaxMatches() {
        int maxMatches = 0;
        boolean isValid;
        Scanner sc = new Scanner(System.in);
        do {
            try {
                System.out.print("Enter the maximum matches to find: ");
                maxMatches = sc.nextInt();
                if (maxMatches < 1) {
                    System.out.println("The number must be bigger than 0");
                    isValid = false;
                } else {
                    isValid = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("You must enter a number.");
                sc.nextLine();
                isValid = false;
            }
        } while (!isValid);
        return maxMatches;
    }

}
