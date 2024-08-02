package BDDAD.dataAccess.repositories;

import BDDAD.dataAccess.DatabaseConnection;
import dto.OperationWeedingDTO;
import oracle.jdbc.OracleTypes;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OperationWeedingRepository {
    public OperationWeedingRepository() {
    }

    public List<OperationWeedingDTO> getOperationsWeeding(LocalDate pInitialDate, LocalDate pFinalDate) throws SQLException {
        CallableStatement callStmt = null;
        ResultSet resultSet = null;
        List<OperationWeedingDTO> productionFactorAppliances = new ArrayList<>();

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ ? = call fncWeedingOperations(?,?) }");

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            callStmt.setDate(2, Date.valueOf(pInitialDate));
            callStmt.setDate(3, Date.valueOf(pFinalDate));

            callStmt.execute();
            resultSet = (ResultSet) callStmt.getObject(1);

            // Fill the list with results (or keep it empty if no results)
            productionFactorAppliances = resultSetToList(resultSet);
        } finally {
            if (!Objects.isNull(callStmt)) {
                callStmt.close();
            }
            if (!Objects.isNull(resultSet)) {
                resultSet.close();
            }
        }

        return productionFactorAppliances;
    }

    private List<OperationWeedingDTO> resultSetToList(ResultSet resultSet) throws SQLException {
        List<OperationWeedingDTO> list = new ArrayList<>();

        while (resultSet.next()) {
            OperationWeedingDTO operationWeedingDTO = new OperationWeedingDTO();
            operationWeedingDTO.setOperationDate(resultSet.getDate("operationDate").toLocalDate());
            operationWeedingDTO.setPlotId(resultSet.getInt("plotId"));
            operationWeedingDTO.setCropId(resultSet.getInt("cropId"));
            operationWeedingDTO.setValue(resultSet.getInt("value"));
            operationWeedingDTO.setUnitId(resultSet.getInt("unitId"));
            list.add(operationWeedingDTO);
        }

        return list;
    }

    public void operationWeedingRegister(int operationId, LocalDate operationDate, int plotId, int cropId, Double value, Integer unitId) throws SQLException {
        CallableStatement callStmt = null;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ call prcOperationWeedingRegister(?,?,?,?,?,?) }");

            callStmt.setInt(1, operationId);
            callStmt.setDate(2, java.sql.Date.valueOf(operationDate));
            callStmt.setInt(3, plotId);
            callStmt.setInt(4, cropId);
            callStmt.setDouble(5, value);
            callStmt.setInt(6, unitId);

            callStmt.execute();
            connection.commit();
        } finally {
            if (!Objects.isNull(callStmt)) {
                callStmt.close();
            }
        }
    }

    public int operationWeedingDelete(Integer operationId) throws SQLException {
        CallableStatement callStmt = null;
        int deletedRows = 0;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ ? = call fncOperationWeedingDelete(?) }");

            callStmt.registerOutParameter(1, Types.INTEGER);
            callStmt.setInt(2, operationId);

            callStmt.execute();
            deletedRows = callStmt.getUpdateCount();

            connection.commit();
        } finally {
            if (!Objects.isNull(callStmt)) {
                callStmt.close();
            }
        }
        return deletedRows;
    }
}
