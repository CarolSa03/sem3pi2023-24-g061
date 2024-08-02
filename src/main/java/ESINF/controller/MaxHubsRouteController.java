package ESINF.controller;

import domain.BasketDistribution.*;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

public class MaxHubsRouteController {
    public LinkedList<Local> getRouteMaxHubs(Local startLocal, Local endLocal, List<Hub> selectedHubs) {
       return RouteManager.getRouteMaxHubs(startLocal, endLocal, selectedHubs);
    }

    public List<Hub> getHubs() {
        return HubManager.getHubs();
    }

    public void selectHubs(int numberOfHubs) {
        HubManager.setHubsList(numberOfHubs, new HubComparatorByDefinedCriteria());
    }

    public LinkedList<Route> getRouteInfo(LinkedList<Local> routeMaxHubsList, VehiclesGFH vehicle, Local startLocal, LocalTime startTime, List<Hub> selectedHubs) {
        return RouteManager.getRouteInfo(routeMaxHubsList, vehicle, startLocal, startTime, selectedHubs);
    }

    public LinkedList<Local> getLocalsList() {
        return BasketDistribution.getLocalsList();
    }

    public double getRouteTotalDistance(LinkedList<Route> routeList) {
        return RouteManager.getRouteTotalDistance(routeList);
    }
}
