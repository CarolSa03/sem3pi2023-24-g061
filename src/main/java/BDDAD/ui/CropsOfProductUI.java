package BDDAD.ui;

import BDDAD.controller.CropsOfProductListController;
import dto.CropDTO;

import java.sql.SQLException;
import java.util.List;

public class CropsOfProductUI {
    private final CropsOfProductListController controller;

    public CropsOfProductUI() {
        controller = new CropsOfProductListController();
    }

    public void run(Integer plotId, Integer productId) {
        try {
            List<CropDTO> crops = controller.getCropsOfProduct(plotId, productId);
            if (crops.isEmpty()) {
                System.out.println("There are no crops in the selected plot giving this product!");
                return;
            } else {
                System.out.println("There are " + crops.size() + " crops in the selected plot giving this product:");
            }
            System.out.printf("%-10s %-20s\n","Crop Id","Designation");
            for (CropDTO c: crops) {
                System.out.printf("%-10d %-20s\n", c.getId(), c.getDesignation());
            }
        } catch (SQLException e) {
            System.out.println("Database not return the crops list!\n" + e.getMessage());
        }
    }

    public List<CropDTO> getCropsOfProduct(Integer plotId, Integer productId) throws SQLException {
        return controller.getCropsOfProduct(plotId, productId);
    }
}
