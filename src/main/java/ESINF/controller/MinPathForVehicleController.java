package ESINF.controller;

import ESINF.graph.Graph;
import ESINF.ui.BasketDistributionUI;
import domain.BasketDistribution.BasketDistributionMap;
import domain.BasketDistribution.Local;
import domain.BasketDistribution.VehiclePathFinder;

public class MinPathForVehicleController {

    private Graph<Local, Double> graph;
    VehiclePathFinder vehiclePathFinder = new VehiclePathFinder();

    public void loadGraph() {
        if (BasketDistributionMap.getBasketDistributionMap().numVertices() == 0) new BasketDistributionUI().run();
        graph = BasketDistributionMap.getBasketDistributionMap();
    }
    public void getMinPath() {
        vehiclePathFinder.determineMinimumCourseWithAutonomy(graph);
    }
}
