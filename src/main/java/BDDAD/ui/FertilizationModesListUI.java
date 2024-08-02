package BDDAD.ui;

import BDDAD.controller.FertilizationModesListController;
import dto.FertilizationModeDTO;

import java.sql.SQLException;
import java.util.List;

public class FertilizationModesListUI implements Runnable {
    private FertilizationModesListController controller;

    public FertilizationModesListUI() {
        controller = new FertilizationModesListController();
    }

    public void run() {
        try {
            System.out.println("List of Fertilization Modes");
            List<FertilizationModeDTO> fertilizationModes = controller.getFertilizationModes();
            System.out.printf("%-20s %-20s\n","Fertilization Mode Id","Designation");
            for (FertilizationModeDTO fm: fertilizationModes) {
                System.out.printf("%-20d %-20s\n", fm.getId(), fm.getDesignation());
            }
        } catch (SQLException e) {
            System.out.println("Database not return the fertilization modes list!\n" + e.getMessage());
        }
    }

    public List<FertilizationModeDTO> getFertilizationModes() throws SQLException {
        return controller.getFertilizationModes();
    }
}
