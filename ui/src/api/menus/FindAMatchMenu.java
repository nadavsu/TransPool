package api.menus;

import data.transpool.TransPoolTripRequest;
import exceptions.QuitOnFinishException;

import java.util.ArrayList;
import java.util.List;

public class FindAMatchMenu extends OptionedMenu {
    public FindAMatchMenu(String title) {
        super(title);

    }

    @Override
    public void run() throws QuitOnFinishException {
        List<TransPoolTripRequest> allRequests = new ArrayList<>(engine.getAllTransPoolTripRequests().getTranspoolTripRequests());
        for (TransPoolTripRequest request : allRequests) {
            this.addOption(new Option(request.getDryInfoAsString()));
        }
        show();
        int chosenOption = getOptionFromUser();
        engine.matchTrip(allRequests.get(chosenOption - 1).getID());
    }
}
