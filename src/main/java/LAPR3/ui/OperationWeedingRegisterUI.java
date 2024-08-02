package LAPR3.ui;

import BDDAD.ui.*;
import LAPR3.controller.OperationWeedingRegisterController;
import utils.Utils;

import java.sql.SQLException;
import java.time.LocalDate;

public class OperationWeedingRegisterUI implements Runnable {
    private final OperationWeedingRegisterController controller;
    private final OperationsListUI operationsListUI;
    private final PlotsListUI plotsListUI;
    private final UnitsListUI unitsListUI;
    private final CropsInPlotListUI cropsInPlotListUI;

    public OperationWeedingRegisterUI() {
        operationsListUI = new OperationsListUI();
        plotsListUI = new PlotsListUI();
        unitsListUI = new UnitsListUI();

        cropsInPlotListUI = new CropsInPlotListUI();
        controller = new OperationWeedingRegisterController();
    }

    public void run() {
        try {
            System.out.println("Register a new weeding operation");

            int operationId = Utils.getNextOperationId();
            System.out.printf("\nOperation Id: %d\n\n", operationId);

            LocalDate operationDate = Utils.readDateFromConsole("Operation Date: ");

            plotsListUI.run();
            int plotId = Utils.readIdFromConsole("Plot Id: ", Utils.getIds(plotsListUI.getPlots()));

            cropsInPlotListUI.run(plotId);
            int cropId = Utils.readIdFromConsole("Crop Id: ", Utils.getIds(cropsInPlotListUI.getCropsInPlot(plotId)));


            double value = Utils.readDoubleFromConsole("Value: ");
            while (value <= 0) {
                System.out.print("\nValue must be greater than 0.\n");
                value = Utils.readDoubleFromConsole("Value: ");
            }

            unitsListUI.runQuantityUnits();
            Integer unitId = Utils.readIdFromConsole("Unit Id: ", Utils.getIds(unitsListUI.getQuantityUnits()));

            try {
                controller.operationWeedingRegister(operationId, operationDate, plotId, cropId, value, unitId);
                System.out.println("\nWeeding operation registered.");
            } catch (SQLException e) {
                System.out.println("\nError registering weeding operation: " + e.getMessage());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
