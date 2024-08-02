package LAPR3.ui;

import BDDAD.ui.*;
import LAPR3.controller.ProductionFactorApplianceRegisterController;
import dto.ProductionFactorDTO;
import utils.Utils;

import java.time.LocalDate;

public class ProductionFactorApplianceRegisterUI implements Runnable {

    private final OperationsListUI operationsListUI;
    private final PlotsListUI plotsListUI;
    private final UnitsListUI unitsListUI;
    private final ProductionFactorsListUI productionFactorsListUI;
    private final FertilizationModesListUI fertilizationModesListUI;
    private final CropsInPlotListUI cropsInPlotListUI;
    private final ProductionFactorApplianceRegisterController controller;

    public ProductionFactorApplianceRegisterUI() {
        operationsListUI = new OperationsListUI();
        plotsListUI = new PlotsListUI();
        unitsListUI = new UnitsListUI();
        productionFactorsListUI = new ProductionFactorsListUI();
        fertilizationModesListUI = new FertilizationModesListUI();
        cropsInPlotListUI = new CropsInPlotListUI();
        controller = new ProductionFactorApplianceRegisterController();
    }

    public void run() {
        try {
            System.out.println("Register a new production factor appliance operation");

            Integer operationId = Utils.getNextOperationId();
            System.out.printf("\nOperation Id: %d\n\n", operationId);

            plotsListUI.run();
            Integer plotId = Utils.readIdFromConsole("Plot Id: ", Utils.getIds(plotsListUI.getPlots()));

            LocalDate operationDate = Utils.readDateFromConsole("Operation Date: ");

            Float quantity = 0F;
            Float area = 0F;
            Integer quantityUnitId = 0;
            Integer areaUnitId = 1;
            if (Utils.confirm("Do you want to add an area value (in ha) for this operation (Yes/No)?\nTake in mind that the area can't be greater than the plot's area.")) {
                area = Utils.readFloatFromConsole("Area value (in ha): ");
            }

            if (Utils.confirm("Do you want to add a quantity value (kg or un) for this operation (Yes/No)?")) {
                quantity = Utils.readFloatFromConsole("Quantity value: ");
                unitsListUI.runQuantityUnits();
                quantityUnitId = Utils.readIdFromConsole("Quantity Unit Id: ", Utils.getIds(unitsListUI.getQuantityUnits()));
            }

            productionFactorsListUI.run();
            Integer productionFactorId = Utils.readIdFromConsole("Production Factor Id: ", Utils.getIds(productionFactorsListUI.getProductionFactors()));
            ProductionFactorDTO productionFactor = productionFactorsListUI.getProductionFactors().stream().filter(p -> p.getId().equals(productionFactorId)).findFirst().orElse(null);

            Integer fertilizationModeId = 0;
            Integer cropId = 0;

            if (productionFactor.getType().equalsIgnoreCase("Adubo")) {
                fertilizationModesListUI.run();
                fertilizationModeId = Utils.readIdFromConsole("Fertilization Mode Id: ", Utils.getIds(fertilizationModesListUI.getFertilizationModes()));
                if (Utils.confirm("Is the fertilization made on a crop (Yes/No)?")) {
                    cropsInPlotListUI.run(plotId);
                    cropId = Utils.readIdFromConsole("Crop Id: ", Utils.getIds(cropsInPlotListUI.getCropsInPlot(plotId)));
                }
            }

            controller.productionFactorApplianceRegister(operationId, plotId, operationDate, quantity, area, quantityUnitId, areaUnitId, productionFactorId, fertilizationModeId, cropId);
            System.out.println("\nProduction factor appliance operation registered.");
        } catch (Exception e) {
            System.out.println("\nProduction factor appliance operation not registered!\n" + e.getMessage());
        }
    }
}
