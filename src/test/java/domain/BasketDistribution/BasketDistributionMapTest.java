package domain.BasketDistribution;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ESINF.graph.Graph;

import java.io.File;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Basket distribution map test.
 */
class BasketDistributionMapTest {
    /**
     * The Basket distribution.
     */
    BasketDistribution basketDistribution = new BasketDistribution();
    /**
     * The Basket distribution map.
     */
    BasketDistributionMap basketDistributionMap = new BasketDistributionMap();
    /**
     * The Distances.
     */
    LinkedList<Distance> distances = new LinkedList<>();
    /**
     * The Locals.
     */
    LinkedList<Local> locals = new LinkedList<>();


    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {

        basketDistribution.createDistancesList(new File("docs/Data/tests_data/distancias_test.csv"), "docs/Data/tests_data/");
        basketDistribution.createLocalsList(new File("tests_data/locais_test.csv"), "docs/Data/tests_data/");

        distances = BasketDistribution.getDistancesList();
        locals = BasketDistribution.getLocalsList();
    }

    /**
     * Test create map.
     */
    @Test
    void testCreateMap() {

        // Perform the map creation
        basketDistributionMap.createMap();

        // Get the generated map
        Graph<Local, Double> generatedMap = BasketDistributionMap.getBasketDistributionMap();

        //Asserts
        assertNotNull(generatedMap); // Assert that the generated map is not null
        assertTrue(generatedMap.containsVertex(locals.get(0))); // Checking if the first local is in the map
        assertTrue(generatedMap.containsVertex(locals.get(1))); // Checking if the second local is in the map
        assertTrue(generatedMap.containsVertex(locals.get(2))); // Checking if the third local is in the map
        assertTrue(generatedMap.containsVertex(locals.get(3))); // Checking if the fourth local is in the map
        assertTrue(generatedMap.containsVertex(locals.get(4))); // Checking if the fifth local is in the map
        assertEquals(8, generatedMap.edges().size()); // Checking the total number of edges in the map
        assertEquals(5, generatedMap.vertices().size()); // Checking the total number of vertices in the map
    }
}