package BDDAD.controller;

import BDDAD.dataAccess.repositories.OperationWeedingRepository;
import dto.OperationWeedingDTO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class OperationWeedingListController {
    private OperationWeedingRepository operationWeedingRepository;
    public OperationWeedingListController() {
        operationWeedingRepository = getOperationWeedingRepository();
    }

    private OperationWeedingRepository getOperationWeedingRepository() {
        if (operationWeedingRepository == null) {
            operationWeedingRepository = new OperationWeedingRepository();
        }
        return operationWeedingRepository;
    }

    public List<OperationWeedingDTO> getWeedingOperations(LocalDate initialDate, LocalDate finalDate) throws SQLException {
        return operationWeedingRepository.getOperationsWeeding(initialDate, finalDate);
    }
}
