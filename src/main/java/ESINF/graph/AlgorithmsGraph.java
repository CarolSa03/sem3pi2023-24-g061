package ESINF.graph;

import ESINF.graph.matrix.MatrixGraph;
import domain.BasketDistribution.Local;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.BinaryOperator;

public class AlgorithmsGraph extends Algorithms {

    public static <Local, Double> LinkedList<Local> BreadthFirstSearch(Graph<Local, Double> g, Local vert) {
        if (!g.validVertex(vert)) return null;

        LinkedList<Local> result = new LinkedList<>();
        boolean[] visited = new boolean[g.numVertices()];
        LinkedList<Local> q = new LinkedList<>();

        visited[g.key(vert)] = true;
        q.add(vert);

        while (!q.isEmpty()) {
            vert = q.remove();
            result.add(g.vertices().get(g.key(vert)));
            for (Local vAdj : g.adjVertices(vert)) {
                if (!visited[g.key(vAdj)]) {
                    visited[g.key(vAdj)] = true;
                    q.add(vAdj);
                }
            }
        }
        return result;
    }

    private static <Local, Double> void DepthFirstSearch(Graph<Local, Double> g, Local vOrig, boolean[] visited,
                                                         LinkedList<Local> qdfs) {
        visited[g.key(vOrig)] = true;
        qdfs.add(vOrig);
        for (Local vAdj : g.adjVertices(vOrig)) {
            if (!visited[g.key(vAdj)]) {
                DepthFirstSearch(g, vAdj, visited, qdfs);
            }
        }
    }

    public static <Local, Double> LinkedList<Local> DepthFirstSearch(Graph<Local, Double> g, Local vert) {
        if (!g.validVertex(vert)) return null;

        LinkedList<Local> result = new LinkedList<>();
        boolean[] visited = new boolean[g.numVertices()];

        DepthFirstSearch(g, vert, visited, result);

        return result;
    }

    public static <Local, Double> ArrayList<LinkedList<Local>> allPaths(Graph<Local, Double> g, Local vOrig, Local vDest) {
        if (!g.validVertex(vOrig) || !g.validVertex(vDest)) return null; // O(1)

        ArrayList<LinkedList<Local>> paths = new ArrayList<>(); // O(1)
        LinkedList<Local> path = new LinkedList<>(); // O(1)
        boolean[] visited = new boolean[g.numVertices()]; // O(V)

        allPaths(g, vOrig, vDest, visited, path, paths);

        return paths;
    }

    private static <Local, Double> void allPaths(Graph<Local, Double> g, Local vOrig, Local vDest, boolean[] visited,
                                                 LinkedList<Local> path, ArrayList<LinkedList<Local>> paths) // O(2^V)
    {
        visited[g.key(vOrig)] = true; // O(1)
        path.add(vOrig); // O(1)

        if (vOrig.equals(vDest)) {
            paths.add(new LinkedList<>(path)); // O(N)
        } else {
            for (Edge<Local, Double> edge : g.outgoingEdges(vOrig)) { // O(E)
                if (!visited[g.key(edge.getVDest())]) {
                    allPaths(g, edge.getVDest(), vDest, visited, path, paths); //O(2^V)
                }
            }
        }

        visited[g.key(vOrig)] = false;
        path.removeLast();
    }

    public static void allPathsWithAutonomy(Graph<Local, Double> g, Local vOrig, Local vDest, boolean[] visited,
                                            LinkedList<Local> path, ArrayList<LinkedList<Local>> paths, Double vehicleAutonomy) {

        if (vOrig == null || vDest == null || visited.length == 0) {
            return;
        }
        // Check for autonomy. If it's zero and the origin is not the destination, return.
        if (vehicleAutonomy == 0 && !vOrig.equals(vDest)) {
            return;
        }
        int vOrigKey = g.key(vOrig);
        if (vOrigKey < 0 || vOrigKey >= visited.length) {
            return; // Invalid vertex key
        }
        visited[vOrigKey] = true;
        path.add(vOrig);
        if (vOrig.equals(vDest)) { // Add path when destination is reached
            paths.add(new LinkedList<>(path));
        } else {
            for (Edge<Local, Double> edge : g.outgoingEdges(vOrig)) {
                Local vDestEdge = edge.getVDest();
                Double autonomyCost = edge.getWeight();
                // Proceed only if there's enough autonomy and the destination vertex is not visited
                if (!visited[g.key(vDestEdge)] && vehicleAutonomy >= autonomyCost) {
                    allPathsWithAutonomy(g, vDestEdge, vDest, visited, path, paths, vehicleAutonomy - autonomyCost);}}}
        visited[vOrigKey] = false;
        path.removeLast(); // Backtracking
    }




    public static <Local, Double> Double shortestPath(Graph<Local, Double> graph, Local vOrig, Local vDest,
                                                      Comparator<Double> ce, BinaryOperator<Double> sum, Double zero,
                                                      LinkedList<Local> shortPath) {

        if (!graph.validVertex(vOrig) || !graph.validVertex(vDest))
            return null;

        shortPath.clear();
        int numVerts = graph.numVertices();
        boolean[] visited = new boolean[numVerts];

        @SuppressWarnings("unchecked")
        Local[] pathKeys = (Local[]) Array.newInstance(vOrig.getClass(), numVerts);

        @SuppressWarnings("unchecked")
        Double[] dist = (Double[]) Array.newInstance(zero.getClass(), numVerts);

        for (int i = 0; i < numVerts; i++) {
            dist[i] = null;
            pathKeys[i] = null;
        }

        shortestPathDijkstra(graph, vOrig, ce, sum, zero, visited, pathKeys, dist);
        Double lengthPath = dist[graph.key(vDest)];

        if (lengthPath == null)
            return null;

        getPath(graph, vOrig, vDest, pathKeys, shortPath);

        return lengthPath;
    }

    private static <Local, Double> void shortestPathDijkstra(Graph<Local, Double> g, Local vOrig, Comparator<Double> ce,
                                                             BinaryOperator<Double> sum, Double zero, boolean[] visited,
                                                             Local[] pathKeys, Double[] dist) {
        int vk = g.key(vOrig);

        dist[vk] = zero;

        pathKeys[vk] = vOrig;

        while (vOrig != null) {
            vk = g.key(vOrig);
            visited[vk] = true;

            for (Edge<Local, Double> edge : g.outgoingEdges(vOrig)) {
                int vKeyAdj = g.key(edge.getVDest());

                if (!visited[vKeyAdj]) {
                    Double s = sum.apply(dist[vk], edge.getWeight());

                    if ((dist[vKeyAdj] == null) || ce.compare(dist[vKeyAdj], s) > 0) {
                        dist[vKeyAdj] = s;
                        pathKeys[vKeyAdj] = vOrig;
                    }
                }
            }

            Double minDistance = null;
            vOrig = null;
            for (Local vert : g.vertices()) {
                int i = g.key(vert);

                if (!visited[i] && dist[i] != null &&
                        (minDistance == null || ce.compare(dist[i], minDistance) < 0)) {
                    minDistance = dist[i];
                    vOrig = vert;
                }
            }
        }

    }


    public static <Local, Double> boolean shortestPaths(Graph<Local, Double> graph, Local
            vOrig, Comparator<Double> ce,
                                                        BinaryOperator<Double> sum, Double zero,
                                                        ArrayList<LinkedList<Local>> paths, ArrayList<Double> dists) {
        if (!graph.validVertex(vOrig)) return false;

        int numVerts = graph.numVertices();
        boolean[] visited = new boolean[numVerts];

        @SuppressWarnings("unchecked")
        Local[] pathKeys = (Local[]) Array.newInstance(vOrig.getClass(), numVerts);

        @SuppressWarnings("unchecked")
        Double[] dist = (Double[]) Array.newInstance(zero.getClass(), numVerts);

        for (int i = 0; i < numVerts; i++) {
            dist[i] = null;
            pathKeys[i] = null;
        }

        shortestPathDijkstra(graph, vOrig, ce, sum, zero, visited, pathKeys, dist);

        for (int i = 0; i < numVerts; i++) {
            if (dist[i] != null) {
                LinkedList<Local> path = new LinkedList<>();
                getPath(graph, vOrig, graph.vertices().get(i), pathKeys, path);
                paths.add(path);
                dists.add(dist[i]);
            }
        }

        return true;
    }

    private static <Local, Double> void getPath(Graph<Local, Double> g, Local vOrig, Local vDest,
                                                Local[] pathKeys, LinkedList<Local> path) {
        if (vOrig.equals(vDest)) {
            path.push(vOrig);
        } else {
            path.push(vDest);
            int vKey = g.key(vDest);
            vDest = pathKeys[vKey];
            getPath(g, vOrig, vDest, pathKeys, path);
        }
    }

    public static <
            Local, Double> MatrixGraph<Local, Double> minDistGraph(Graph<Local, Double> g, Comparator<Double> ce,
                                                                   BinaryOperator<Double> sum) {
        MatrixGraph<Local, Double> minDistGraph = new MatrixGraph<>(g);

        for (Local vertex : g.vertices()) {
            minDistGraph.addVertex(vertex);
            for (Edge<Local, Double> edge : g.outgoingEdges(vertex)) {
                if (edge.getVOrig().equals(vertex)) {
                    minDistGraph.addEdge(vertex, edge.getVDest(), edge.getWeight());
                }
            }
        }

        for (int k = 0; k < g.numVertices(); k++) {
            for (int i = 0; i < g.numVertices(); i++) {
                if (i != k && minDistGraph.edge(g.vertices().get(i), g.vertices().get(k)) != null) {
                    for (int j = 0; j < g.numVertices(); j++) {
                        if (j != i && j != k && minDistGraph.edge(g.vertices().get(k), g.vertices().get(j)) != null) {
                            Double s = sum.apply(minDistGraph.edge(g.vertices().get(i), g.vertices().get(k)).getWeight(),
                                    minDistGraph.edge(g.vertices().get(k), g.vertices().get(j)).getWeight());

                            if ((minDistGraph.edge(g.vertices().get(i), g.vertices().get(j)) == null) ||
                                    ce.compare(minDistGraph.edge(g.vertices().get(i), g.vertices().get(j)).getWeight(), s) > 0) {
                                minDistGraph.removeEdge(g.vertices().get(i), g.vertices().get(j));
                                minDistGraph.addEdge(g.vertices().get(i), g.vertices().get(j), s);
                            }
                        }
                    }
                }
            }
        }

        return minDistGraph;
    }

    private static <Local, Double extends Comparable<Double>> void shortestPathDijkstraWithAutonomy(
            Graph<Local, Double> g, Local vOrig, Comparator<Double> ce, BinaryOperator<Double> sum,
            Double zero, boolean[] visited, Local[] pathKeys, Double[] dist, Double vehicleAutonomy) {

        int numVerts = g.numVertices();

        PriorityQueue<AbstractMap.SimpleEntry<Local, Double>> queue = new PriorityQueue<>(Map.Entry.comparingByValue());

        queue.add(new AbstractMap.SimpleEntry<>(vOrig, zero));
        while (!queue.isEmpty()) {
            AbstractMap.SimpleEntry<Local, Double> current = queue.poll();
            Local currentNode = current.getKey();
            Double currentDist = current.getValue();

            if (visited[g.key(currentNode)])
                continue;

            visited[g.key(currentNode)] = true;

            for (Local neighbor : g.adjVertices(currentNode)) {
                if (!visited[g.key(neighbor)]) {
                    Double newDist = sum.apply(currentDist, g.edge(currentNode, neighbor).getWeight());

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

    public static <Local, Double extends Comparable<Double>> LinkedList<Local> shortestPathWithAutonomy(
            Graph<Local, Double> g, Local vOrig, Local
            vDest, Comparator<Double> ce, BinaryOperator<Double> sum,
            Double zero, Double vehicleAutonomy) {

        if (!g.validVertex(vOrig) || !g.validVertex(vDest))
            return null;

        LinkedList<Local> shortPath = new LinkedList<>();
        int numVerts = g.numVertices();
        boolean[] visited = new boolean[numVerts];

        @SuppressWarnings("unchecked")
        Local[] pathKeys = (Local[]) Array.newInstance(vOrig.getClass(), numVerts);

        @SuppressWarnings("unchecked")
        Double[] dist = (Double[]) Array.newInstance(zero.getClass(), numVerts);

        for (int i = 0; i < numVerts; i++) {
            dist[i] = null;
            pathKeys[i] = null;
        }

        shortestPathDijkstraWithAutonomy(g, vOrig, ce, sum, zero, visited, pathKeys, dist, vehicleAutonomy);
        Double lengthPath = dist[g.key(vDest)];

        if (lengthPath == null) return null;

        getPath(g, vOrig, vDest, pathKeys, shortPath);

        return shortPath;
    }

    public static <Local, Double> void allPathsWithAutonomy(Graph<Local, Double> g, Local vOrig, Local vDest,
                                                            boolean[] visited, LinkedList<Local> path,
                                                            ArrayList<LinkedList<Local>> paths) {
        visited[g.key(vOrig)] = true;
        path.add(vOrig);
        for (Edge<Local, Double> edg : g.outgoingEdges(vOrig)) {
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

    public static <Local, Double> Local[] shortestPathElements(Graph<Local, Double> g, Local vOrig, Local vDest,
                                                               Comparator<Double> ce, BinaryOperator<Double> sum,
                                                               Double zero, LinkedList<Local> shortPath) {

        if (!g.validVertex(vOrig) || !g.validVertex(vDest)) return null;

        shortPath.clear();

        int numVerts = g.numVertices();

        boolean[] visited = new boolean[numVerts];
        @SuppressWarnings("unchecked")
        Local[] pathKeys = (Local[]) Array.newInstance(vOrig.getClass(), numVerts);
        @SuppressWarnings("unchecked")
        Double[] dist = (Double[]) Array.newInstance(zero.getClass(), numVerts);

        for (int i = 0; i < numVerts; i++) {
            dist[i] = null;
            pathKeys[i] = null;
        }

        shortestPathDijkstra(g, vOrig, ce, sum, zero, visited, pathKeys, dist);

        Double lengthPath = dist[g.key(vDest)];

        if (lengthPath == null) return null;
        getPath(g, vOrig, vDest, pathKeys, shortPath);

        return pathKeys;
    }

    public static Local shortestPathLength(Graph<Local, Double> g, Local vOrig,
                                           boolean[] visited, int[] path, double[] dist) {
        Local minLocal = new Local();

        for (Local v : g.vertices()) {
            dist[g.key(v)] = Double.MAX_VALUE;
            path[g.key(v)] = -1;
            visited[g.key(v)] = false;
        }

        dist[g.key(vOrig)] = 0;

        while (vOrig != null) {
            int vOrigKey = g.key(vOrig);
            visited[vOrigKey] = true;

            for (Edge<Local, Double> edge : g.outgoingEdges(vOrig)) {
                Local vAdj = edge.getVDest();
                int vAdjKey = g.key(vAdj);

                if (!visited[vAdjKey]) {
                    double edgeWeight = edge.getWeight();
                    if (dist[vAdjKey] > dist[vOrigKey] + edgeWeight) {
                        dist[vAdjKey] = dist[vOrigKey] + edgeWeight;
                        path[vAdjKey] = vOrigKey;
                    }
                }
            }

            minLocal = getVertMinDist(dist, visited, g);
        }

        return minLocal;
    }

    private static Local getVertMinDist(double[] dist, boolean[] visited, Graph<Local, Double> g) {
        double min = Double.MAX_VALUE;
        Local minVertex = null;

        for (Local v : g.vertices()) {
            int vKey = g.key(v);
            if (!visited[vKey] && dist[vKey] < min) {
                min = dist[vKey];
                minVertex = v;
            }
        }

        return minVertex;
    }
}