package BDDAD.dataAccess.repositories;

import BDDAD.dataAccess.DatabaseConnection;
import dto.OperationSowingDTO;
import oracle.jdbc.OracleTypes;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OperationSowingRepository {
    public OperationSowingRepository() {
    }

    public List<OperationSowingDTO> getOperationsSowing(Integer pPlotId, LocalDate pInitialDate, LocalDate pFinalDate) throws SQLException {
        CallableStatement callStmt = null;
        ResultSet resultSet = null;
        List<OperationSowingDTO> operationsSowing;

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{? = call fncGetOperationsSowing(?,?,?) }");

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            callStmt.setInt(2, pPlotId);
            callStmt.setDate(3, Date.valueOf(pInitialDate));
            callStmt.setDate(4, Date.valueOf(pFinalDate));

            callStmt.execute();
            resultSet = (ResultSet) callStmt.getObject(1);

            operationsSowing = resultSetToList(resultSet);
        } finally {
            if (callStmt != null) {
                callStmt.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        }

        return operationsSowing;
    }

    public void operationSowingRegister(Integer operationId, LocalDate operationDate, Integer plotId, Float quantityValue, Float areaValue, Integer quantityUnitId, Integer areaUnitId, Integer cropId) throws SQLException {
        CallableStatement callStmt = null;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ call prcSowingRegister(?,?,?,?,?,?,?,?) }");

            callStmt.setInt(1, operationId);
            callStmt.setInt(2, plotId);
            callStmt.setDate(3, java.sql.Date.valueOf(operationDate));
            callStmt.setFloat(4, quantityValue);
            callStmt.setFloat(5, areaValue);
            callStmt.setInt(6, quantityUnitId);
            callStmt.setInt(7, areaUnitId);
            callStmt.setInt(8, cropId);

            callStmt.execute();
            connection.commit();
        } finally {
            if (callStmt != null) {
                callStmt.close();
            }
        }
    }


    public void operationSowingDelete(Integer operationId) throws SQLException {
        CallableStatement callStmt = null;
        int rowsAffected = 0;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ call prcSowingDelete(?) }");

            callStmt.setInt(1, operationId);
            callStmt.execute();
        } finally {
            if (callStmt != null) {
                callStmt.close();
            }
        }
    }

    private List<OperationSowingDTO> resultSetToList(ResultSet resultSet) throws SQLException {
        List<OperationSowingDTO> operationsSowing = new ArrayList<>();
        while (resultSet.next()) {
            OperationSowingDTO operationSowing = new OperationSowingDTO(
                    resultSet.getDate(1).toLocalDate(),
                    resultSet.getDouble(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
            operationsSowing.add(operationSowing);
        }
        return operationsSowing;
    }

}
