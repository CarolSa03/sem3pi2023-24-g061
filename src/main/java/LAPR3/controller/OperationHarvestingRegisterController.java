package LAPR3.controller;

import BDDAD.dataAccess.repositories.OperationHarvestingRepository;
import BDDAD.dataAccess.repositories.Repositories;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

public class OperationHarvestingRegisterController {
    private OperationHarvestingRepository operationHarvestingRepository;

    public OperationHarvestingRegisterController() {
        operationHarvestingRepository = getOperationHarvestingRepository();
    }

    private OperationHarvestingRepository getOperationHarvestingRepository() {
        if (Objects.isNull(operationHarvestingRepository)) {
            Repositories repositories = Repositories.getInstance();
            operationHarvestingRepository = repositories.getOperationHarvestingRepository();
        }
        return operationHarvestingRepository;
    }

    public void operationHarvestingRegister(int operationId, LocalDate operationDate, int plotId, int productId, float quantity, int unitId, int cropId) throws SQLException {
        operationHarvestingRepository.operationHarvestingRegister(operationId, operationDate, plotId, productId, quantity, unitId, cropId);
    }

}
