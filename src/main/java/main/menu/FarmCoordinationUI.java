package main.menu;

import LAPR3.ui.IrrigationSystemUI;
import LAPR3.ui.ShowFieldbookUI;
import utils.Utils;

import java.util.*;

public class FarmCoordinationUI implements Runnable {
    public FarmCoordinationUI() {
    }

    @Override
    public void run() {
        LinkedList<MenuItem> options = new LinkedList<>();
        options.add(new MenuItem("Irrigation System Controller Simulation", new IrrigationSystemUI()));
        options.add(new MenuItem("Show Fieldbook Content", new ShowFieldbookUI()));
        options.add(new MenuItem("Return", new MainMenuUI()));

        int optionSelected;
        do {
            optionSelected = Utils.showAndSelectIndex(options, "\n\nFarm Coordination Menu");
            options.get(optionSelected).run();
        } while (!options.get(optionSelected).equals(options.getLast()));
    }
}
