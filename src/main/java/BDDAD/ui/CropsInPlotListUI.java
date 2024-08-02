package BDDAD.ui;

import BDDAD.controller.CropsInPlotListController;
import dto.CropDTO;

import java.sql.SQLException;
import java.util.List;

public class CropsInPlotListUI {

    private CropsInPlotListController controller;

    public CropsInPlotListUI() {
        controller = new CropsInPlotListController();
    }

    public void run(Integer plotId) {
        try {
            System.out.println("List of Crops in Selected Plot");
            List<CropDTO> crops = controller.getCropsInPlot(plotId);
            System.out.printf("%-10s %-20s\n","Crop Id","Designation");
            for (CropDTO c: crops) {
                System.out.printf("%-10d %-20s\n", c.getId(), c.getDesignation());
            }
        } catch (SQLException e) {
            System.out.println("Database not return the crops list!\n" + e.getMessage());
        }
    }

    public List<CropDTO> getCropsInPlot(Integer plotId) throws SQLException {
        return controller.getCropsInPlot(plotId);
    }
}
