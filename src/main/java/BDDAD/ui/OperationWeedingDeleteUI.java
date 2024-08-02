package BDDAD.ui;

import BDDAD.controller.OperationWeedingDeleteController;
import utils.Utils;

public class OperationWeedingDeleteUI implements Runnable{
    private final OperationWeedingDeleteController controller = new OperationWeedingDeleteController();
    @Override
    public void run() {
        System.out.println("Delete a weeding operation");
        int operationId = Utils.readIntegerFromConsole("Operation Id: ");
        try {
            controller.operationWeedingDelete(operationId);
            System.out.println("\nWeeding operation deleted.");
        } catch (Exception e) {
            System.out.println("\nError deleting weeding operation: " + e.getMessage());
        }
    }
}
