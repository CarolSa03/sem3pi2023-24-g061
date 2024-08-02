package domain.BasketDistribution;

import ESINF.graph.Edge;
import ESINF.graph.Graph;
import utils.MathUtils;

import java.util.*;

public class ClusterManager {
    /**
     * Gets the distance between two Locals.
     *
     * @param local Local instance.
     * @param hub   Hub instance.
     * @return Distance between two Locals.
     */
    private double getDistanceBetweenLocals(Local local, Hub hub) { // O(1)
        Graph<Local, Double> map = BasketDistributionMap.getBasketDistributionMap();

        Edge<Local, Double> edge = map.edge(local, hub);
        if (edge == null)
            return MathUtils.calculateDistance(local.getLat(), local.getLng(), hub.getLat(), hub.getLng()); // O(1)

        return edge.getWeight();
    }

    /**
     * Filters out Hub instances from a list of Locals.
     *
     * @param selectedHubs List of Hubs, pre-selected.
     * @param filteredList List of Local instances which may include Hubs
     */
    public void filterHubsFromLocals(List<Hub> selectedHubs, List<Local> filteredList) { // O(n * m)
        for(Hub hub : selectedHubs){ // O(n)
            filteredList.removeIf(local -> Objects.equals(local.getId(), hub.getId())); // O(m)
        }
    }

    /**
     * Creates a list of clusters based on a list of selected Hubs and a list of Locals.
     *
     * @param selectedHubs List of Hubs, pre-selected.
     * @param locals       List of Local instances.
     * @return List of Cluster instances.
     */

    public List<Cluster> createClusters(List<Hub> selectedHubs, List<Local> locals) { // O(m + n * m^2)
        List<Cluster> clusters = new ArrayList<>();
        for (Hub hub : selectedHubs) { // O(m)
            clusters.add(new Cluster(hub));
        }
        List<Local> filtered = new ArrayList<>(locals);

        filterHubsFromLocals(selectedHubs, filtered);

        for (Local local : filtered) { // // O(N * M^2)
            Hub nearestHub = findNearestHub(local, selectedHubs); // O(m)
            for (Cluster cluster : clusters) { // O(m)
                if (cluster.getHub().equals(nearestHub)) {
                    cluster.addLocal(local);
                }
            }
        }

        return clusters;
    }

    /**
     * Finds the nearest Hub to a Local instance.
     *
     * @param local        Local instance.
     * @param selectedHubs List of Hubs, pre-selected.
     * @return Hub instance.
     */

    private Hub findNearestHub(Local local, List<Hub> selectedHubs) { // O(m)
        double minDistance = Double.MAX_VALUE;
        Hub nearestHub = null;
        for (Hub potentialHub : selectedHubs) { // O(m)
            double distance = getDistanceBetweenLocals(local, potentialHub);
            if (distance < minDistance) {
                minDistance = distance;
                nearestHub = potentialHub;
            }
        }
        return nearestHub;
    }
}
