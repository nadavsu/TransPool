package api.menus;

import api.Engine;
import data.transpool.TransPoolTripRequest;
import exceptions.QuitOnFinishException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FindAMatchMenu extends OptionedMenu {

    private Engine engine;

    public FindAMatchMenu(String title) {
        super(title);
        engine = new Engine();
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
        do {
            chosenOption = getOptionFromUser();
            System.out.print("Enter the maximum matches to find: ");
            maxMatches = sc.nextInt();
        } while (maxMatches < 1);

        engine.findPossibleMatches(allRequests.get(chosenOption - 1).getID(), maxMatches);
        OptionedMenu chooseAMatchMenu = new ChooseATripMenu("Choose your match", engine);
        chooseAMatchMenu.run();
    }
}
