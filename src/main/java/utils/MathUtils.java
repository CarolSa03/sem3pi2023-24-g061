package utils;

import ESINF.graph.AlgorithmsGraph;
import ESINF.graph.Graph;
import domain.BasketDistribution.Local;

import java.util.Comparator;
import java.util.LinkedList;

public class MathUtils {

    private static final double earthRadius = 6371.0;

    /**
     * Calculates the distance between two points in a sphere
     *
     * @param lat1 latitude of first point
     * @param lng1 longitude of first point
     * @param lat2 latitude of second point
     * @param lng2 longitude of second point
     * @return distance between two points
     */
    public static Double calculateDistance(double lat1, double lng1, double lat2, double lng2) { // O(1)

        //code extracted from given source: http://www.movable-type.co.uk/scripts/latlong.html
        lat1 = Math.toRadians(lat1);
        lng1 = Math.toRadians(lng1);
        lat2 = Math.toRadians(lat2);
        lng2 = Math.toRadians(lng2);

        double dLat = lat2 - lat1;
        double dLon = lng2 - lng1;

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return Math.round(earthRadius * c * 100.0) / 100.0;
    }

    /**
     * This method calculates the average distance from a locality to all other localities in the graph.
     *
     * @param mapGraph the map graph
     * @param local    the local
     * @return double
     */
    public static double calculateAverage(Graph<Local, Double> mapGraph, Local local) {
        double total = 0;
        int numVertices = mapGraph.numVertices() - 1;

        for (Local other : mapGraph.vertices()) {
            if (!local.equals(other)) {
                Double distance = AlgorithmsGraph.shortestPath(mapGraph, local, other, Comparator.naturalOrder(), Double::sum, 0.0, new LinkedList<>());
                if (distance != null) total += distance;
            }
        }

        return total / numVertices;
    }


    /**
     * Calculates centrality of a locality based on reachable vertices using Dijkstra's algorithm.
     *
     * @param mapGraph the map graph
     * @param local    the local
     * @return int
     */
    public static int calculateCentrality(Graph<Local, Double> mapGraph, Local local) {
        int centrality = 0;
        for (Local other : mapGraph.vertices()) {
            if (!local.equals(other) && AlgorithmsGraph.shortestPath(mapGraph, local, other, Comparator.naturalOrder(), Double::sum, 0.0, new LinkedList<>()) != null) {
                centrality++;

            }
        }

        return centrality;
    }

}
