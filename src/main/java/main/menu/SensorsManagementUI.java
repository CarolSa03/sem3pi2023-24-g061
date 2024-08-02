package main.menu;

import utils.Utils;

import java.util.*;

public class SensorsManagementUI implements Runnable {
    public SensorsManagementUI() {
    }

    @Override
    public void run() {
        LinkedList<MenuItem> options = new LinkedList<>();
        options.add(new MenuItem("Return", new MainMenuUI()));

        int optionSelected;
        do {
            optionSelected = Utils.showAndSelectIndex(options, "\n\nSensors Management Menu");
            options.get(optionSelected).run();
        } while (!options.get(optionSelected).equals(options.getLast()));
    }
}
