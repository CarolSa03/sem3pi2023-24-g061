package BDDAD.controller;

import BDDAD.dataAccess.repositories.ProductionFactorRepository;
import BDDAD.dataAccess.repositories.Repositories;
import dto.ProductionFactorDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class ProductionFactorsListController {
    private ProductionFactorRepository productionFactorRepository;

    public ProductionFactorsListController() {
        productionFactorRepository = getProductionFactorRepository();
    }

    private ProductionFactorRepository getProductionFactorRepository() {
        if (Objects.isNull(productionFactorRepository)) {
            Repositories repositories = Repositories.getInstance();
            productionFactorRepository = repositories.getProductionFactorRepository();
        }
        return productionFactorRepository;
    }

    public List<ProductionFactorDTO> getProductionFactors() throws SQLException {
        return productionFactorRepository.getProductionFactors();
    }
}
