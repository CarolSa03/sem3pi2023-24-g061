package LAPR3.ui;

import BDDAD.ui.CropsInPlotListUI;
import BDDAD.ui.OperationsListUI;
import BDDAD.ui.PlotsListUI;
import BDDAD.ui.UnitsListUI;
import LAPR3.controller.OperationSowingRegisterController;
import dto.PlotDTO;
import utils.Utils;

import java.time.LocalDate;
import java.util.List;

public class OperationSowingRegisterUI implements Runnable {

    private final OperationsListUI operationsListUI;
    private final PlotsListUI plotsListUI;
    private final UnitsListUI unitsListUI;
    private final CropsInPlotListUI cropsInPlotListUI;
    private final OperationSowingRegisterController controller;

    public OperationSowingRegisterUI() {
        operationsListUI = new OperationsListUI();
        plotsListUI = new PlotsListUI();
        unitsListUI = new UnitsListUI();
        cropsInPlotListUI = new CropsInPlotListUI();
        controller = new OperationSowingRegisterController();
    }

    public void run() {
        try {
            System.out.println("Register a new sowing operation");

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

                List<PlotDTO> plots = plotsListUI.getPlots();

                PlotDTO selectedPlot = plots.stream()
                        .filter(plot -> plot.getId().equals(plotId))
                        .findFirst()
                        .orElse(null);

                if (selectedPlot != null) {
                    float plotArea = selectedPlot.getArea();
                    while (area > plotArea) {
                        System.out.println("The area can't be greater than the plot's area (" + plotArea + " ha).");
                        area = Utils.readFloatFromConsole("Area value (in ha): ");
                    }
                }
            }

            if (Utils.confirm("Do you want to add a quantity value (kg or un) for this operation (Yes/No)?")) {
                quantity = Utils.readFloatFromConsole("Quantity value: ");
                unitsListUI.runQuantityUnits();
                quantityUnitId = Utils.readIdFromConsole("Quantity Unit Id: ", Utils.getIds(unitsListUI.getQuantityUnits()));
            }

            cropsInPlotListUI.run(plotId);
            Integer cropId = Utils.readIdFromConsole("Crop Id: ", Utils.getIds(cropsInPlotListUI.getCropsInPlot(plotId)));

            controller.operationSowingRegister(operationId, operationDate, plotId, quantity, area, quantityUnitId, areaUnitId, cropId);
            System.out.println("\nSowing operation registered.");
        } catch (Exception e) {
            System.out.println("\nSowing operation not registered!\n" + e.getMessage());
        }
    }

}
