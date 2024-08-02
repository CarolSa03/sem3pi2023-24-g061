package ESINF.graph;

import ESINF.graph.matrix.MatrixGraph;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.BinaryOperator;

/**
 * The type Algorithms.
 *
 * @author DEI -ISEP
 */
public class Algorithms {

    /**
     * Performs breadth-first search of a Graph starting in a vertex
     *
     * @param <V>  the type parameter
     * @param <E>  the type parameter
     * @param g    Graph instance
     * @param vert vertex that will be the source of the search
     * @return a LinkedList with the vertices of breadth-first search
     */
    public static <V, E> LinkedList<V> breadthFirstSearch(Graph<V, E> g, V vert) {
        if (!g.validVertex(vert)) {
            return null;
        }
        LinkedList<V> qbfs = new LinkedList<>();
        LinkedList<V> qaux = new LinkedList<>();

        qaux.add(vert);
        qbfs.add(vert);

        while (!qaux.isEmpty()) {
            vert = qaux.remove();
            for (V vAdj : g.adjVertices(vert)) {
                if (!qbfs.contains(vAdj)) {
                    qbfs.add(vAdj);
                    qaux.add(vAdj);
                }
            }
        }
        return qbfs;
    }

    /**
     * Performs depth-first search starting in a vertex
     *
     * @param g       Graph instance
     * @param vOrig   vertex of graph g that will be the source of the search
     * @param visited set of previously visited vertices
     * @param qdfs    return LinkedList with vertices of depth-first search
     */
    private static <V, E> void depthFirstSearch(Graph<V, E> g, V vOrig, boolean[] visited, LinkedList<V> qdfs) {
        visited[g.key(vOrig)] = true;
        qdfs.add(vOrig);
        for (V adjVertice : g.adjVertices(vOrig)) {
            if (!visited[g.key(adjVertice)]) {
                depthFirstSearch(g, adjVertice, visited, qdfs);
            }
        }
    }

    /**
     * Performs depth-first search starting in a vertex
     *
     * @param <V>  the type parameter
     * @param <E>  the type parameter
     * @param g    Graph instance
     * @param vert vertex of graph g that will be the source of the search
     * @return a LinkedList with the vertices of depth-first search
     */
    public static <V, E> LinkedList<V> depthFirstSearch(Graph<V, E> g, V vert) {
        LinkedList<V> qdfs = new LinkedList<>();
        boolean[] visited = new boolean[g.numVertices()];
        if (!g.validVertex(vert)) {
            return null;
        }
        depthFirstSearch(g, vert, visited, qdfs);
        return qdfs;
    }

    /**
     * Returns all paths from vOrig to vDest
     *
     * @param g       Graph instance
     * @param vOrig   Vertex that will be the source of the path
     * @param vDest   Vertex that will be the end of the path
     * @param visited set of discovered vertices
     * @param path    stack with vertices of the current path (the path is in reverse order)
     * @param paths   ArrayList with all the paths (in correct order)
     */
    private static <V, E> void allPaths(Graph<V, E> g, V vOrig, V vDest, boolean[] visited, LinkedList<V> path, ArrayList<LinkedList<V>> paths) {
        visited[g.key(vOrig)] = true;
        path.add(vOrig);
        for (Edge<V, E> edg : g.outgoingEdges(vOrig)) {
            if (edg.getVDest().equals(vDest)) {
                path.add(vDest);
                paths.add(path);
                path.removeLast();
            } else {
                if (!visited[g.key(edg.getVDest())]) {
                    allPaths(g, edg.getVDest(), vDest, visited, path, paths);
                }
            }

        }
        visited[g.key(vOrig)] = false;
        path.removeLast();
    }

    /**
     * Returns all paths from vOrig to vDest
     *
     * @param <V>   the type parameter
     * @param <E>   the type parameter
     * @param g     Graph instance
     * @param vOrig information of the Vertex origin
     * @param vDest information of the Vertex destination
     * @return paths ArrayList with all paths from vOrig to vDest
     */
    public static <V, E> ArrayList<LinkedList<V>> allPaths(Graph<V, E> g, V vOrig, V vDest) {
        LinkedList<V> path = new LinkedList<>();
        ArrayList<LinkedList<V>> paths = new ArrayList<>();
        if (g.validVertex(vDest) && g.validVertex(vOrig)) {
            boolean[] visited = new boolean[g.numVertices()];
            allPaths(g, vOrig, vDest, visited, path, paths);
            return paths;
        }
        return null;
    }


    /**
     * Computes shortest-path distance from a source vertex to all reachable
     * vertices of a graph g with non-negative edge weights
     * This implementation uses Dijkstra's algorithm
     *
     * @param g        Graph instance
     * @param vOrig    Vertex that will be the source of the path
     * @param visited  set of previously visited vertices
     * @param pathKeys minimum path vertices keys
     * @param dist     minimum distances
     */

    private static <V, E> void shortestPathDijkstra(Graph<V, E> g, V vOrig, Comparator<E> ce, BinaryOperator<E> sum, E zero, boolean[] visited, V[] pathKeys, E[] dist) {
        int vk = g.key(vOrig);

        dist[vk] = zero;

        pathKeys[vk] = vOrig;

        while (vOrig != null) {
            vk = g.key(vOrig);
            visited[vk] = true;

            for (Edge<V, E> edge : g.outgoingEdges(vOrig)) {
                int vkeyAdj = g.key(edge.getVDest());

                if (!visited[vkeyAdj]) {
                    E s = sum.apply(dist[vk], edge.getWeight());

                    if (dist[vkeyAdj] == null || ce.compare(dist[vkeyAdj], s) > 0) {
                        dist[vkeyAdj] = s;
                        pathKeys[vkeyAdj] = vOrig;
                    }
                }
            }

            E minDistance = null;
            vOrig = null;
            for (V vert : g.vertices()) {
                int i = g.key(vert);

                if (!visited[i] && dist[i] != null && (minDistance == null || ce.compare(dist[i], minDistance) < 0)) {
                    minDistance = dist[i];
                    vOrig = vert;
                }
            }
        }
    }

    private static <V, E extends Comparable<E>> void shortestPathDijkstraWithAutonomy(Graph<V, E> g, V vOrig, Comparator<E> ce, BinaryOperator<E> sum, E zero, boolean[] visited, V[] pathKeys, E[] dist, E vehicleAutonomy) {

        int numVerts = g.numVertices();

        PriorityQueue<AbstractMap.SimpleEntry<V, E>> queue = new PriorityQueue<>(Comparator.comparing(AbstractMap.SimpleEntry::getValue));
        queue.add(new AbstractMap.SimpleEntry<>(vOrig, zero));

        while (!queue.isEmpty()) {
            AbstractMap.SimpleEntry<V, E> current = queue.poll();
            V currentNode = current.getKey();
            E currentDist = current.getValue();

            if (visited[g.key(currentNode)])
                continue;

            visited[g.key(currentNode)] = true;

            for (V neighbor : g.adjVertices(currentNode)) {
                if (!visited[g.key(neighbor)]) {
                    E newDist = sum.apply(currentDist, g.edge(currentNode, neighbor).getWeight());

                    int neighborKey = g.key(neighbor);
                    if (neighborKey >= 0 && neighborKey < dist.length && dist[neighborKey] != null && ce.compare(newDist, dist[neighborKey]) > 0) {
                        newDist.equals(vehicleAutonomy);
                    }

                    if (dist[g.key(neighbor)] == null || ce.compare(newDist, dist[g.key(neighbor)]) < 0) {
                        dist[g.key(neighbor)] = newDist;
                        pathKeys[g.key(neighbor)] = currentNode;
                        queue.add(new AbstractMap.SimpleEntry<>(neighbor, newDist));
                    }

                }
            }
        }

    }

    /**
     * Shortest-path between two vertices
     *
     * @param <V>       the type parameter
     * @param <E>       the type parameter
     * @param graph     graph
     * @param vOrig     origin vertex
     * @param vDest     destination vertex
     * @param ce        comparator between elements of type E
     * @param sum       sum two elements of type E
     * @param zero      neutral element of the sum in elements of type E
     * @param shortPath returns the vertices which make the shortest path
     * @return if vertices exist in the graph and are connected, true, false otherwise
     */
    public static <V, E> E shortestPath(Graph<V, E> graph, V vOrig, V vDest, Comparator<E> ce, BinaryOperator<E> sum, E zero, LinkedList<V> shortPath) {
        if (!graph.validVertex(vOrig) || !graph.validVertex(vDest))
            return null;

        shortPath.clear();
        int numVerts = graph.numVertices();
        boolean[] visited = new boolean[numVerts]; //default value: false
        @SuppressWarnings("unchecked")
        V[] pathKeys = (V[]) new Object[numVerts];
        @SuppressWarnings("unchecked")
        E[] dist = (E[]) new Object[numVerts];
        for (int i = 0; i < numVerts; i++) {
            dist[i] = null;
            pathKeys[i] = null;
        }
        shortestPathDijkstra(graph, vOrig, ce, sum, zero, visited, pathKeys, dist);

        E lengthPath = dist[graph.key(vDest)];

        if (lengthPath != null) {
            getPath(graph, vOrig, vDest, pathKeys, shortPath);
            return lengthPath;
        }
        return null;
    }

    /**
     * Shortest-path between two vertices
     *
     * @param g         graph
     * @param vOrig     origin vertex
     * @param vDest     destination vertex
     * @param ce        comparator between elements of type E
     * @param sum       sum two elements of type E
     * @param zero      neutral element of the sum in elements of type E
     * @param shortPath returns the vertices which make the shortest path
     * @return if vertices exist in the graph and are connected, true, false otherwise
     */
    public static <V, E> V[] shortestPathElements(Graph<V, E> g, V vOrig, V vDest, Comparator<E> ce, BinaryOperator<E> sum, E zero, LinkedList<V> shortPath) {

        if (!g.validVertex(vOrig) || !g.validVertex(vDest))
            return null;

        shortPath.clear();
        int numVerts = g.numVertices();
        boolean[] visited = new boolean[numVerts];

        @SuppressWarnings("unchecked")
        V[] pathKeys = (V[]) Array.newInstance(vOrig.getClass(), numVerts);

        @SuppressWarnings("unchecked")
        E[] dist = (E[]) Array.newInstance(zero.getClass(), numVerts);

        for (int i = 0; i < numVerts; i++) {
            dist[i] = null;
            pathKeys[i] = null;
        }

        shortestPathDijkstra(g, vOrig, ce, sum, zero, visited, pathKeys, dist);
        E lengthPath = dist[g.key(vDest)];

        if (lengthPath == null)
            return null;

        getPath(g, vOrig, vDest, pathKeys, shortPath);

        return pathKeys;
    }

    public static <V, E extends Comparable<E>> LinkedList<V> shortestPathWithAutonomy(Graph<V, E> g, V vOrig, V vDest, Comparator<E> ce, BinaryOperator<E> sum, E zero, E vehicleAutonomy) {

        if (!g.validVertex(vOrig) || !g.validVertex(vDest))
            return null;

        LinkedList<V> shortPath = new LinkedList<>();
        int numVerts = g.numVertices();
        boolean[] visited = new boolean[numVerts];

        @SuppressWarnings("unchecked")
        V[] pathKeys = (V[]) Array.newInstance(vOrig.getClass(), numVerts);

        @SuppressWarnings("unchecked")
        E[] dist = (E[]) Array.newInstance(zero.getClass(), numVerts);

        for (int i = 0; i < numVerts; i++) {
            dist[i] = null;
            pathKeys[i] = null;
        }

        shortestPathDijkstraWithAutonomy(g, vOrig, ce, sum, zero, visited, pathKeys, dist, vehicleAutonomy);
        E lengthPath = dist[g.key(vDest)];

        if (lengthPath == null)
            return null;

        getPath(g, vOrig, vDest, pathKeys, shortPath);

        return shortPath;
    }

    /**
     * Shortest path through hubs.
     *
     * @param <V>    the type parameter
     * @param <E>    the type parameter
     * @param graph  the graph
     * @param vertex the vertex
     * @param hubs   the hubs
     * @param ce     the ce
     * @param sum    the sum
     * @param zero   the zero
     */
    public static <V, E> void shortestPathThroughHubs(Graph<V, E> graph, V vertex, List<V> hubs, Comparator<E> ce, BinaryOperator<E> sum, E zero) {
        if (!graph.validVertex(vertex)) {
            throw new IllegalArgumentException("Invalid vertex");
        }

        for (V hub : hubs) {
            if (!graph.validVertex(hub)) {
                throw new IllegalArgumentException("Invalid hub: " + hub);
            }

            LinkedList<V> shortPathToHub = new LinkedList<>();
            E distanceToHub = shortestPath(graph, vertex, hub, ce, sum, zero, shortPathToHub);

            if (distanceToHub != null) {
                System.out.println("Path to hub " + hub + ": " + shortPathToHub + " Distance: " + distanceToHub);
            } else {
                System.out.println("No path found to hub " + hub);
            }
        }

        LinkedList<V> shortPathToOrig = new LinkedList<>();
        E distanceToOrig = shortestPath(graph, hubs.get(hubs.size() - 1), vertex, ce, sum, zero, shortPathToOrig);

        if (distanceToOrig != null) {
            System.out.println("Returning path to origin " + vertex + ": " + shortPathToOrig + " Distance: " + distanceToOrig);
        } else {
            System.out.println("No path found returning to origin " + vertex);
        }
    }

    /**
     * Shortest-path between a vertex and all other vertices
     *
     * @param <V>   the type parameter
     * @param <E>   the type parameter
     * @param graph graph
     * @param vOrig start vertex
     * @param ce    comparator between elements of type E
     * @param sum   sum two elements of type E
     * @param zero  neutral element of the sum in elements of type E
     * @param paths returns all the minimum paths
     * @param dists returns the corresponding minimum distances
     * @return if vOrig exists in the graph true, false otherwise
     */
    public static <V, E> boolean shortestPaths(Graph<V, E> graph, V vOrig, Comparator<E> ce, BinaryOperator<E> sum, E zero, ArrayList<LinkedList<V>> paths, ArrayList<E> dists) {
        if (!graph.validVertex(vOrig)) return false;

        paths.clear();
        dists.clear();
        int numVerts = graph.numVertices();
        boolean[] visited = new boolean[numVerts]; //default value: false
        @SuppressWarnings("unchecked")
        V[] pathKeys = (V[]) new Object[numVerts];
        @SuppressWarnings("unchecked")
        E[] dist = (E[]) new Object[numVerts];
        for (int i = 0; i < numVerts; i++) {
            dist[i] = null;
            pathKeys[i] = null;
        }
        shortestPathDijkstra(graph, vOrig, ce, sum, zero, visited, pathKeys, dist);

        dists.clear();
        paths.clear();
        for (int i = 0; i < numVerts; i++) {
            paths.add(null);
            dists.add(null);
        }
        for (V vDst : graph.vertices()) {
            int i = graph.key(vDst);
            if (dist[i] != null) {
                LinkedList<V> shortPath = new LinkedList<>();
                getPath(graph, vOrig, vDst, pathKeys, shortPath);
                paths.set(i, shortPath);
                dists.set(i, dist[i]);
            }
        }
        return true;
    }


    /**
     * Extracts from pathKeys the minimum path between voInf and vdInf
     * The path is constructed from the end to the beginning
     *
     * @param g        Graph instance
     * @param vOrig    information of the Vertex origin
     * @param vDest    information of the Vertex destination
     * @param pathKeys minimum path vertices keys
     * @param path     stack with the minimum path (correct order)
     */
    private static <V, E> void getPath(Graph<V, E> g, V vOrig, V vDest, V[] pathKeys, LinkedList<V> path) {
        if (vOrig.equals(vDest))
            path.push(vOrig);
        else {
            path.push(vDest);
            int vKey = g.key(vDest);
            vDest = pathKeys[vKey];
            getPath(g, vOrig, vDest, pathKeys, path);
        }
    }

    /**
     * Calculates the minimum distance graph using Floyd-Warshall
     *
     * @param <V> the type parameter
     * @param <E> the type parameter
     * @param g   initial graph
     * @param ce  comparator between elements of type E
     * @param sum sum two elements of type E
     * @return the minimum distance graph
     */
    public static <V, E> MatrixGraph<V, E> minDistGraph(Graph<V, E> g, Comparator<E> ce, BinaryOperator<E> sum) {
        MatrixGraph<V, E> minDistGraph = new MatrixGraph<>(g);
        for (V vertex : g.vertices()) {
            minDistGraph.addVertex(vertex);
            for (Edge<V, E> edge : g.outgoingEdges(vertex)) {
                if (edge.getVOrig().equals(vertex)) {
                    minDistGraph.addEdge(vertex, edge.getVDest(), edge.getWeight());
                }
            }
        }

        for (int k = 0; k < g.numVertices(); k++) {
            for (int i = 0; i < g.numVertices(); i++) {
                if (i != k && g.edge(i, k) != null && g.edge(i, k).getWeight().equals(1)) {
                    for (int j = 0; j < g.numVertices(); j++) {
                        if (i != j && j != k && minDistGraph.edge(k, j) != null && g.edge(k, j).getWeight().equals(1)) {
                            if (g.edge(i, j) == null || ce.compare(g.edge(i, j).getWeight(), sum.apply(g.edge(i, k).getWeight(), g.edge(k, j).getWeight())) > 0) {
                                minDistGraph.removeEdge(g.vertex(i), g.vertex(j));
                                minDistGraph.addEdge(g.vertex(i), g.vertex(j), sum.apply(minDistGraph.edge(i, k).getWeight(), minDistGraph.edge(k, j).getWeight()));
                            }
                        }
                    }
                }
            }
        }

        return minDistGraph;
    }

    public static <V, E> Map.Entry<V, V> furthestVertices(Graph<V, E> g, Comparator<E> ce, BinaryOperator<E> sum) {
        int numVerts = g.numVertices();
        if (numVerts == 0) {
            return null;
        }

        E[][] dist = (E[][]) new Object[g.numVertices()][g.numVertices()];

        for (int i = 0; i < numVerts; i++) {
            for (int j = 0; j < numVerts; j++) {
                Edge<V, E> e = g.edge(g.vertices().get(i), g.vertices().get(j));
                if (e != null) {
                    dist[i][j] = e.getWeight();
                }
            }
        }

        for (int k = 0; k < numVerts; k++) {
            for (int i = 0; i < numVerts; i++) {
                if (i != k && dist[i][k] != null) {
                    for (int j = 0; j < numVerts; j++) {
                        if (j != i && j != k && dist[k][j] != null) {
                            E s = sum.apply(dist[i][k], dist[k][j]);
                            if ((dist[i][j] == null) || ce.compare(dist[i][j], s) > 0) {
                                dist[i][j] = s;
                            }
                        }
                    }
                }
            }
        }

        E maxDistance = null;
        Map.Entry<V, V> furthestVertices = null;

        for (int i = 0; i < numVerts; i++) {
            for (int j = 0; j < numVerts; j++) {
                if (dist[i][j] != null && (maxDistance == null || ce.compare(dist[i][j], maxDistance) > 0)) {
                    maxDistance = dist[i][j];
                    furthestVertices = new AbstractMap.SimpleEntry<>(g.vertices().get(i), g.vertices().get(j));
                }
            }
        }

        return furthestVertices;
    }

    private static <V, E extends Number> V findNearestVertexInGraph(Graph<V, E> optimizedGraph, V hub, Graph<V, E> originalGraph) {
        V nearest = null;
        double minDistance = Double.MAX_VALUE;

        for (V vertex : optimizedGraph.vertices()) {
            E edgeWeight = originalGraph.edge(hub, vertex).getWeight();
            if (edgeWeight != null) {
                double distance = edgeWeight.doubleValue();
                if (distance < minDistance) {
                    nearest = vertex;
                    minDistance = distance;
                }
            }
        }

        return nearest;
    }

//    public static <V, E> MapGraph<V, E> shortestDeliveryRoute(V vertex, Graph<V, E> graph, List<V> hubs) {
//        Map<V, E> shortestDeliveryRoute = new HashMap<>();
//        if (!graph.validVertex(vertex)) {
//            return null;
//        }
//
//        for (E hub : hubs) {
//            if (!graph.validVertex((V) hub)) {
//                return null;
//            }
//        }
//
//        for (E hub : hubs) {
//            LinkedList<V> shortPathToHub = new LinkedList<>();
//            E distanceToHub = shortestPath(graph, vertex, hub, Comparator.naturalOrder(), Double::sum, 0.0, shortPathToHub);
//
//            if (distanceToHub != null) {
//                shortestDeliveryRoute.put(hub, distanceToHub);
//            } else {
//                return null;
//            }
//        }
//
//        LinkedList<V> shortPathToOrig = new LinkedList<>();
//        E distanceToOrig = shortestPath(graph, hubs.get(hubs.size() - 1), vertex, Comparator.naturalOrder(), Double::sum, 0.0, shortPathToOrig);
//
//        if (distanceToOrig != null) {
//            shortestDeliveryRoute.put(vertex, distanceToOrig);
//        } else {
//            return null;
//        }
//
//        return shortestDeliveryRoute;
//    }

}