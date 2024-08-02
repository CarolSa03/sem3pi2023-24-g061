package ESINF.controller;

import ESINF.graph.Graph;
import ESINF.graph.KruskalAlgorithm;
import ESINF.graph.map.MapGraph;
import ESINF.ui.BasketDistributionUI;
import domain.BasketDistribution.BasketDistributionMap;
import domain.BasketDistribution.Local;

public class MinDistNetworkController {

    private Graph<Local, Double> graph;

    private MapGraph<Local, Double> minDistNetwork;

    public void loadGraph() {
        if (BasketDistributionMap.getBasketDistributionMap().numVertices() == 0) new BasketDistributionUI().run();
        graph = BasketDistributionMap.getBasketDistributionMap();
    }

    public MapGraph<Local, Double> getMinDistNetwork() {
        if (minDistNetwork == null) {
            minDistNetwork = KruskalAlgorithm.minDistGraph(graph);
        }
        return minDistNetwork;
    }

    public double getTotalDistance() {
        if (minDistNetwork == null) getMinDistNetwork();
        return KruskalAlgorithm.getTotalDistance();
    }

}
