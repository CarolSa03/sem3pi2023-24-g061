package ESINF.ui;

import ESINF.controller.MaxHubsRouteController;
import domain.BasketDistribution.Hub;
import domain.BasketDistribution.Local;
import domain.BasketDistribution.Route;
import domain.BasketDistribution.VehiclesGFH;
import utils.Utils;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

public class MaxHubsRouteUI implements Runnable {
    private static final MaxHubsRouteController controller = new MaxHubsRouteController();

    private final LinkedList<Local> localsList = controller.getLocalsList();

    @Override
    public void run() {
        System.out.println("Best Route with N-Hubs");
        LinkedList<Local> localsListCopy = new LinkedList<>(localsList);

        Local startLocal = readLocal(localsListCopy, "Start Local: ");
        Local endLocal = readLocal(localsListCopy, "End Local: ");
        LocalTime startTime = Utils.readTimeFromConsole("Time that leaves start local: ");
        int numberOfHubs = readNumberOfHubs();

        controller.selectHubs(numberOfHubs);

        List<Hub> hubs = controller.getHubs();

        LinkedList<Local> routeMaxHubsList = controller.getRouteMaxHubs(startLocal, endLocal, hubs);

        if (routeMaxHubsList.isEmpty()) {
            System.out.println("No route found with the specified information.");
            return;
        }

        System.out.println("Route created with success.\n");

        System.out.println("Vehicle Info:");
        VehiclesGFH vehicle = Utils.readVehicle();

        LinkedList<Route> routeList = controller.getRouteInfo(routeMaxHubsList, vehicle, startLocal, startTime, hubs);

        System.out.println("\nRoute Info:");
        for (Route route : routeList) {
            System.out.println(route.toString());
        }

        System.out.printf("\nTotal distance: %.0fkm\n", controller.getRouteTotalDistance(routeList));

    }

    private int readNumberOfHubs() {
        int numberOfHubs = Utils.readIntegerFromConsole("Number of Hubs [1 to " + localsList.size() + "]: ");
        while (numberOfHubs <= 0 || numberOfHubs > localsList.size() - 2) {
            if (numberOfHubs < 0) System.out.print("Number of Hubs can't be negative. ");
            if (numberOfHubs == 0) System.out.print("Number of Hubs can't be zero. ");
            if (numberOfHubs > localsList.size() - 2)
                System.out.printf("Number of Hubs can't be greater than the number of locals available. [1 to %s] ", localsList.size());
            System.out.println("Please try again.");
            numberOfHubs = Utils.readIntegerFromConsole("Number of Hubs: ");
        }
        return numberOfHubs;
    }

    private Local readLocal(LinkedList<Local> localsListCopy, String msg) {
        printList(localsListCopy);
        int localId = Utils.readIntegerFromConsole(msg);

        while (localId < 0 || localId > localsListCopy.size() - 1) {
            System.out.println("Invalid Local. Please try again.\n");
            printList(localsListCopy);
            localId = Utils.readIntegerFromConsole(msg);
        }
        Local local = getLocalById(localId, localsList);
        localsListCopy.remove(local);
        return local;
    }

    private Local getLocalById(int localId, LinkedList<Local> localListCopy) {
        Local local = new Local();
        for (Local locals : localListCopy) {
            if (localListCopy.get(localId).equals(locals)) local = locals;
        }

        return local;
    }

    private void printList(LinkedList<?> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + " - " + list.get(i).toString());
        }
    }

}
