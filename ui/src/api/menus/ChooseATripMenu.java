package api.menus;

import api.Engine;
import data.transpool.TransPoolTrip;

public class ChooseATripMenu extends OptionedMenu {

    private Engine engine;

    ChooseATripMenu(String menuName, Engine engine) {
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
        engine.createNewMatch(chosenOption - 1);
        System.out.println("Match created successfully!");
    }

    @Override
    public void show() {
        System.out.println(this);
    }
}
