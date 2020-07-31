package data.transpool.trip;

import data.transpool.map.component.Stop;
import data.transpool.user.account.TransPoolUserAccount;

public interface Trip {
    TransPoolUserAccount getTripOwner();

    int getTripID();

    Stop getSourceStop();

    Stop getDestinationStop();
}
