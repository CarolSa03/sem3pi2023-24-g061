package BDDAD.controller;

import BDDAD.dataAccess.repositories.OperationWeedingRepository;

import java.sql.SQLException;

public class OperationWeedingDeleteController {

    private OperationWeedingRepository operationWeedingRepository;

    public OperationWeedingDeleteController() {
        operationWeedingRepository = getOperationWeedingRepository();
    }

    private OperationWeedingRepository getOperationWeedingRepository() {
        if (operationWeedingRepository == null) {
            operationWeedingRepository = new OperationWeedingRepository();
        }
        return operationWeedingRepository;
    }

    public void operationWeedingDelete(int operationId) throws SQLException {
        operationWeedingRepository.operationWeedingDelete(operationId);
    }
}
