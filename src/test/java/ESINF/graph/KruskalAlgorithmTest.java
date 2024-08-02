package ESINF.graph;

import ESINF.graph.map.MapGraph;
import domain.BasketDistribution.BasketDistribution;
import domain.BasketDistribution.BasketDistributionMap;
import domain.BasketDistribution.Distance;
import domain.BasketDistribution.Local;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class KruskalAlgorithmTest {

    private Graph<Local, Double> graph = new MapGraph<>(false);
    private final BasketDistributionMap basketDistributionMap = new BasketDistributionMap();
    private final BasketDistribution basketDistribution = new BasketDistribution();
    private LinkedList<Distance> distances = new LinkedList<>();
    private LinkedList<Local> locals = new LinkedList<>();

    @BeforeEach
    void setUp() {
//        distances = basketDistribution.getDistancesList("docs/Data/tests_data/distancias_test.csv", distances);
//        locals = basketDistribution.getLocalsList("docs/Data/tests_data/locais_test.csv", locals);
        basketDistributionMap.createMap();
        graph = BasketDistributionMap.getBasketDistributionMap();
    }

    @Test
    public void testMinDistGraph() {
        MapGraph<Local, Double> minDistGraph = KruskalAlgorithm.minDistGraph(graph);
        assertNotNull(minDistGraph);
        assertEquals(5, minDistGraph.numVertices());
        assertEquals(8, minDistGraph.numEdges());
        assertEquals(0.095, KruskalAlgorithm.getTotalDistance());
    }

}
