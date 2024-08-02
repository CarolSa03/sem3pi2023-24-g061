package LAPR3.controller;

import BDDAD.dataAccess.repositories.OperationWeedingRepository;
import BDDAD.dataAccess.repositories.Repositories;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

public class OperationWeedingRegisterController {
    private OperationWeedingRepository operationWeedingRepository;

    public OperationWeedingRegisterController() {
        operationWeedingRepository = getOperationWeedingRepository();
    }

    private OperationWeedingRepository getOperationWeedingRepository() {
        if (Objects.isNull(operationWeedingRepository)) {
            Repositories repositories = Repositories.getInstance();
            operationWeedingRepository = repositories.getOperationWeedingRepository();
        }
        return operationWeedingRepository;
    }

    public void operationWeedingRegister(int operationId, LocalDate operationDate, int plotId, int cropId, Double value, Integer unitId) throws SQLException {
        operationWeedingRepository.operationWeedingRegister(operationId, operationDate, plotId, cropId, value, unitId);
    }
}
