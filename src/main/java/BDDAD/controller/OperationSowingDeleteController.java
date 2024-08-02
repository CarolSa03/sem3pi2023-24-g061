package BDDAD.controller;

import BDDAD.dataAccess.repositories.OperationSowingRepository;

import java.sql.SQLException;

public class OperationSowingDeleteController {
    private OperationSowingRepository operationSowingRepository;

    public OperationSowingDeleteController() {
        operationSowingRepository = getOperationSowingRepository();
    }

    private OperationSowingRepository getOperationSowingRepository() {
        if (operationSowingRepository == null) {
            operationSowingRepository = new OperationSowingRepository();
        }
        return operationSowingRepository;
    }

    public void operationSowingDelete(int operationId) throws SQLException {
        operationSowingRepository.operationSowingDelete(operationId);
    }
}
