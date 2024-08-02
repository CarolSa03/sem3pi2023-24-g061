package BDDAD.dataAccess.repositories;

import BDDAD.dataAccess.DatabaseConnection;
import dto.SectorDTO;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SectorRepository {

    public SectorRepository() {
    }

    public List<SectorDTO> getSectors() throws SQLException {

        CallableStatement callStmt = null;
        ResultSet resultSet = null;
        List<SectorDTO> sectors;

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ ? = call fncSectors() }");

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.execute();
            resultSet = (ResultSet) callStmt.getObject(1);

            sectors = resultSetToList(resultSet);
        } finally {
            if(!Objects.isNull(callStmt)) {
                callStmt.close();
            }
            if(!Objects.isNull(resultSet)) {
                resultSet.close();
            }
        }

        return sectors;
    }

    private List<SectorDTO> resultSetToList(ResultSet resultSet) throws SQLException {
        List<SectorDTO> sectors = new ArrayList<>();
        while (true) {
            if (!resultSet.next()) break;
            SectorDTO sector = new SectorDTO(
                    resultSet.getInt(1)
            );
            sectors.add(sector);
        }
        return sectors;
    }

}