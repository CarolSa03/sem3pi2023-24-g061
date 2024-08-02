package LAPR3.ui;

import BDDAD.ui.*;
import LAPR3.controller.OperationHarvestingRegisterController;
import utils.Utils;

import java.time.LocalDate;

public class OperationHarvestRegisterUI implements Runnable {

    private final OperationsListUI operationsListUI;
    private final ProductsListUI productsListUi;
    private final PlotsListUI plotsListUI;
    private final UnitsListUI unitsListUI;
    private final CropsOfProductUI cropsOfProductUI;
    private final OperationHarvestingRegisterController controller;

    public OperationHarvestRegisterUI() {
        operationsListUI = new OperationsListUI();
        plotsListUI = new PlotsListUI();
        unitsListUI = new UnitsListUI();
        cropsOfProductUI = new CropsOfProductUI();
        productsListUi = new ProductsListUI();
        controller = new OperationHarvestingRegisterController();
    }

    public void run() {
        try {
            System.out.println("Register a new harvest operation");

            Integer operationId = Utils.getNextOperationId();
            System.out.printf("\nOperation Id: %d\n\n", operationId);

            plotsListUI.run();
            Integer plotId = Utils.readIdFromConsole("Plot Id: ", Utils.getIds(plotsListUI.getPlots()));

            LocalDate operationDate = Utils.readDateFromConsole("Operation Date: ");

            productsListUi.run();
            Integer productId = Utils.readIdFromConsole("Product Id: ", Utils.getIds(productsListUi.getProducts()));

            Integer cropId = cropsOfProductUI.getCropsOfProduct(plotId, productId).get(0).getId();
            System.out.printf("\nCrop Id: %d\n\n", cropId);

            Float quantity = Utils.readFloatFromConsole("Amount: ");

            unitsListUI.runQuantityUnits();
            int quantityUnitId = Utils.readIdFromConsole("Amount Unit Id: ", Utils.getIds(unitsListUI.getQuantityUnits()));

            controller.operationHarvestingRegister(operationId, operationDate, plotId, productId, quantity, quantityUnitId, cropId);
            System.out.println("\nHarvest operation registered.");
        } catch (Exception e) {
            System.out.println("\nHarvest operation not registered!\n" + e.getMessage());
        }
    }

}
