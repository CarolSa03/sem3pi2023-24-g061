package BDDAD.dataAccess.repositories;

import BDDAD.dataAccess.DatabaseConnection;
import domain.Irrigation.Fertigation;
import domain.DataBase.Sector;
import domain.Irrigation.Irrigation;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IrrigationRepository {
    public IrrigationRepository() {
    }

    public List<Irrigation> getIrrigations() throws SQLException {

        CallableStatement callStmt = null;
        ResultSet resultSet = null;
        List<Irrigation> irrigations;

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ ? = call fncIrrigations() }");

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.execute();
            resultSet = (ResultSet) callStmt.getObject(1);

            irrigations = resultSetToList(resultSet);
        } finally {
            if(!Objects.isNull(callStmt)) {
                callStmt.close();
            }
            if(!Objects.isNull(resultSet)) {
                resultSet.close();
            }
        }

        return irrigations;
    }

    public List<Irrigation> getFieldbookIrrigations() throws SQLException {

        CallableStatement callStmt = null;
        ResultSet resultSet = null;
        List<Irrigation> irrigations;

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ ? = call fncIrrigationsInFieldbook() }");

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.execute();
            resultSet = (ResultSet) callStmt.getObject(1);

            irrigations = resultSetToList(resultSet);
        } finally {
            if(!Objects.isNull(callStmt)) {
                callStmt.close();
            }
            if(!Objects.isNull(resultSet)) {
                resultSet.close();
            }
        }

        return irrigations;
    }

    public Irrigation getLastIrrigationFromFieldbook() throws SQLException {

        CallableStatement callStmt = null;
        ResultSet resultSet = null;
        Irrigation irrigation = null;

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ ? = call fncLastIrrigationInFieldbook() }");

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.execute();
            resultSet = (ResultSet) callStmt.getObject(1);

            List<Irrigation> irrigations = resultSetToList(resultSet);
            if (!irrigations.isEmpty()) irrigation = irrigations.get(0);
        } finally {
            if(!Objects.isNull(callStmt)) {
                callStmt.close();
            }
            if(!Objects.isNull(resultSet)) {
                resultSet.close();
            }
        }

        return irrigation;
    }

    public void irrigationRegister(Integer operationId, Integer plotId, LocalDate operationDate, Integer unitId, Float durationInMinutes, Integer sectorId, LocalTime startTime, Integer fieldbookId, Integer recipeId, Float areaUsed, Integer areaUnitId) throws SQLException {

        if (operationId == null || plotId == null || operationDate == null || unitId == null || durationInMinutes == null || sectorId == null || startTime == null || fieldbookId == null || recipeId == null || areaUsed == null || areaUnitId == null) throw new IllegalArgumentException("All arguments are mandatory!");

        CallableStatement callStmt = null;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ call prcSectorIrrigationRegister(?,?,?,?,?,?,?,?,?,?,?) }");

            callStmt.setInt(1, operationId);
            callStmt.setInt(2, plotId);
            callStmt.setDate(3, java.sql.Date.valueOf(operationDate));
            callStmt.setInt(4, unitId);
            callStmt.setFloat(5, durationInMinutes);
            callStmt.setInt(6, sectorId);
            callStmt.setTimestamp(7, java.sql.Timestamp.valueOf(LocalDateTime.of(operationDate, startTime)));
            callStmt.setInt(8, fieldbookId);
            callStmt.setInt(9, recipeId);
            callStmt.setFloat(10, areaUsed);
            callStmt.setInt(11, areaUnitId);

            callStmt.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(!Objects.isNull(callStmt)) {
                callStmt.close();
            }
        }

    }

    private List<Irrigation> resultSetToList(ResultSet resultSet) throws SQLException {
        List<Irrigation> irrigations = new ArrayList<>();
        while (resultSet.next()) {
            Irrigation irrigation = new Irrigation();
            irrigation.setSector(new Sector(resultSet.getInt(1)));
            irrigation.setOperationDate(resultSet.getDate(2).toLocalDate());
            irrigation.setDurationInMinutes(resultSet.getInt(3));
            irrigation.setStartTime(LocalTime.of(resultSet.getTimestamp(4).toLocalDateTime().getHour(), resultSet.getTimestamp(4).toLocalDateTime().getMinute()));
            irrigation.setEndTime(LocalTime.of(resultSet.getTimestamp(4).toLocalDateTime().getHour(), resultSet.getTimestamp(4).toLocalDateTime().getMinute()).plusMinutes(resultSet.getInt(3)));
            if (resultSet.getInt(5) != 0) irrigation = new Fertigation(irrigation, resultSet.getInt(5));
            irrigations.add(irrigation);
        }
        return irrigations;
    }

}
