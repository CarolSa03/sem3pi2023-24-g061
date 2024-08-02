package BDDAD.dataAccess.repositories;

import BDDAD.dataAccess.DatabaseConnection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class OperationHarvestingRepository {

    public void operationHarvestingRegister(int operationId, LocalDate operationDate, int plotId, int productId, float quantityValue, int quantityUnitId, int cropId) throws SQLException {
        CallableStatement callStmt = null;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ call prcHarvestRegister(?,?,?,?,?,?,?) }");

            callStmt.setInt(1, operationId);
            callStmt.setDate(2, java.sql.Date.valueOf(operationDate));
            callStmt.setInt(3, plotId);
            callStmt.setInt(4, productId);
            callStmt.setFloat(5, quantityValue);
            callStmt.setInt(6, quantityUnitId);
            callStmt.setInt(7, cropId);

            callStmt.execute();
        } finally {
            if (callStmt != null) {
                callStmt.close();
            }
        }
    }

}
