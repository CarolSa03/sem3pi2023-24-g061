package BDDAD.controller;

import BDDAD.dataAccess.repositories.ProductionFactorApplianceRepository;
import BDDAD.dataAccess.repositories.Repositories;

import java.sql.SQLException;
import java.util.Objects;

public class ProductionFactorApplianceDeleteController {
    private ProductionFactorApplianceRepository productionFactorApplianceRepository;

    public ProductionFactorApplianceDeleteController() {
        productionFactorApplianceRepository = getProductionFactorApplianceRepository();
    }

    private ProductionFactorApplianceRepository getProductionFactorApplianceRepository() {
        if (Objects.isNull(productionFactorApplianceRepository)) {
            Repositories repositories = Repositories.getInstance();
            productionFactorApplianceRepository = repositories.getProductionFactorApplianceRepository();
        }
        return productionFactorApplianceRepository;
    }

    public int productionFactorApplianceDelete(Integer operationId) throws SQLException {
        return productionFactorApplianceRepository.productionFactorApplianceDelete(operationId);
    }
}
