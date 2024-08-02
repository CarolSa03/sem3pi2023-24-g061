package domain.BasketDistribution;

import ESINF.graph.AlgorithmsGraph;
import ESINF.graph.Graph;


import java.util.*;

public class VehiclePathFinder {


    /**
     * Determine minimum course with autonomy.
     * @param mapGraph the map graph
     */
    public void determineMinimumCourseWithAutonomy(Graph<Local, Double> mapGraph) {
        Map.Entry<Local, Local> furthestPair = AlgorithmsGraph.furthestVertices(mapGraph, Comparator.naturalOrder(), Double::sum);
        LinkedList<Local> shortPath = new LinkedList<>();
        List<Double> distances = new LinkedList<>();
        List<Local> chargings = new ArrayList<>();

        VehiclesGFH vehicle = new VehiclesGFH(80000, 1, 1);
        double autonomy = vehicle.getAvgAutonomy();
        double currentAutonomy = autonomy;

        Local origin = furthestPair.getKey();
        Local destination = furthestPair.getValue();

        System.out.println("\n");
        System.out.printf("Furthest locations are %s and %s \n", origin, destination);
        System.out.printf("Origin is %s \n", origin);
        System.out.printf("Destination is %s \n", destination);
        System.out.println("\n");
        System.out.println("Path between the two furthest locations in the distribution network:");

        LinkedList<Local> localities = AlgorithmsGraph.shortestPathWithAutonomy(mapGraph, origin, destination, Comparator.naturalOrder(), Double::sum, 0.0, autonomy);
        double totalDistance = 0;
        double distanceBetweenLocalities;

        if (localities != null) {
            for (int i = 0; i < localities.size() - 1; i++) {
                distanceBetweenLocalities = AlgorithmsGraph.shortestPath(mapGraph, localities.get(i), localities.get(i + 1), Comparator.naturalOrder(), Double::sum, 0.0, shortPath);
                distances.add(distanceBetweenLocalities);
                totalDistance += distanceBetweenLocalities;

                if (distances.get(i) > autonomy) {
                    System.out.println("With this autonomy you can't finish the path.");
                    return;
                }
                System.out.printf("%s -> %s : %.2f Km\n", localities.get(i).toString(), localities.get(i + 1).toString(), distanceBetweenLocalities / 1000);

                if (distances.get(i) > currentAutonomy) {
                    chargings.add(localities.get(i));
                    currentAutonomy = autonomy;
                } else {
                    currentAutonomy -= distances.get(i);
                }

            }
        }

        System.out.println("\n");
        System.out.println("Locations where the car was charged:");

        if (chargings.isEmpty()) {
            System.out.println("The car didn't need to be charged.");
        } else {
            for (Local charging : chargings) {
                System.out.println(charging.toString());
            }

            System.out.println("\n");
            int numCharges = chargings.size();
            System.out.printf("Number of charges needed: %d\n", numCharges);
            System.out.printf("Total distance of the path is %.02f km\n", totalDistance / 1000);
        }

    }
}
