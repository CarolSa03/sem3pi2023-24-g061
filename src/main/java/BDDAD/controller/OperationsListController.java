package BDDAD.controller;

import BDDAD.dataAccess.repositories.OperationRepository;
import BDDAD.dataAccess.repositories.Repositories;
import dto.OperationDTO;
import dto.mapper.OperationMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class OperationsListController {
    private OperationRepository operationRepository;

    public OperationsListController() {
        operationRepository = getOperationRepository();
    }

    private OperationRepository getOperationRepository() {
        if (Objects.isNull(operationRepository)) {
            Repositories repositories = Repositories.getInstance();
            operationRepository = repositories.getOperationRepository();
        }
        return operationRepository;
    }

    public List<OperationDTO> getOperations() throws SQLException {
        return OperationMapper.toDTOList(operationRepository.getOperations());
    }
}
