package api.menus;

import data.transpool.TransPoolTrip;
import data.transpool.TransPoolTripRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FindAMatchMenu extends OptionedMenu {
    public FindAMatchMenu(String title) {
        super(title);

    }

    @Override
    public void run() {
        int maxMatches;
        Scanner sc = new Scanner(System.in);
        List<TransPoolTripRequest> allRequests = new ArrayList<>(engine.getAllTransPoolTripRequests().getTranspoolTripRequests());

        for (TransPoolTripRequest request : allRequests) {
            this.addOption(new Option(request.getDryInfoAsString()));
        }

        show();

        do {
            int chosenOption = getOptionFromUser();

            System.out.print("Enter the maximum matches to find: ");
            maxMatches = sc.nextInt();

            List<TransPoolTrip> foundMatches = engine.findAMatch(allRequests.get(chosenOption - 1).getID(), maxMatches);

        } while (maxMatches > 1);
    }
}
