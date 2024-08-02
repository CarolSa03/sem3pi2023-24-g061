package ESINF.ui;

import ESINF.controller.ClusteringController;
import ESINF.graph.Graph;
import domain.BasketDistribution.BasketDistribution;
import domain.BasketDistribution.Cluster;
import domain.BasketDistribution.Hub;
import domain.BasketDistribution.Local;
import utils.Utils;

import java.util.LinkedList;
import java.util.List;

public class ClusterUI implements Runnable {
    private final ClusteringController controller = new ClusteringController();

    @Override
    public void run() {
        LinkedList<Local> localList = BasketDistribution.getLocalsList();

        System.out.printf("Number of Locals Available - %s\n", localList.size() - 2);
        int numberOfClusters = Utils.readIntegerFromConsole("Number of Clusters: ");
        while (numberOfClusters <= 0 || numberOfClusters > localList.size() - 2 || numberOfClusters > localList.size() / 2) {
            if (numberOfClusters <= 0) {
                System.out.print("Number of Clusters can't be zero or negative. ");
            }
            if (numberOfClusters > localList.size() - 2) {
                System.out.printf("Number of Clusters can't be greater than the number of locals available. [%s] ", localList.size() - 2);
            }
            if (numberOfClusters > localList.size() / 2) {
                System.out.println("Number of Clusters can't be greater than half the number of locals available.");
            }

            System.out.println("Please try again.");
            numberOfClusters = Utils.readIntegerFromConsole("Number of Clusters: ");
        }

        Graph<Local, Double> map = controller.getMap();
        controller.selectHubs(map, numberOfClusters);

        List<Hub> hubsSelected = controller.getHubs();

        System.out.println("Clustering in progress...");

        List<Cluster> clusters = controller.createClusters(hubsSelected, localList);
        if (clusters.isEmpty()) {
            System.out.println("No clusters formed.");
            return;
        }
        System.out.println("Hubs selected:");
        for (Hub hub : hubsSelected) {
            System.out.println(hub.toString());
        }
        System.out.println("Clusters formed:");
        for (Cluster cluster : clusters) {
            System.out.println(cluster);
        }
    }
}

