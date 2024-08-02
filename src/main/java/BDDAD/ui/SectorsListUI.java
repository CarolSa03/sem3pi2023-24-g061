package BDDAD.ui;

import BDDAD.controller.SectorsListController;
import dto.SectorDTO;

import java.sql.SQLException;
import java.util.List;

public class SectorsListUI implements Runnable {

    private SectorsListController controller;

    public SectorsListUI() {
        controller = new SectorsListController();
    }

    public void run() {
        try {
            System.out.println("List of Sectors");
            List<SectorDTO> sectors = controller.getSectors();
            System.out.printf("%-15s\n","Sector Id");
            for (SectorDTO s: sectors) {
                System.out.printf("%-15d\n", s.getId());
            }
        } catch (SQLException e) {
            System.out.println("Database not return the sectors list!\n" + e.getMessage());
        }
    }

    public List<SectorDTO> getSectors() throws SQLException {
        return controller.getSectors();
    }
}
