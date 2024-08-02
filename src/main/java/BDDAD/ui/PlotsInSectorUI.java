package BDDAD.ui;

import BDDAD.controller.PlotsInSectorController;
import dto.PlotDTO;

import java.sql.SQLException;
import java.util.List;

public class PlotsInSectorUI {

    private PlotsInSectorController controller;

    public PlotsInSectorUI() {
        controller = new PlotsInSectorController();
    }

    public void run(Integer sectorId) {
        try {
            System.out.println("List of Plots in Selected Sector");
            List<PlotDTO> plots = controller.getPlots(sectorId);
            System.out.printf("%-12s %-20s %-18s\n","Plot Id","Designation","Area (in Ha)");
            for (PlotDTO p: plots) {
                System.out.printf("%-12d %-20s %-17.1f\n", p.getId(), p.getDesignation(), p.getArea());
            }
        } catch (SQLException e) {
            System.out.println("Database not return the plots list!\n" + e.getMessage());
        }
    }

    public List<PlotDTO> getPlots(Integer sectorId) throws SQLException {
        return controller.getPlots(sectorId);
    }

}
