package domain.BasketDistribution;

import ESINF.graph.Graph;
import ESINF.graph.map.MapGraph;

import java.util.LinkedList;

public class BasketDistributionMap {

    public static final Graph<Local, Double> basketDistributionMap = new MapGraph<>(false);

    /**
     * Gets basket distribution map.
     *
     * @return the basket distribution map
     */
    public static Graph<Local, Double> getBasketDistributionMap() {
        return basketDistributionMap;
    }

    /**
     * Create map.
     */
    public void createMap() {
        for (Local local1 : BasketDistribution.getLocalsList()) { // O(n);
            if (!basketDistributionMap.containsVertex(local1)) {
                basketDistributionMap.addVertex(local1);
                for (Distance distance : BasketDistribution.getDistancesList()) {  // O(n);
                    Local local2 = getLocalFromDistance(distance, BasketDistribution.getLocalsList());
                    if (distance.getLocal1().equals(local1.getDesignation())) {
                        if (!basketDistributionMap.containsVertex(local2)) {
                            basketDistributionMap.addVertex(local2);
                        }
                        basketDistributionMap.addEdge(local1, local2, distance.getLength());
                    }
                }
            }
        }

    }

    private Local getLocalFromDistance(Distance distance, LinkedList<Local> locals) {
        for (Local local : locals) { // O(n);
            if (local.getDesignation().equals(distance.getLocal2())) {
                return local;
            }
        }

        return new Local();
    }
}
