package BDDAD.ui;

import BDDAD.controller.OperationWeedingListController;
import dto.OperationWeedingDTO;
import utils.Utils;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;


public class OperationWeedingListUI implements Runnable {
    private final OperationWeedingListController controller;

    public OperationWeedingListUI() {
        controller = new OperationWeedingListController();
    }

    @Override
    public void run() {
        try {
            System.out.println("### List of Weeding Operations ###\n");
            LocalDate initialDate = Utils.readDateFromConsole("Initial Date: ");
            LocalDate finalDate = Utils.readDateFromConsole("Final Date: ");
            List<OperationWeedingDTO> weedingOperationsList = controller.getWeedingOperations(initialDate, finalDate);
            if (weedingOperationsList.isEmpty()) {
                System.out.println("No weeding operations found!");
            } else {
                System.out.printf("%-10s %-10s %-10s %-10s %-10s\n\n", "Operation Date", "Plot Id", "Crop Id", "Value", "Unit");
                for (OperationWeedingDTO w : weedingOperationsList) {
                    System.out.println(w.toString());
                }
            }
        } catch (SQLException e) {
            System.out.println("\nDatabase error while retrieving the weeding operations list!\n" + e.getMessage());
        }
    }
}
