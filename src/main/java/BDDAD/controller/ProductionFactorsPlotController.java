package BDDAD.controller;

import BDDAD.dataAccess.repositories.ProductionFactorsPlotRepository;
import dto.ProductionFactorDTO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ProductionFactorsPlotController {
    private final ProductionFactorsPlotRepository repository = new ProductionFactorsPlotRepository();

    public List<ProductionFactorDTO> getProductionFactorsPlot(Integer plotId, LocalDate initialDate, LocalDate finalDate) throws SQLException {
        return repository.getProductionFactorsPlot(plotId, initialDate, finalDate);
    }
}
