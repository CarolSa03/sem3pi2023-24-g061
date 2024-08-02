package BDDAD.ui;

import BDDAD.controller.PlotsListController;
import dto.PlotDTO;

import java.sql.SQLException;
import java.util.List;

public class PlotsListUI implements Runnable {

    private PlotsListController controller;

    public PlotsListUI() {
        controller = new PlotsListController();
    }

    public void run() {
        try {
            System.out.println("List of Plots");
            List<PlotDTO> plots = controller.getPlots();
            System.out.printf("%-12s %-20s %-18s\n","Plot Id","Designation","Area (in Ha)");
            for (PlotDTO p: plots) {
                System.out.printf("%-12d %-20s %-17.1f\n", p.getId(), p.getDesignation(), p.getArea());
            }
        } catch (SQLException e) {
            System.out.println("Database not return the plots list!\n" + e.getMessage());
        }
    }

    public List<PlotDTO> getPlots() throws SQLException {
        return controller.getPlots();
    }
}
