package BDDAD.ui;

import BDDAD.controller.OperationSowingDeleteController;
import utils.Utils;

public class OperationSowingDeleteUI implements Runnable {

        private final OperationSowingDeleteController controller = new OperationSowingDeleteController();

        public void run() {
            System.out.println("Delete a sowing operation");

             int operationId = Utils.readIntegerFromConsole("Operation Id: ");

            try {
                controller.operationSowingDelete(operationId);
                System.out.println("\nSowing operation deleted.");
            } catch (Exception e) {
                System.out.println("\nError deleting sowing operation: " + e.getMessage());
            }
        }
}
