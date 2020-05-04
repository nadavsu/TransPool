package api.menu;

import api.MatchingEngine;
import data.transpool.trip.PossibleMatch;


/**
 * A menu which allows the user to choose the found possible TransPool trip matches.
 */
public class ChooseAMatchMenu extends OptionedMenu {

    private MatchingEngine engine;

    /**
     * Constructs a new menu, adds the possible TP trips as strings to the options list.
     * @param menuName - the name of the menu
     * @param engine - the matching engine with the possible matches list populated.
     */
    ChooseAMatchMenu(String menuName, MatchingEngine engine) {
        super(menuName);
        this.engine = engine;
        for (PossibleMatch possibleMatch : engine.getPossibleMatches()) {
            this.addOption(new Option(possibleMatch.toString()));
        }
    }

    @Override
    public void run() {
        showMenu();
        getOptionFromUser();
        System.out.println("Attempting to match...");
        engine.createNewMatch(chosenOption);
        System.out.println("Match created successfully!");
    }

    @Override
    public void showMenu() {
        System.out.println(menuName);
        System.out.println(this);
    }
}
