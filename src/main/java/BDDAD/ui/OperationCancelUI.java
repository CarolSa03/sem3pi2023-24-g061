package BDDAD.ui;

import BDDAD.controller.OperationCancelController;
import utils.Utils;

public class OperationCancelUI implements Runnable {
    private final OperationCancelController controller = new OperationCancelController();


    @Override
    public void run() {
        System.out.println("Cancel an operation.\n Operations can only be canceled if the operation has happened in the last 3 days (maximum) and there are no dependencies on the operation.");

        int operationId = Utils.readIntegerFromConsole("Operation Id: ");

        try {
            controller.operationCancel(operationId);
            System.out.println("\nOperation cancelled.");
        } catch (Exception e) {
            System.out.println("\nError canceling operation: " + e.getMessage());
        }
    }
}
