package BDDAD.dataAccess.repositories;

import BDDAD.dataAccess.DatabaseConnection;
import domain.DataBase.Operation;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OperationRepository {
    public OperationRepository() {
    }

    public List<Operation> getOperations() throws SQLException {

        CallableStatement callStmt = null;
        ResultSet resultSet = null;
        List<Operation> operations;

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ ? = call fncOperations() }");

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.execute();
            resultSet = (ResultSet) callStmt.getObject(1);

            operations = resultSetToList(resultSet);
        } finally {
            if(!Objects.isNull(callStmt)) {
                callStmt.close();
            }
            if(!Objects.isNull(resultSet)) {
                resultSet.close();
            }
        }

        return operations;
    }

    public int getNextOperationId() throws SQLException {
        CallableStatement callStmt = null;
        int operationId;

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ ? = call fncGenerateOperationId() }");

            callStmt.registerOutParameter(1, OracleTypes.INTEGER);

            callStmt.execute();

            operationId = callStmt.getInt(1);
        } finally {
            if(!Objects.isNull(callStmt)) {
                callStmt.close();
            }
        }

        return operationId;
    }

    private List<Operation> resultSetToList(ResultSet resultSet) throws SQLException {
        List<Operation> operations = new ArrayList<>();
        while (true) {
            if (!resultSet.next()) break;
            Operation operation = new Operation(
                    resultSet.getInt(1),
                    resultSet.getInt(2),
                    resultSet.getDate(3).toLocalDate()
            );
            operations.add(operation);
        }
        return operations;
    }

    public void operationCancel(Integer operationId) throws SQLException {
        CallableStatement callStmt = null;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ call prcCancelOperation(?) }");

            callStmt.setInt(1, operationId);
            callStmt.execute();
        } finally {
            if (callStmt != null) {
                callStmt.close();
            }
        }
    }

    public Operation getOperation(int operationId) throws SQLException {

        CallableStatement callStmt = null;
        ResultSet resultSet = null;
        Operation operation = null;

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();

            callStmt = connection.prepareCall("{ ? = call fncGetTheOperation(?) }");
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            callStmt.setInt(2, operationId);

            callStmt.execute();
            resultSet = (ResultSet) callStmt.getObject(1);

            if (resultSet.next()) {
                operation = resultSetToList(resultSet).getFirst();
            }
        } finally {
            if (callStmt != null) {
                callStmt.close();
            }
        }

        return operation;
    }

}
