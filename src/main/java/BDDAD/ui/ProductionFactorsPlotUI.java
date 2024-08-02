package BDDAD.ui;

import BDDAD.controller.ProductionFactorsPlotController;
import dto.ProductionFactorDTO;
import utils.Utils;

import java.time.LocalDate;
import java.util.List;

public class ProductionFactorsPlotUI implements Runnable {
    private final ProductionFactorsPlotController controller;
    public ProductionFactorsPlotUI() {
        controller = new ProductionFactorsPlotController();
    }
    @Override
    public void run() {
        System.out.println("Production Factors Applied on a specific Plot");
        new PlotsListUI().run();
        Integer plotId = Utils.readIntegerFromConsole("Plot Id: ");
        LocalDate initialDate = Utils.readDateFromConsole("Initial Date: ");
        LocalDate finalDate = Utils.readDateFromConsole("Final Date: ");
        try {
            List<ProductionFactorDTO> productionFactorsList = controller.getProductionFactorsPlot(plotId, initialDate, finalDate);
            for (ProductionFactorDTO productionFactor : productionFactorsList) {
                System.out.println(productionFactor.toStringPlot());
            }
        } catch (Exception e) {
            System.out.println("\nDatabase error while retrieving the production factors list!\n" + e.getMessage());
        }
    }
}
