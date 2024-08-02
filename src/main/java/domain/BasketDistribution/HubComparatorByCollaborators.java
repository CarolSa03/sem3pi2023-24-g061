package domain.BasketDistribution;

import java.util.Comparator;

public class HubComparatorByCollaborators implements Comparator<Local> {
    @Override
    public int compare(Local locality1, Local locality2) {
        return Integer.compare(HubManager.localToHub(locality1).getCollaborators(), HubManager.localToHub(locality2).getCollaborators());
    }
}
