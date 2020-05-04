package api.menu;

import api.MatchingEngine;
import data.transpool.trip.TransPoolTripRequest;
import exception.NoMatchesFoundException;
import exception.NoOptionsToShowException;
import exception.TransPoolFileNotLoadedException;

import java.util.ArrayList;
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

        //Getting the trip requests as options in the options list.
        List<TransPoolTripRequest> allRequests = new ArrayList<>(engine.getAllTransPoolTripRequests().getTranspoolTripRequests());
        createOptions(allRequests);
        showMenu();
        getOptionFromUser();
        int maxMatches = getMaxMatches();

        //Finding the possible matches by the chosen TP trip's ID.
        try {
            engine.findPossibleMatches(allRequests.get(chosenOption).getID(), maxMatches);

            //Creating a ChooseAMatchMenu menu to show the possible list of matches.
            OptionedMenu chooseAMatchMenu = new ChooseAMatchMenu("Here is a list of possible matches for your ride", engine);
            chooseAMatchMenu.run();
        } catch (NoMatchesFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            options.clear();
        }

    }

    private int getMaxMatches() {
        int maxMatches;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.print("Enter the maximum matches to find: ");
            maxMatches = sc.nextInt();
            if (maxMatches <= 0) {
                System.out.println("The maximum number of matches needs to be bigger than 0.");
            }
        } while (maxMatches < 1);
        return maxMatches;
    }

    private void createOptions(List<TransPoolTripRequest> allRequests) throws NoOptionsToShowException {
        for (TransPoolTripRequest request : allRequests) {
            this.addOption(new Option(request.toString()));
        }

        if (options.isEmpty()) {
            throw new NoOptionsToShowException();
        }
    }

}
