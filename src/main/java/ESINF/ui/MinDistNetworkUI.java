package ESINF.ui;

import ESINF.controller.MinDistNetworkController;
import ESINF.graph.map.MapGraph;
import domain.BasketDistribution.Local;

public class MinDistNetworkUI implements Runnable {

    private final MinDistNetworkController controller = new MinDistNetworkController();

    public void run() {

        System.out.println("###Minimum Distance Network That Connects All Places###\n");

        controller.loadGraph();

        MapGraph<Local, Double> minDistNetwork = controller.getMinDistNetwork();

        System.out.println(minDistNetwork.toString());
        System.out.printf("\nTotal distance: %.3fkm\n", controller.getTotalDistance());

    }



}
