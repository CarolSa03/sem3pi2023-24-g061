package BDDAD.controller;

import BDDAD.dataAccess.repositories.OperationIrrigationRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class OperationIrrigationRegisterController {
    private final OperationIrrigationRepository repository = new OperationIrrigationRepository();
    public void operationIrrigationRegister(int operationId, int plotId, LocalDate operationDate, int timeUnitId,
                                            float durationInMinutes, int sectorId, LocalTime initialTimestamp,
                                            int fieldbookId, int recipeId, float areaUsed, int areaUnitId)  throws SQLException {

        repository.operationIrrigationRegister(operationId, plotId, operationDate, timeUnitId, durationInMinutes,
                                                sectorId, initialTimestamp, fieldbookId, recipeId, areaUsed, areaUnitId);
    }
}
