package main.menu;


import BDDAD.ui.*;
import LAPR3.ui.*;
import utils.Utils;

import java.util.LinkedList;

public class FarmOperationManagementUI implements Runnable {
    public FarmOperationManagementUI() {
    }

    @Override
    public void run() {
        LinkedList<MenuItem> options = new LinkedList<>();
        options.add(new MenuItem("Database Connection Test", new DatabaseConnectionTestUI()));
        options.add(new MenuItem("Harvest Operation Register", new OperationHarvestRegisterUI()));
        options.add(new MenuItem("Production Factor Appliance Operation Register", new ProductionFactorApplianceRegisterUI()));
        options.add(new MenuItem("Production Factor Appliance Operation List", new ProductionFactorAppliancesListUI()));
        options.add(new MenuItem("Weeding Operation Register", new OperationWeedingRegisterUI()));
        options.add(new MenuItem("Weeding Operation List", new OperationWeedingListUI()));
        options.add(new MenuItem("Sowing Operation Register", new OperationSowingRegisterUI()));
        options.add(new MenuItem("Sowing Operation List", new OperationSowingListUI()));
        options.add(new MenuItem("Production Factors on Plot List", new ProductionFactorsPlotUI()));
        options.add(new MenuItem("Recipe Register", new RecipeRegisterUI()));
        options.add(new MenuItem("Irrigation Operation Register", new OperationIrrigationRegisterUI()));
        options.add(new MenuItem("Operation Cancel", new OperationCancelUI()));
        options.add(new MenuItem("Return", new MainMenuUI()));

        int optionSelected;
        do {
            optionSelected = Utils.showAndSelectIndex(options, "\n\nFarm Operation Management Menu");
            options.get(optionSelected).run();
        } while (!options.get(optionSelected).equals(options.getLast()));

    }
}
