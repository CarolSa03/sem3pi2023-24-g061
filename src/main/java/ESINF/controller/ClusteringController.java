package ESINF.controller;

import ESINF.graph.Graph;
import domain.BasketDistribution.*;

import java.util.List;


public class ClusteringController {
    ClusterManager manager = new ClusterManager();

    /**
     * Gets the map of the basket distribution
     *
     * @return map of the basket distribution
     */
    public Graph<Local, Double> getMap() {
        return BasketDistributionMap.getBasketDistributionMap();
    }
    public void selectHubs(Graph<Local, Double> map, int numberOfHubs) {
        HubManager.setHubsList(numberOfHubs, new HubComparatorByCollaborators());
    }

    public List<Cluster> createClusters(List<Hub> selectedHubs, List<Local> locals) {
        return manager.createClusters(selectedHubs, locals);
    }

    public List<Hub> getHubs() {
        return HubManager.getHubs();
    }
}
