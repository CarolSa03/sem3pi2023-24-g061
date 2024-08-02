package main.menu;

import BDDAD.ui.ExitUI;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainMenuUI implements Runnable {

    public MainMenuUI() {
    }

    @Override
    public void run() {
        List<MenuItem> options = new ArrayList<>();

        options.add(new MenuItem("Basket Management", new BasketManagementUI()));
        options.add(new MenuItem("Sensors Management", new SensorsManagementUI()));
        options.add(new MenuItem("Farm Operation Management [Data Base]", new FarmOperationManagementUI()));
        options.add(new MenuItem("Farm Coordination", new FarmCoordinationUI()));
        options.add(new MenuItem("Exit", new ExitUI()));

        int optionSelected = Utils.showAndSelectIndex(options, "\n\nMain Menu");
        options.get(optionSelected).run();
    }
}