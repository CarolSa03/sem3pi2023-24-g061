package BDDAD.controller;

import BDDAD.dataAccess.repositories.OperationSowingRepository;
import BDDAD.dataAccess.repositories.Repositories;
import dto.OperationSowingDTO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class OperationSowingListController {
    private OperationSowingRepository operationSowingRepository;

    public OperationSowingListController() {
        operationSowingRepository = getOperationSowingRepository();
    }

    private OperationSowingRepository getOperationSowingRepository() {
        if (Objects.isNull(operationSowingRepository)) {
            Repositories repositories = Repositories.getInstance();
            operationSowingRepository = repositories.getOperationSowingRepository();
        }
        return operationSowingRepository;
    }

    public List<OperationSowingDTO> getSowingOperations(Integer pPlotId, LocalDate pInitialDate, LocalDate pFinalDate) throws SQLException {
        return operationSowingRepository.getOperationsSowing(pPlotId, pInitialDate, pFinalDate);
    }

}
