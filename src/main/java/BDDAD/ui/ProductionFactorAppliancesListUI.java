package BDDAD.ui;

import BDDAD.controller.ProductionFactorAppliancesListController;
import dto.ProductionFactorApplianceDTO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ProductionFactorAppliancesListUI implements Runnable {
    private ProductionFactorAppliancesListController controller;

    public ProductionFactorAppliancesListUI() {
        controller = new ProductionFactorAppliancesListController();
    }

    public void run() {
        try {
            System.out.println("###List of Production Factor Appliances###\n");
            Integer pPlotId = 105;
            LocalDate pInitialDate = LocalDate.of(2019, 1, 1);
            LocalDate pFinalDate = LocalDate.of(2023, 7, 6);
            List<ProductionFactorApplianceDTO> productionFactorAppliances = controller.getProductionFactorAppliances(pPlotId, pInitialDate, pFinalDate);
            System.out.printf("%s, %s, %s\n\n", "Operation Date", "Production Factor", "Crop");
            for (ProductionFactorApplianceDTO p: productionFactorAppliances) {
                System.out.println(p.toString());
            }
        } catch (SQLException e) {
            System.out.println("Database not return the production factor appliances list!\n" + e.getMessage());
        }
    }
}
