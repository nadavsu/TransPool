package api.menus;

import api.ConsoleUserInterface;
import api.MatchingEngine;
import data.transpool.trips.TransPoolTrip;

public class ChooseAMatchMenu extends OptionedMenu {

    private MatchingEngine engine;

    ChooseAMatchMenu(String menuName, MatchingEngine engine) {
        super(menuName);
        this.engine = engine;
        for (TransPoolTrip trip : engine.getPossibleMatches()) {
            this.addOption(new Option(trip.getDryInfoAsString()));
        }
    }

    @Override
    public void run() {
        show();
        int chosenOption = getOptionFromUser();
        System.out.println("Attempting to match...");
        engine.createNewMatch(chosenOption);
        System.out.println("Match created successfully!");
    }

    @Override
    public void show() {
        System.out.println(menuName);
        System.out.println(this);
    }
}
