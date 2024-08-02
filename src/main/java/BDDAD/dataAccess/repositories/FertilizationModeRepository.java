package BDDAD.dataAccess.repositories;

import BDDAD.dataAccess.DatabaseConnection;
import dto.FertilizationModeDTO;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FertilizationModeRepository {

    public FertilizationModeRepository() {
    }

    public List<FertilizationModeDTO> getFertilizationModes() throws SQLException {

        CallableStatement callStmt = null;
        ResultSet resultSet = null;
        List<FertilizationModeDTO> fertilizationModes;

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ ? = call fncFertilizationModes() }");

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.execute();
            resultSet = (ResultSet) callStmt.getObject(1);

            fertilizationModes = resultSetToList(resultSet);
        } finally {
            if(!Objects.isNull(callStmt)) {
                callStmt.close();
            }
            if(!Objects.isNull(resultSet)) {
                resultSet.close();
            }
        }

        return fertilizationModes;
    }

//    public void fertilizationRegister(Integer operationId, Integer cropId, Integer operationTypeId, LocalDate operationDate, Double amount, String unit, Integer productionFactorId, Integer fertilizationModeId) throws SQLException {
//
//        CallableStatement callStmt = null;
//        try {
//            Connection connection = DatabaseConnection.getInstance().getConnection();
//            callStmt = connection.prepareCall("{ call prcFertilizationRegister(?,?,?,?,?,?,?,?) }");
//
//            callStmt.setInt(1, operationId);
//            callStmt.setInt(2, cropId);
//            callStmt.setInt(3, operationTypeId);
//            callStmt.setDate(4, java.sql.Date.valueOf(operationDate));
//            callStmt.setDouble(5, amount);
//            callStmt.setString(6, unit);
//            callStmt.setInt(7, productionFactorId);
//            callStmt.setInt(8, fertilizationModeId);
//
//            callStmt.execute();
//            connection.commit();
//        } finally {
//            if(!Objects.isNull(callStmt)) {
//                callStmt.close();
//            }
//        }
//
//    }
//
//    public int fertilizationDelete(Integer operationId) throws SQLException {
//
//        CallableStatement callStmt = null;
//        int deletedRows = 0;
//        try {
//            Connection connection = DatabaseConnection.getInstance().getConnection();
//            callStmt = connection.prepareCall("{ ? = call fncFertilizationDelete(?) }");
//
//            callStmt.registerOutParameter(1, OracleTypes.INTEGER);
//            callStmt.setInt(2, operationId);
//
//            callStmt.execute();
//            deletedRows = callStmt.getInt(1);
//
//            connection.commit();
//
//        } finally {
//            if(!Objects.isNull(callStmt)) {
//                callStmt.close();
//            }
//        }
//        return deletedRows;
//    }

    private List<FertilizationModeDTO> resultSetToList(ResultSet resultSet) throws SQLException {
        List<FertilizationModeDTO> fertilizationModes = new ArrayList<>();
        while (true) {
            if (!resultSet.next()) break;
            FertilizationModeDTO fertilizationMode = new FertilizationModeDTO(
                    resultSet.getInt(1),
                    resultSet.getString(2)
            );
            fertilizationModes.add(fertilizationMode);
        }
        return fertilizationModes;
    }

}
