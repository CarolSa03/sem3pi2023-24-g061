package BDDAD.dataAccess.repositories;

import BDDAD.dataAccess.DatabaseConnection;
import dto.CropDTO;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CropRepository {

    public CropRepository() {
    }

    public List<CropDTO> getCropsInPlot(Integer pPlotId) throws SQLException {

        CallableStatement callStmt = null;
        ResultSet resultSet = null;
        List<CropDTO> crops;

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ ? = call fncCropsInPlot(?) }");

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            callStmt.setInt(2, pPlotId);

            callStmt.execute();
            resultSet = (ResultSet) callStmt.getObject(1);

            crops = resultSetToList(resultSet);
        } finally {
            if(!Objects.isNull(callStmt)) {
                callStmt.close();
            }
            if(!Objects.isNull(resultSet)) {
                resultSet.close();
            }
        }

        return crops;
    }

    public List<CropDTO> getCropsOfProduct(Integer pPlotId, Integer pProductId) throws SQLException {

        CallableStatement callStmt = null;
        ResultSet resultSet = null;
        List<CropDTO> crops;

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ ? = call fncCropOfProduct(?,?) }");

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            callStmt.setInt(2, pPlotId);
            callStmt.setInt(3, pProductId);

            callStmt.execute();
            resultSet = (ResultSet) callStmt.getObject(1);

            crops = resultSetToList(resultSet);
        } finally {
            if(!Objects.isNull(callStmt)) {
                callStmt.close();
            }
            if(!Objects.isNull(resultSet)) {
                resultSet.close();
            }
        }

        return crops;
    }

    private List<CropDTO> resultSetToList(ResultSet resultSet) throws SQLException {
        List<CropDTO> crops = new ArrayList<>();
        while (true) {
            if (!resultSet.next()) break;
            CropDTO crop = new CropDTO(
                    resultSet.getInt(1),
                    resultSet.getString(2)
            );
            crops.add(crop);
        }
        return crops;
    }

}
