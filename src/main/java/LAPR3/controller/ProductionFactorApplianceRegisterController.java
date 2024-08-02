package LAPR3.controller;

import BDDAD.dataAccess.repositories.ProductionFactorApplianceRepository;
import BDDAD.dataAccess.repositories.Repositories;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

public class ProductionFactorApplianceRegisterController {
    private ProductionFactorApplianceRepository productionFactorApplianceRepository;

    public ProductionFactorApplianceRegisterController() {
        productionFactorApplianceRepository = getProductionFactorApplianceRepository();
    }

    private ProductionFactorApplianceRepository getProductionFactorApplianceRepository() {
        if (Objects.isNull(productionFactorApplianceRepository)) {
            Repositories repositories = Repositories.getInstance();
            productionFactorApplianceRepository = repositories.getProductionFactorApplianceRepository();
        }
        return productionFactorApplianceRepository;
    }

    public void productionFactorApplianceRegister(Integer operationId, Integer plotId, LocalDate operationDate, Float quantity, Float area, Integer quantityUnitId, Integer areaUnitId, Integer productionFactorId, Integer fertilizationModeId, Integer cropId) throws SQLException {
        productionFactorApplianceRepository.productionFactorApplianceRegister(operationId, plotId, operationDate, quantity, area, quantityUnitId, areaUnitId, productionFactorId, fertilizationModeId, cropId);
    }
}
