package BDDAD.dataAccess.repositories;

import BDDAD.dataAccess.DatabaseConnection;
import dto.PlotDTO;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlotRepository {

    public PlotRepository() {
    }

    public List<PlotDTO> getPlots() throws SQLException {

        CallableStatement callStmt = null;
        ResultSet resultSet = null;
        List<PlotDTO> plots;

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ ? = call fncPlots() }");

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.execute();
            resultSet = (ResultSet) callStmt.getObject(1);

            plots = resultSetToList(resultSet);
        } finally {
            if(!Objects.isNull(callStmt)) {
                callStmt.close();
            }
            if(!Objects.isNull(resultSet)) {
                resultSet.close();
            }
        }

        return plots;
    }

    public List<PlotDTO> getPlotsOfSector(Integer sectorId) throws SQLException {
        CallableStatement callStmt = null;
        ResultSet resultSet = null;
        List<PlotDTO> plots;

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ ? = call fncPlotsInSector(?) }");

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            callStmt.setInt(2, sectorId);

            callStmt.execute();
            resultSet = (ResultSet) callStmt.getObject(1);

            plots = resultSetToList(resultSet);
        } finally {
            if(!Objects.isNull(callStmt)) {
                callStmt.close();
            }
            if(!Objects.isNull(resultSet)) {
                resultSet.close();
            }
        }

        return plots;
    }

    public void plotRegister(Integer plotId, String designation, float areaInHa) throws SQLException {

        CallableStatement callStmt = null;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ call prcPlotRegister(?,?,?) }");

            callStmt.setInt(1, plotId);
            callStmt.setString(2, designation);
            callStmt.setFloat(3, areaInHa);

            callStmt.execute();
            connection.commit();
        } finally {
            if(!Objects.isNull(callStmt)) {
                callStmt.close();
            }
        }
    }

    public int plotDelete(Integer plotId) throws SQLException {

        CallableStatement callStmt = null;
        int deletedRows = 0;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ ? = call fncPlotDelete(?) }");

            callStmt.registerOutParameter(1, OracleTypes.INTEGER);
            callStmt.setInt(2, plotId);

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

    private List<PlotDTO> resultSetToList(ResultSet resultSet) throws SQLException {
        List<PlotDTO> plots = new ArrayList<>();
        while (true) {
            if (!resultSet.next()) break;
            PlotDTO plot = new PlotDTO(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getFloat(3)
            );
            plots.add(plot);
        }
        return plots;
    }

}