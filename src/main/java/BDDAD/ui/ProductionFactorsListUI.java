package BDDAD.ui;

import BDDAD.controller.ProductionFactorsListController;
import dto.ProductionFactorDTO;

import java.sql.SQLException;
import java.util.List;

public class ProductionFactorsListUI implements Runnable {
    private ProductionFactorsListController controller;

    public ProductionFactorsListUI() {
        controller = new ProductionFactorsListController();
    }

    @Override
    public void run() {
        try {
            System.out.println("List of Production Factors");
            List<ProductionFactorDTO> productionFactors = controller.getProductionFactors();
            System.out.printf("%-20s %-30s %-20s\n","Production Factor Id","Designation","Type of Production Factor");
            for (ProductionFactorDTO pf: productionFactors) {
                System.out.printf("%-20d %-30s %-20s\n", pf.getId(), pf.getDesignation(), pf.getType());
            }
        } catch (SQLException e) {
            System.out.println("Database not return the plots list!\n" + e.getMessage());
        }
    }

    public List<ProductionFactorDTO> getProductionFactors() throws SQLException {
        return controller.getProductionFactors();
    }
}
