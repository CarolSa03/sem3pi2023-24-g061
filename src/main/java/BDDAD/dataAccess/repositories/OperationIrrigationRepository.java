package BDDAD.dataAccess.repositories;

import BDDAD.dataAccess.DatabaseConnection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class OperationIrrigationRepository {
    public void operationIrrigationRegister(int operationId, int plotId, LocalDate operationDate, int timeUnitId,
                                            float durationInMinutes, int sectorId, LocalTime initialTimestamp,
                                            int fieldbookId, int recipeId, float areaUsed, int areaUnitId)  throws SQLException {
        CallableStatement callStmt = null;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ call prcSectorIrrigationRegister(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");

            callStmt.setInt(1, operationId);
            callStmt.setInt(2, plotId);
            callStmt.setDate(3, java.sql.Date.valueOf(operationDate));
            callStmt.setInt(4, timeUnitId);
            callStmt.setFloat(5, durationInMinutes);
            callStmt.setInt(6, sectorId);
            callStmt.setTime(7, java.sql.Time.valueOf(initialTimestamp));
            callStmt.setInt(8, fieldbookId);
            callStmt.setInt(9, recipeId);
            callStmt.setFloat(10, areaUsed);
            callStmt.setInt(11, areaUnitId);

            callStmt.execute();
        } finally {
            if (callStmt != null) {
                callStmt.close();
            }
        }
    }
}
