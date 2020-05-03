package api.menus;

import api.MatchingEngine;
import data.transpool.trips.TransPoolTripRequest;
import exceptions.NoMatchesFoundException;
import exceptions.QuitOnFinishException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FindAMatchMenu extends OptionedMenu {

    private MatchingEngine engine;

    public FindAMatchMenu(String title) {
        super(title);
        engine = new MatchingEngine();
    }

    @Override
    public void run() throws QuitOnFinishException {
        int maxMatches;
        int chosenOption;
        Scanner sc = new Scanner(System.in);
        List<TransPoolTripRequest> allRequests = new ArrayList<>(engine.getAllTransPoolTripRequests().getTranspoolTripRequests());
        for (TransPoolTripRequest request : allRequests) {
            this.addOption(new Option(request.getDryInfoAsString()));
        }

        show();
        chosenOption = getOptionFromUser();
        do {
            System.out.print("Enter the maximum matches to find: ");
            maxMatches = sc.nextInt();
            if (maxMatches <= 0) {
                System.out.println("The maximum number of matches needs to be bigger than 0.");
            }
        } while (maxMatches < 1);

        try {
            engine.findPossibleMatches(allRequests.get(chosenOption).getID(), maxMatches);
            OptionedMenu chooseAMatchMenu = new ChooseAMatchMenu("Here is a list of possible matches for your ride", engine);
            chooseAMatchMenu.run();
        } catch (NoMatchesFoundException e) {
            System.out.println(e.getMessage());
        }
    }

}
