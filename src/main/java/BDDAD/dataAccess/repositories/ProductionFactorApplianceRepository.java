package BDDAD.dataAccess.repositories;

import BDDAD.dataAccess.DatabaseConnection;
import dto.ProductionFactorApplianceDTO;
import oracle.jdbc.OracleTypes;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductionFactorApplianceRepository {

    public ProductionFactorApplianceRepository() {
    }

    public List<ProductionFactorApplianceDTO> getProductionFactorAppliances(Integer pPlotId, LocalDate pInitialDate, LocalDate pFinalDate) throws SQLException {

        CallableStatement callStmt = null;
        ResultSet resultSet = null;
        List<ProductionFactorApplianceDTO> productionFactorAppliances;

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{? = call fncProductionFactorAppliances(?,?,?) }");

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            callStmt.setInt(2, pPlotId);
            callStmt.setDate(3, Date.valueOf(pInitialDate));
            callStmt.setDate(4, Date.valueOf(pFinalDate));

            callStmt.execute();
            resultSet = (ResultSet) callStmt.getObject(1);

            productionFactorAppliances = resultSetToList(resultSet);
        } finally {
            if(!Objects.isNull(callStmt)) {
                callStmt.close();
            }
            if(!Objects.isNull(resultSet)) {
                resultSet.close();
            }
        }

        return productionFactorAppliances;
    }

    public void productionFactorApplianceRegister(Integer operationId, Integer plotId, LocalDate operationDate, Float quantity, Float area, Integer quantityUnitId, Integer areaUnitId, Integer productionFactorId, Integer fertilizationModeId, Integer cropId) throws SQLException {

        CallableStatement callStmt = null;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ call prcProductionFactorApplianceRegister(?,?,?,?,?,?,?,?,?,?) }");

            callStmt.setInt(1, operationId);
            callStmt.setInt(2, plotId);
            callStmt.setDate(3, java.sql.Date.valueOf(operationDate));
            callStmt.setFloat(4, quantity);
            callStmt.setFloat(5, area);
            callStmt.setInt(6, quantityUnitId);
            callStmt.setInt(7, areaUnitId);
            callStmt.setInt(8, productionFactorId);
            callStmt.setInt(9, fertilizationModeId);
            callStmt.setInt(10, cropId);

            callStmt.execute();
            connection.commit();
        } finally {
            if(!Objects.isNull(callStmt)) {
                callStmt.close();
            }
        }

    }

    public int productionFactorApplianceDelete(Integer operationId) throws SQLException {

        CallableStatement callStmt = null;
        int deletedRows = 0;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ ? = call fncProductionFactorApplianceDelete(?) }");

            callStmt.registerOutParameter(1, OracleTypes.INTEGER);
            callStmt.setInt(2, operationId);

            callStmt.execute();
            deletedRows = callStmt.getInt(1);

            connection.commit();

        } finally {
            if(!Objects.isNull(callStmt)) {
                callStmt.close();
            }
        }
        return deletedRows;
    }

    private List<ProductionFactorApplianceDTO> resultSetToList(ResultSet resultSet) throws SQLException {
        List<ProductionFactorApplianceDTO> productionFactorAppliances = new ArrayList<>();
        while (true) {
            if (!resultSet.next()) break;
            ProductionFactorApplianceDTO productionFactorAppliance = new ProductionFactorApplianceDTO(
                    resultSet.getDate(1).toLocalDate(),
                    resultSet.getString(2),
                    resultSet.getString(3)
            );
            productionFactorAppliances.add(productionFactorAppliance);
        }
        return productionFactorAppliances;
    }

}
