package BDDAD.dataAccess.repositories;

import BDDAD.dataAccess.DatabaseConnection;
import dto.ProductDTO;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductRepository {
    public ProductRepository() {
    }

    public List<ProductDTO> getProducts() throws SQLException {

        CallableStatement callStmt = null;
        ResultSet resultSet = null;
        List<ProductDTO> products;

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{? = call fncProducts() }");

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.execute();
            resultSet = (ResultSet) callStmt.getObject(1);

            products = resultSetToList(resultSet);
        } finally {
            if(!Objects.isNull(callStmt)) {
                callStmt.close();
            }
            if(!Objects.isNull(resultSet)) {
                resultSet.close();
            }
        }

        return products;
    }

    private List<ProductDTO> resultSetToList(ResultSet resultSet) throws SQLException {
        List<ProductDTO> products = new ArrayList<>();
        while (true) {
            if (!resultSet.next()) break;
            ProductDTO product = new ProductDTO(
                    resultSet.getInt(1),
                    resultSet.getString(2)
            );
            products.add(product);
        }
        return products;
    }

}