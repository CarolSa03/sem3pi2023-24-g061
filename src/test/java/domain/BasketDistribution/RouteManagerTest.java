package domain.BasketDistribution;

import javafx.util.Pair;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalTime;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RouteManagerTest {
    private final BasketDistributionMap basketDistributionMapClass = new BasketDistributionMap();
    private final BasketDistribution basketDistribution = new BasketDistribution();
    private LinkedList<Local> localsList = new LinkedList<>();
    private LinkedList<Distance> distancesList = new LinkedList<>();
    private final LinkedList<Local> routeLocalsExpected = new LinkedList<>();
    private final LinkedList<Route> routeExpected = new LinkedList<>();
    private final LinkedList<Hub> hubsExpected = new LinkedList<>();

    @BeforeEach
    void setUp() {
        String file_locals = "docs/Data/locais_small.csv";
        basketDistribution.createLocalsList(new File(file_locals), "docs/Data/");
        localsList = BasketDistribution.getLocalsList();
        String file_distances = "docs/Data/distancias_small.csv";
        basketDistribution.createDistancesList(new File(file_distances), "docs/Data/");
        distancesList = BasketDistribution.getDistancesList();

        basketDistributionMapClass.createMap();

        Local local1 = new Local(2, "CT2", 38.0333,-7.8833);
        Local local2 = new Local(7, "CT7", 38.5667,-7.9);
        Local local3 = new Local(13, "CT13", 39.2369,-8.685);
        Local local4 = new Local(10, "CT10", 39.7444,-8.8072);
        Local local5 = new Local(5, "CT5", 39.823,-7.4931);
        Local local6 = new Local(17, "CT17", 40.6667,-7.9167);
        Local local7 = new Local(16, "CT16", 41.3002,-7.7398);
        Local local8 = new Local(9, "CT9", 40.5364,-7.2683);

        routeLocalsExpected.add(local1);
        routeLocalsExpected.add(local2);
        routeLocalsExpected.add(local3);
        routeLocalsExpected.add(local4);
        routeLocalsExpected.add(local5);
        routeLocalsExpected.add(local6);
        routeLocalsExpected.add(local7);
        routeLocalsExpected.add(local8);

        routeExpected.add(new Route(local1, local2, 65.574, LocalTime.of(10, 0), LocalTime.of(10, 52)));
        routeExpected.add(new Route(local2, local3, 111.686, LocalTime.of(10, 52), LocalTime.of(12, 21)));
        routeExpected.add(new Route(local3, local4, 63.448, LocalTime.of(12, 21), LocalTime.of(13, 11)));
        routeExpected.add(new Route(local4, local5, 125.041, LocalTime.of(13, 11), LocalTime.of(14, 51)));
        routeExpected.add(new Route(local5, local6, 111.134, LocalTime.of(14, 51), LocalTime.of(16, 19)));
        routeExpected.add(new Route(local6, local7, 79.56, LocalTime.of(7, 57), LocalTime.of(9, 0)));
        routeExpected.add(new Route(local7, local8, 103.704, LocalTime.of(9, 25), LocalTime.of(10, 47)));

        hubsExpected.add(new Hub(16, "CT16"));
        hubsExpected.add(new Hub(2, "CT2"));
    }

    @AfterEach
    void tearDown() {
        routeLocalsExpected.clear();
        hubsExpected.clear();
        routeExpected.clear();
    }

    @Test
    void getRouteMaxHubs() {
        LinkedList<Local> routeLocals = RouteManager.getRouteMaxHubs(new Local(2, "CT2", 38.0333,-7.8833),
                new Local(9, "CT9", 40.5364,-7.2683), hubsExpected);
        assertEquals(routeLocalsExpected, routeLocals);
    }

    @Test
    void getRouteInfo() {
        VehiclesGFH vehicle = new VehiclesGFH(500, 75.0, 25);
        LinkedList<Route> route = RouteManager.getRouteInfo(routeLocalsExpected, vehicle, new Local(2, "CT2", 38.0333,-7.8833),
                LocalTime.of(10, 0), hubsExpected);
        for (int i = 0; i < route.size(); i++) {
            assertEquals(routeExpected.get(i).getLeavingLocal(), route.get(i).getLeavingLocal());
            assertEquals(routeExpected.get(i).getArrivingLocal(), route.get(i).getArrivingLocal());
            assertEquals(routeExpected.get(i).getDistance(), route.get(i).getDistance());
            assertEquals(routeExpected.get(i).getLeavingTime(), route.get(i).getLeavingTime());
            assertEquals(routeExpected.get(i).getArrivingTime(), route.get(i).getArrivingTime());
        }
    }

    @Test
    void getShortestDeliveryRoute() {
        Double expectedDistance = 0.0;
        Pair<LinkedList<Local>, Double> route = RouteManager.getShortestDeliveryRoute(new Local(2, "CT2", 38.0333,-7.8833), hubsExpected);
        for (int i = 0; i < route.getKey().size(); i++) {
            assertEquals(routeLocalsExpected.get(i), route.getKey().get(i));
            expectedDistance += routeExpected.get(i).getDistance();
        }
//        for (int i = 0; i < route.getKey().size(); i++) {
//            assertEquals(routeExpected.get(i).getLeavingLocal(), route.getKey().get(i).getLeavingLocal());
//            assertEquals(routeExpected.get(i).getArrivingLocal(), route.getKey().get(i).getArrivingLocal());
//            assertEquals(routeExpected.get(i).getDistance(), route.getKey().get(i).getDistance());
//            assertEquals(routeExpected.get(i).getLeavingTime(), route.getKey().get(i).getLeavingTime());
//            assertEquals(routeExpected.get(i).getArrivingTime(), route.getKey().get(i).getArrivingTime());
//            expectedDistance += routeExpected.get(i).getDistance();
//        }
        assertEquals(expectedDistance, route.getValue());
    }
}