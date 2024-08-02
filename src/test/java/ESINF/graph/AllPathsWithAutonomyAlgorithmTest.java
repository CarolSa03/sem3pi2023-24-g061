package ESINF.graph;

import ESINF.graph.map.MapGraph;
import domain.BasketDistribution.BasketDistributionMap;
import domain.BasketDistribution.Local;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;


public class AllPathsWithAutonomyAlgorithmTest {

    @Test
    void testNoAutonomy() {
        Graph<Local, Double> graph = new MapGraph<>(true);
        Local v1 = new Local(2, "CT2", -20, 0.01);
        Local v2 = new Local(3, "CT3", -89, 1.01);
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addEdge(v1, v2, 50.0);

        double autonomy = 0.0;

        ArrayList<LinkedList<Local>> paths = new ArrayList<>();
        AlgorithmsGraph.allPathsWithAutonomy(graph, v1, v2, new boolean[graph.numVertices()], new LinkedList<>(), paths, autonomy);

        assertTrue(paths.isEmpty(), "Paths should be empty when there is no autonomy");
    }

    @Test
    void testAutonomy() {
        Graph<Local, Double> graph = new MapGraph<>(true);
        Local v2 = new Local(2, "CT2", -20, 0.01);
        Local v3 = new Local(3, "CT3", -89, 1.01);
        graph.addVertex(v2);
        graph.addVertex(v2);
        graph.addEdge(v2, v3, 50.0);

        double autonomy = 70.0;

        ArrayList<LinkedList<Local>> paths = new ArrayList<>();
        AlgorithmsGraph.allPathsWithAutonomy(graph, v2, v3, new boolean[graph.numVertices()], new LinkedList<>(), paths, autonomy);

        assertEquals(1, paths.size(), "Path size should be one.");
    }

    @Test
    void testMultiplePaths() {
        Graph<Local, Double> graph = new MapGraph<>(true);
        Local v1 = new Local(1, "CT1", -10, 0.01);
        Local v2 = new Local(2, "CT2", -20, 0.01);
        Local v3 = new Local(3, "CT3", -89, 1.01);
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addEdge(v1, v2, 50.0);
        graph.addEdge(v1, v3, 200.0);
        graph.addEdge(v2, v3, 150.0);

        double autonomy = 200.0;

        ArrayList<LinkedList<Local>> paths = new ArrayList<>();
        AlgorithmsGraph.allPathsWithAutonomy(graph, v1, v3, new boolean[graph.numVertices()], new LinkedList<>(), paths, autonomy);

        assertEquals(2, paths.size(), "There are should be 2 paths.");
        System.out.println(paths);
    }

    @Test
    void testEmptyGraph() {
        Graph<Local, Double> graph = new MapGraph<>(true);
        ArrayList<LinkedList<Local>> paths = new ArrayList<>();
        AlgorithmsGraph.allPathsWithAutonomy(graph, null, null, new boolean[0], new LinkedList<>(), paths, 100.0);
        assertTrue(paths.isEmpty());
    }
}
