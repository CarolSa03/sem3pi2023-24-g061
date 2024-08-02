package ESINF.controller;

import ESINF.ui.InsertHubsFileUI;
import domain.BasketDistribution.BasketDistribution;
import domain.BasketDistribution.BasketDistributionMap;

import java.io.File;

public class BasketDistributionController {

    BasketDistribution basketDistribution = new BasketDistribution();
    BasketDistributionMap basketDistributionMap = new BasketDistributionMap();
    InsertHubsFileUI insertHubsFileUI = new InsertHubsFileUI();

    public void getDistancesList(File file, String path) {
        basketDistribution.createDistancesList(file, path);
    }

    public void getLocalsList(File file, String path) {
        basketDistribution.createLocalsList(file, path);
    }

    public void createBasketDistributionGraph() {
        basketDistributionMap.createMap();
    }

    public void runInsertHubsFileUI() {
        insertHubsFileUI.run();
    }
}
