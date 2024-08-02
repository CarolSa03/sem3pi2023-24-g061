package BDDAD.controller;

import BDDAD.dataAccess.repositories.OperationRepository;
import BDDAD.dataAccess.repositories.Repositories;

import java.sql.SQLException;
import java.util.Objects;

public class OperationCancelController {
    private OperationRepository operationRepository;

    public OperationCancelController() {
        operationRepository = getOperationRepository();
    }

    private OperationRepository getOperationRepository() {
        if (Objects.isNull(operationRepository)) {
            Repositories repositories = Repositories.getInstance();
            operationRepository = repositories.getOperationRepository();
        }
        return operationRepository;
    }

    public void operationCancel(Integer operationId) throws SQLException {
        operationRepository.operationCancel(operationId);
    }
}
