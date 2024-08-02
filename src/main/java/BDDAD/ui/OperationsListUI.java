package BDDAD.ui;

import BDDAD.controller.OperationsListController;
import dto.OperationDTO;

import java.sql.SQLException;
import java.util.List;

public class OperationsListUI implements Runnable {

    private OperationsListController controller;

    public OperationsListUI() {
        controller = new OperationsListController();
    }

    public void run() {
        try {
            System.out.println("List of Operations");
            List<OperationDTO> operations = controller.getOperations();
            System.out.printf("%-13s %-15s %-13s\n","Operation Id","Plot Id","Operation Date");
            for (OperationDTO o: operations) {
                String operationDateString = o.getOperationDate() == null ? "" : o.getOperationDate().getDayOfMonth() + "/" + o.getOperationDate().getMonthValue() + "/" + o.getOperationDate().getYear();
                System.out.printf("%-13d %-15s %-13s\n", o.getId(), o.getPlotId(), operationDateString);
            }
        } catch (SQLException e) {
            System.out.println("Database not return the operations list!\n" + e.getMessage());
        }
    }

    public List<OperationDTO> getOperations() throws SQLException {
        return controller.getOperations();
    }
}
