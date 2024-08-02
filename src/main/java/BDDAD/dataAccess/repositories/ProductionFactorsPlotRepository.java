package BDDAD.dataAccess.repositories;

import BDDAD.dataAccess.DatabaseConnection;
import dto.ProductionFactorDTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductionFactorsPlotRepository {
    public List<ProductionFactorDTO> getProductionFactorsPlot(Integer plotId, LocalDate initialDate, LocalDate finalDate) throws SQLException {
        CallableStatement callStmt = null;
        ResultSet resultSet = null;
        List<ProductionFactorDTO> productionFactorsList = new ArrayList<>();

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ ? = call fncProductionFactorInPlotList(?, ?, ?) }");

            callStmt.registerOutParameter(1, java.sql.Types.REF_CURSOR);
            callStmt.setInt(2, plotId);
            callStmt.setDate(3, Date.valueOf(initialDate));
            callStmt.setDate(4, Date.valueOf(finalDate));


            callStmt.execute();
            resultSet = (ResultSet) callStmt.getObject(1);

            productionFactorsList = resultSetToList(resultSet);
        } finally {
            if (!Objects.isNull(callStmt)) {
                callStmt.close();
            }
            if (!Objects.isNull(resultSet)) {
                resultSet.close();
            }
        }

        return productionFactorsList;
    }

    private List<ProductionFactorDTO> resultSetToList(ResultSet resultSet) throws SQLException {
        List<ProductionFactorDTO> productionFactors = new ArrayList<>();
        while (true) {
            if (!resultSet.next()) break;
            ProductionFactorDTO productionFactor = new ProductionFactorDTO(
                    resultSet.getDate(1).toLocalDate(),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getFloat(4)
            );
            productionFactors.add(productionFactor);
        }
        return productionFactors;
    }
}
