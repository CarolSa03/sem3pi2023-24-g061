package BDDAD.dataAccess.repositories;

import BDDAD.dataAccess.DatabaseConnection;
import dto.ProductionFactorDTO;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductionFactorRepository {

    public ProductionFactorRepository() {
    }

    public List<ProductionFactorDTO> getProductionFactors() throws SQLException {

        CallableStatement callStmt = null;
        ResultSet resultSet = null;
        List<ProductionFactorDTO> productionFactors;

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ ? = call fncProductionFactors() }");

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.execute();
            resultSet = (ResultSet) callStmt.getObject(1);

            productionFactors = resultSetToList(resultSet);
        } finally {
            if(!Objects.isNull(callStmt)) {
                callStmt.close();
            }
            if(!Objects.isNull(resultSet)) {
                resultSet.close();
            }
        }

        return productionFactors;
    }

//    public void plotRegister(Integer plotId, String designation, float areaInHa) throws SQLException {
//
//        CallableStatement callStmt = null;
//        try {
//            Connection connection = DatabaseConnection.getInstance().getConnection();
//            callStmt = connection.prepareCall("{ call prcPlotRegister(?,?,?) }");
//
//            callStmt.setInt(1, plotId);
//            callStmt.setString(2, designation);
//            callStmt.setFloat(3, areaInHa);
//
//            callStmt.execute();
//            connection.commit();
//        } finally {
//            if(!Objects.isNull(callStmt)) {
//                callStmt.close();
//            }
//        }
//    }

//    public int plotDelete(Integer plotId) throws SQLException {
//
//        CallableStatement callStmt = null;
//        int deletedRows = 0;
//        try {
//            Connection connection = DatabaseConnection.getInstance().getConnection();
//            callStmt = connection.prepareCall("{ ? = call fncPlotDelete(?) }");
//
//            callStmt.registerOutParameter(1, OracleTypes.INTEGER);
//            callStmt.setInt(2, plotId);
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

    private List<ProductionFactorDTO> resultSetToList(ResultSet resultSet) throws SQLException {
        List<ProductionFactorDTO> productionFactors = new ArrayList<>();
        while (true) {
            if (!resultSet.next()) break;
            ProductionFactorDTO productionFactor = new ProductionFactorDTO(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            );
            productionFactors.add(productionFactor);
        }
        return productionFactors;
    }

}