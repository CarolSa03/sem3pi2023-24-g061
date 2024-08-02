package BDDAD.dataAccess.repositories;

import BDDAD.dataAccess.DatabaseConnection;
import dto.UnitDTO;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UnitRepository {
    public UnitRepository() {
    }

    public List<UnitDTO> getUnits() throws SQLException {
        CallableStatement callStmt = null;
        ResultSet resultSet = null;
        List<UnitDTO> units;

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ ? = call fncUnits() }");

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.execute();
            resultSet = (ResultSet) callStmt.getObject(1);

            units = resultSetToList(resultSet);
        } finally {
            if(!Objects.isNull(callStmt)) {
                callStmt.close();
            }
            if(!Objects.isNull(resultSet)) {
                resultSet.close();
            }
        }

        return units;
    }

    public List<UnitDTO> getQuantityUnits() throws SQLException {
        CallableStatement callStmt = null;
        ResultSet resultSet = null;
        List<UnitDTO> units;

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ ? = call fncQuantityUnits() }");

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.execute();
            resultSet = (ResultSet) callStmt.getObject(1);

            units = resultSetToList(resultSet);
        } finally {
            if(!Objects.isNull(callStmt)) {
                callStmt.close();
            }
            if(!Objects.isNull(resultSet)) {
                resultSet.close();
            }
        }

        return units;
    }

    public List<UnitDTO> getAreaUnits() throws SQLException {
        CallableStatement callStmt = null;
        ResultSet resultSet = null;
        List<UnitDTO> units;

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ ? = call fncAreaUnits() }");

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.execute();
            resultSet = (ResultSet) callStmt.getObject(1);

            units = resultSetToList(resultSet);
        } finally {
            if (!Objects.isNull(callStmt)) {
                callStmt.close();
            }
            if (!Objects.isNull(resultSet)) {
                resultSet.close();
            }
        }

        return units;
    }

    public List<UnitDTO> getTimeUnits() throws SQLException {
        CallableStatement callStmt = null;
        ResultSet resultSet = null;
        List<UnitDTO> units;

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ ? = call fncTimeUnits() }");

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.execute();
            resultSet = (ResultSet) callStmt.getObject(1);

            units = resultSetToList(resultSet);
        } finally {
            if (!Objects.isNull(callStmt)) {
                callStmt.close();
            }
            if (!Objects.isNull(resultSet)) {
                resultSet.close();
            }
        }

        return units;
    }

    public List<UnitDTO> getRatioUnits() throws SQLException {
        CallableStatement callStmt = null;
        ResultSet resultSet = null;
        List<UnitDTO> units;

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ ? = call fncRatioUnits() }");

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.execute();
            resultSet = (ResultSet) callStmt.getObject(1);

            units = resultSetToList(resultSet);
        } finally {
            if (!Objects.isNull(callStmt)) {
                callStmt.close();
            }
            if (!Objects.isNull(resultSet)) {
                resultSet.close();
            }
        }

        return units;
    }

    private List<UnitDTO> resultSetToList(ResultSet resultSet) throws SQLException {
        List<UnitDTO> units = new ArrayList<>();
        while (true) {
            if (!resultSet.next()) break;
            UnitDTO unit = new UnitDTO(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            );
            units.add(unit);
        }
        return units;
    }

}
