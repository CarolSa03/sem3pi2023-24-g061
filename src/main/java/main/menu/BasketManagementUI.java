package main.menu;

import ESINF.ui.*;
import utils.Utils;

import java.util.*;

public class BasketManagementUI implements Runnable {
    public BasketManagementUI() {
    }

    @Override
    public void run() {
        LinkedList<MenuItem> options = new LinkedList<>();

        options.add(new MenuItem("Create Basket Distribution Graph", new BasketDistributionUI()));
        options.add(new MenuItem("Return", new MainMenuUI()));

        int optionSelected;
        do {
            optionSelected = Utils.showAndSelectIndex(options, "\n\nBasket Management Menu");
            options.get(optionSelected).run();

            options.clear();
            options.add(new MenuItem("Minimum Distance Network", new MinDistNetworkUI()));
            options.add(new MenuItem("Clustering", new ClusterUI()));
            options.add(new MenuItem("Best Route with N-Hubs", new MaxHubsRouteUI()));
            options.add(new MenuItem("Shortest Delivery Route", new ShortestDeliveryRouteUI()));
            options.add(new MenuItem("Return", new MainMenuUI()));

        } while (!options.get(optionSelected).equals(options.getLast()));
    }
}
