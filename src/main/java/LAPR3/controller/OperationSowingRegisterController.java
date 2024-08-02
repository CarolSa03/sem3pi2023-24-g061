package LAPR3.controller;

import BDDAD.dataAccess.repositories.OperationSowingRepository;
import BDDAD.dataAccess.repositories.Repositories;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

public class OperationSowingRegisterController {
    private OperationSowingRepository operationSowingRepository;

    public OperationSowingRegisterController() {
        operationSowingRepository = getOperationSowingRepository();
    }

    private OperationSowingRepository getOperationSowingRepository() {
        if (Objects.isNull(operationSowingRepository)) {
            Repositories repositories = Repositories.getInstance();
            operationSowingRepository = repositories.getOperationSowingRepository();
        }
        return operationSowingRepository;
    }

    public void operationSowingRegister(Integer operationId, LocalDate operationDate, Integer plotId, Float quantityValue, Float areaValue, Integer quantityUnitId, Integer areaUnitId, Integer cropId) throws SQLException {
        operationSowingRepository.operationSowingRegister(operationId, operationDate, plotId, quantityValue, areaValue, quantityUnitId, areaUnitId, cropId);
    }


}
