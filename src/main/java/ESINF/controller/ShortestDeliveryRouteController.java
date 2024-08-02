package ESINF.controller;

import domain.BasketDistribution.*;
import javafx.util.Pair;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

public class ShortestDeliveryRouteController {

    public List<Hub> getHubs(int numberOfHubs) {
        HubManager.setHubsList(numberOfHubs, new HubComparatorByCollaborators());
        return HubManager.getHubs();
    }

    public Pair<LinkedList<Route>, Double> getShortestDeliveryRoute(VehiclesGFH vehicle, Local vertex, LocalTime startTime, List<Hub> hubs) {
        return RouteManager.getShortestDeliveryRouteInfo(vehicle, vertex, startTime, hubs);
    }

}