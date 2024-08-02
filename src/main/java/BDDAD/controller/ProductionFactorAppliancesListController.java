package BDDAD.controller;

import BDDAD.dataAccess.repositories.ProductionFactorApplianceRepository;
import BDDAD.dataAccess.repositories.Repositories;
import dto.ProductionFactorApplianceDTO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class ProductionFactorAppliancesListController {
    private ProductionFactorApplianceRepository productionFactorApplianceRepository;

    public ProductionFactorAppliancesListController() {
        productionFactorApplianceRepository = getProductionFactorApplianceRepository();
    }

    private ProductionFactorApplianceRepository getProductionFactorApplianceRepository() {
        if (Objects.isNull(productionFactorApplianceRepository)) {
            Repositories repositories = Repositories.getInstance();
            productionFactorApplianceRepository = repositories.getProductionFactorApplianceRepository();
        }
        return productionFactorApplianceRepository;
    }

    public List<ProductionFactorApplianceDTO> getProductionFactorAppliances(Integer pPlotId, LocalDate pInitialDate, LocalDate pFinalDate) throws SQLException {
        return productionFactorApplianceRepository.getProductionFactorAppliances(pPlotId, pInitialDate, pFinalDate);
    }
}
