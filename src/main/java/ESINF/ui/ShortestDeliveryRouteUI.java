package ESINF.ui;

import ESINF.controller.ShortestDeliveryRouteController;
import domain.BasketDistribution.*;
import javafx.util.Pair;
import utils.Utils;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;


public class ShortestDeliveryRouteUI implements Runnable {

    private final ShortestDeliveryRouteController controller;

    public ShortestDeliveryRouteUI() {
        controller = new ShortestDeliveryRouteController();
    }

    @Override
    public void run() {

        System.out.println("###Shortest Delivery Route###");

        int numberOfHubs = Utils.readIntegerFromConsole("Number of hubs: ");
        List<Hub> hubs = controller.getHubs(numberOfHubs);

        if (hubs.size() < numberOfHubs) {
            System.out.printf("The inserted number of hubs is too big!\nHubs size: %d hubs\n", hubs.size());
        }

        Utils.showList(hubs, "\n#HUBS#\n");

        Local vertex = (Local) Utils.showAndSelectOne(BasketDistributionMap.getBasketDistributionMap().vertices(), "Select origin local from the following locals:\n\nLOCALS\n");
        VehiclesGFH vehicle = Utils.readVehicle();
        LocalTime startTime = Utils.readTimeFromConsole("Time that leaves origin local: ");

        Pair<LinkedList<Route>, Double> route = controller.getShortestDeliveryRoute(vehicle, vertex, startTime, hubs);

        LocalTime travellingTime = LocalTime.of(0,0);
        System.out.println("\n#Route Info:#\n");
        for (Route r : route.getKey()) {
            System.out.println(r.toString());
            LocalTime routeTravellingTime = r.getArrivingTime().minusHours(startTime.getHour()).minusMinutes(startTime.getMinute());
            travellingTime = travellingTime.plusHours(routeTravellingTime.getHour()).plusMinutes(routeTravellingTime.getMinute());
        }

        System.out.printf("\nTotal distance: %.0fkm\n", route.getValue());
        System.out.printf("Total travelling time: %sh\n", Utils.parsedTimeHelper(travellingTime.getHour() + ":" + travellingTime.getMinute()));

    }

}