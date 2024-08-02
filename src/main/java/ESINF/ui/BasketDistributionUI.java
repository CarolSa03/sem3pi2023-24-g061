package ESINF.ui;

import ESINF.controller.BasketDistributionController;
import utils.Utils;

import java.io.File;

import static domain.BasketDistribution.BasketDistributionMap.basketDistributionMap;

public class BasketDistributionUI implements Runnable {

    private final BasketDistributionController basketDistributionController = new BasketDistributionController();
    private static File distancesFile = new File("distancias_small.csv");
    private static File localsFile = new File("locais_small.csv");

    public void run() {
        System.out.println("Basket Distribution Graph Creation");

        if (!(basketDistributionMap.numVertices() == 0) && !(basketDistributionMap.numEdges() == 0)) {
            System.out.println("\nBasket distribution graph already exists.\n");
            return;
        }

        boolean existHubsFile = Utils.confirm("\nDo you want to use an existent hubs file? [YES/NO]");

        if (existHubsFile) {
            distancesFile = new File("distancias_big.csv");
            localsFile = new File("locais_big.csv");
        }

        getList();

        System.out.println("Creating basket distribution graph...");
        basketDistributionController.createBasketDistributionGraph();
        System.out.println("\nBasket distribution graph created successfully.");

        if (existHubsFile) {
            basketDistributionController.runInsertHubsFileUI();
            System.out.println("\nHubs inserted successfully.\n");
        }

    }

    private void getList() {
        basketDistributionController.getDistancesList(distancesFile, "docs/Data/");
        basketDistributionController.getLocalsList(localsFile, "docs/Data/");
    }

}
