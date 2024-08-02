package ESINF.graph;

import ESINF.graph.map.MapGraph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * The type Kruskal algorithm.
 */
public class KruskalAlgorithm extends Algorithms {

    private static double totalDistanceTwoWays;

    /**
     * Min dist graph map graph.
     *
     * @param <V> the type parameter
     * @param <E> the type parameter
     * @param g   the g
     * @return the map graph
     */
    public static <V, E> MapGraph<V, E> minDistGraph(Graph<V, E> g) {

        //V: number of vertices in the graph
        //E: number of edges in the graph

        MapGraph<V,E> minDistGraph = new MapGraph<>(false);
        for (V v : g.vertices()) {
            minDistGraph.addVertex(v);                                                             //O(V)
        }

        List<Edge<V, E>> edgesList = new ArrayList<>(g.edges());                                        //O(E)

        for (Edge<V, E> e : edgesList) {                                                             //O(E)
            LinkedList<V> verticesReachableByOrigin = depthFirstSearch(minDistGraph, e.getVOrig());  //O(V+E)
            if(!verticesReachableByOrigin.contains(e.getVDest())) {
                minDistGraph.addEdge(e.getVOrig(), e.getVDest(), e.getWeight());               //O(E)
            }
        }

        minDistGraph.edges().forEach(e -> totalDistanceTwoWays += (Double) e.getWeight());

        return minDistGraph;

        //O(V) + O(E) + O(E) * [O(V+E) + O(E)] + O[E] = O(E) * O(V+E) = O(E^2) = O(n^2)

    }

    /**
     * Gets total distance.
     *
     * @return the total distance
     */
    public static double getTotalDistance() {
        return totalDistanceTwoWays / 2;
    }

}
