package BDDAD.ui;

import BDDAD.controller.ProductionFactorApplianceDeleteController;
import utils.Utils;

import java.sql.SQLException;

public class ProductionFactorApplianceDeleteUI implements Runnable {
    private ProductionFactorApplianceDeleteController controller;

    public ProductionFactorApplianceDeleteUI() {
        controller = new ProductionFactorApplianceDeleteController();
    }

    public void run() {
        try {
            System.out.println("Delete a production factor appliance operation");

            Integer operationId = Utils.readIntegerFromConsole("OperationId: ");

            controller.productionFactorApplianceDelete(operationId);
            System.out.println("\nProduction factor appliance operation deleted.");
        } catch (SQLException e ) {
            System.out.println("\nProduction factor appliance operation not deleted!\n" + e.getMessage());
        }
    }
}
