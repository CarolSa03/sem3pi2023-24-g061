package domain.BasketDistribution;

import ESINF.graph.AlgorithmsGraph;
import javafx.util.Pair;

import java.time.LocalTime;
import java.util.*;

public class RouteManager {

    /**
     * This method returns the route with the maximum number of hubs.
     *
     * @param startLocal the start local
     * @param endLocal the end local
     * @param selectedHubs the selected hubs
     * @return the route max hubs
     */

    public static LinkedList<Local> getRouteMaxHubs(Local startLocal, Local endLocal, List<Hub> selectedHubs) //O(2^V)
    {

        ArrayList<LinkedList<Local>> possibleRoutes =
                AlgorithmsGraph.allPaths(BasketDistributionMap.getBasketDistributionMap(), startLocal, endLocal); //O(2^V)

        if (possibleRoutes == null || possibleRoutes.isEmpty()) return new LinkedList<>();

        LinkedList<Local> routeList = possibleRoutes.getFirst();

        while (!verifyRoute(routeList, selectedHubs) && possibleRoutes.size() > 1) { //O(N)
            possibleRoutes.removeFirst();
            if (!possibleRoutes.isEmpty()) {
                routeList = possibleRoutes.getFirst();
            }
        }

        possibleRoutes.removeFirst(); //O(N)

        if (possibleRoutes.isEmpty()) return new LinkedList<>(); //O(N)


        for (LinkedList<Local> route : possibleRoutes) //O(N^2 * M)
            if (verifyRoute(route, selectedHubs) // O(N*M)
                    && (route.size() < routeList.size())) routeList = route;


        if (!verifyRoute(routeList, selectedHubs)) return new LinkedList<>(); // O(N*M)

        return routeList;
    }

    /**
     * This method calculates the distance of a route.
     *
     * @param route the route
     * @return the double
     */
    public static double calculateRouteDistance(LinkedList<Local> route) { //O(V)
        double distance = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            distance += BasketDistributionMap.getBasketDistributionMap().edge(route.get(i), route.get(i + 1)).getWeight();
        }
        return distance;
    }

    /**
     * This method verifies if a route is valid.
     *
     * @param route the route
     * @param selectedHubs the selected hubs
     * @return the boolean
     */
    private static boolean verifyRoute(LinkedList<Local> route, List<Hub> selectedHubs) //O(N*M)
    {
        int counter = 0;

        if (route.size() < selectedHubs.size()) return false;

        for (Local local : route)
            if (selectedHubs.contains(HubManager.localToHub(local))) counter++;

        return counter == selectedHubs.size();
    }

    /**
     * This method returns the route info.
     *
     * @param routeMaxHubsList the route max hubs list
     * @param vehicle the vehicle
     * @param leavingLocal the leaving local
     * @param startTime the start time
     * @param selectedHubs the selected hubs
     * @return the route info
     */
    public static LinkedList<Route> getRouteInfo(LinkedList<Local> routeMaxHubsList, VehiclesGFH vehicle,
                                                 Local leavingLocal, LocalTime startTime, List<Hub> selectedHubs) { // O(N^2)
        LinkedList<Route> list = new LinkedList<>();
        vehicle.setAccumulatedDistance(0.0);
        LocalTime leavingTime = startTime;
        routeMaxHubsList.remove(leavingLocal); // O(N)

        for (Local arrivingLocal : routeMaxHubsList) { // O(N)
            Route route = new Route();

            Double distance = BasketDistributionMap.getBasketDistributionMap().edge(leavingLocal, arrivingLocal).getWeight();

            boolean leavingLocalIsHub = selectedHubs.contains(HubManager.localToHub(leavingLocal));
            boolean arrivingLocalIsHub = selectedHubs.contains(HubManager.localToHub(arrivingLocal));

            route.setLeavingLocalIsHub(leavingLocalIsHub);
            route.setArrivingLocalIsHub(arrivingLocalIsHub);
            route.setLeavingLocal(leavingLocal);
            route.setArrivingLocal(arrivingLocal);
            route.setDistance(distance);

            if (leavingLocalIsHub && !list.isEmpty()) {
                leavingTime = leavingTime.plusMinutes(vehicle.getAvgDischargeTime());
            }

            LocalTime arrivalTime = calculateArrivalTime(leavingTime, distance, vehicle);

            if (arrivingLocalIsHub) {
                if (isHubOpen(HubManager.localToHub(arrivingLocal), arrivalTime)) {
                    arrivalTime = arrivalTime.plusMinutes(vehicle.getAvgDischargeTime());
                } else {
                    arrivalTime = HubManager.localToHub(arrivingLocal).getOperatingHoursStart();
                    leavingTime = calculateLeavingTime(arrivalTime, distance, vehicle);
                }
            }


            route.setLeavingTime(leavingTime);
            route.setArrivingTime(arrivalTime);

            leavingLocal = arrivingLocal;
            leavingTime = route.getArrivingTime();
            list.add(route);

            if (list.getLast().getArrivingLocal().equals(routeMaxHubsList.getLast())) break;
        }

        return list;
    }

    /**
     * This method calculates the leaving time.
     *
     * @param arrivalTime the arrival time
     * @param distance the distance
     * @param vehicle the vehicle
     * @return the local time
     */
    private static LocalTime calculateLeavingTime(LocalTime arrivalTime, Double distance, VehiclesGFH vehicle) { // O(1)
        double travelTimeHours = distance / vehicle.getAvgVelocity();
        long hours = (long) travelTimeHours;
        long minutes = (long) ((travelTimeHours - hours) * 60);
        return arrivalTime.minusHours(hours).minusMinutes(minutes);
    }

    /**
     * This method verifies if a hub is open.
     * @param hub the hub
     * @param time the time
     * @return the boolean
     */
    private static boolean isHubOpen(Hub hub, LocalTime time) { //O(1)
        return !time.isBefore(hub.getOperatingHoursStart()) && !time.isAfter(hub.getOperatingHoursEnd());
    }

    /**
     * This method calculates the arrival time.
     *
     * @param arrivingTime the arriving time
     * @param distance the distance
     * @param vehicle the vehicle
     * @return the local time
     */
    public static LocalTime calculateArrivalTime(LocalTime arrivingTime, double distance, VehiclesGFH vehicle) { // O(1)
        double travelTimeHours = distance / vehicle.getAvgVelocity();
        vehicle.setAccumulatedDistance(vehicle.getAccumulatedDistance() + distance);
        if (vehicle.getAccumulatedDistance() > vehicle.getAvgAutonomy()) {
            vehicle.setAccumulatedDistance(distance);
            travelTimeHours += (double) vehicle.getAvgDischargeTime() / 60;
        }
        long hours = (long) travelTimeHours;
        long minutes = (long) ((travelTimeHours - hours) * 60);
        return arrivingTime.plusHours(hours).plusMinutes(minutes);
    }


    /**
     * This method returns the shortest delivery route.
     * @param vertex the vertex
     * @param hubs the hubs
     * @return the pair
     */
    public static Pair<LinkedList<Local>, Double> getShortestDeliveryRoute(Local vertex, List<Hub> hubs) { // O(N^3)

        LinkedList<Local> bestRoute = new LinkedList<>();
        bestRoute.add(0, vertex);

        Double shortestDistance = Double.MAX_VALUE;

        LinkedList<Local> shortestPath = new LinkedList<>();
        LinkedList<Local> hubShortestPath = new LinkedList<>();

        Local origin = vertex;
        Local destiny = null;

        List<Local> hubsCopy = new ArrayList<>(HubManager.hubsToLocals(hubs));

        while (!hubsCopy.isEmpty()) { // O(N^2)
            for (Local local : hubsCopy) {
                Double shortestPathLength = AlgorithmsGraph.shortestPath(BasketDistributionMap.getBasketDistributionMap(), origin, local, Comparator.comparingDouble(Double::doubleValue), Double::sum, 0.0, shortestPath);
                if (shortestPathLength != null && shortestPathLength < shortestDistance) {
                    shortestDistance = shortestPathLength;
                    hubShortestPath = new LinkedList<>(shortestPath);
                    destiny = local;
                }
            }

            for (int i = 1; i < hubShortestPath.size(); i++) {
                bestRoute.add(hubShortestPath.get(i));
            }

            origin = destiny;
            hubsCopy.remove(destiny);

            shortestDistance = Double.MAX_VALUE;
            hubShortestPath.clear();
            shortestPath.clear();
        }

        AlgorithmsGraph.shortestPath(BasketDistributionMap.getBasketDistributionMap(), origin, vertex, Comparator.comparing(Double::doubleValue), Double::sum, 0.0, shortestPath);
        for (int i = 1; i < shortestPath.size(); i++) { // O(N)
            bestRoute.add(shortestPath.get(i));
        }

        return new Pair<>(bestRoute, calculateRouteDistance(bestRoute));
    }

    /**
     * This method returns the route info.
     *
     * @param vehicle the vehicle
     * @param vertex the vertex
     * @param startTime the start time
     * @param hubs the hubs
     * @return the pair
     */
    public static Pair<LinkedList<Route>, Double> getShortestDeliveryRouteInfo(VehiclesGFH vehicle, Local vertex, LocalTime startTime, List<Hub> hubs) { // O(N^3)
        Pair<LinkedList<Local>, Double> shortestDeliveryRoute = getShortestDeliveryRoute(vertex, hubs); // O(N^3)
        return new Pair<>(getRouteInfo(shortestDeliveryRoute.getKey(), vehicle, vertex, startTime, hubs), shortestDeliveryRoute.getValue());
    }

    /**
     * This method returns the route total distance.
     * @param routes the routes
     * @return the distance
     */
    public static double getRouteTotalDistance(LinkedList<Route> routes) { // O(N)
        double distance = 0;
        for (Route route : routes) distance += route.getDistance();
        return distance;
    }

}