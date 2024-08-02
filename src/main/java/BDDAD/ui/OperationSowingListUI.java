package BDDAD.ui;

import BDDAD.controller.OperationSowingListController;
import dto.OperationSowingDTO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class OperationSowingListUI implements Runnable {
    private OperationSowingListController controller;

    public OperationSowingListUI() {
        controller = new OperationSowingListController();
    }

    public void run() {
        try {
            System.out.println("###List of Sowing Operations###\n");
            Integer pPlotId = 105;
            LocalDate pInitialDate = LocalDate.of(2019, 1, 1);
            LocalDate pFinalDate = LocalDate.of(2025, 7, 6);
            List<OperationSowingDTO> operationSowings = controller.getSowingOperations(pPlotId, pInitialDate, pFinalDate);
            System.out.printf("%s, %s, %s\n\n", "Operation Date", "Amount", "Unit", "Crop", "Plot");
            for (OperationSowingDTO p: operationSowings) {
                System.out.println(p.toString());
            }
        } catch (SQLException e) {
            System.out.println("Database not return the sowing operation list!\n" + e.getMessage());
        }
    }
}
