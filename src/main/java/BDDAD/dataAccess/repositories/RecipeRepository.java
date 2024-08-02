package BDDAD.dataAccess.repositories;

import BDDAD.dataAccess.DatabaseConnection;
import dto.ProductionFactorDTO;
import dto.RecipeDTO;
import dto.UnitDTO;
import oracle.jdbc.OracleTypes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecipeRepository {

    public RecipeRepository() {
    }

    public List<RecipeDTO> getRecipes() throws SQLException {

        CallableStatement callStmt = null;
        ResultSet resultSet = null;
        List<RecipeDTO> recipeDTOS;

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ ? = call fncRecipes() }");

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.execute();
            resultSet = (ResultSet) callStmt.getObject(1);

            recipeDTOS = resultSetToList(resultSet);
        } finally {
            if(!Objects.isNull(callStmt)) {
                callStmt.close();
            }
            if(!Objects.isNull(resultSet)) {
                resultSet.close();
            }
        }

        return recipeDTOS;
    }

    public void recipeRegister(Integer recipeId, String productionFactorIds, String unitIds, String values) throws SQLException {

        CallableStatement callStmt = null;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ call prcRecipeRegister(?,?,?,?) }");

            callStmt.setInt(1, recipeId);
            callStmt.setString(2, productionFactorIds);
            callStmt.setString(3, unitIds);
            callStmt.setString(4, values);

            callStmt.execute();
            connection.commit();
        } finally {
            if(!Objects.isNull(callStmt)) {
                callStmt.close();
            }
        }
    }

    public int recipeDelete(Integer recipeId) throws SQLException {

        CallableStatement callStmt = null;
        int deletedRows = 0;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            callStmt = connection.prepareCall("{ ? = call fncRecipeDelete(?) }");

            callStmt.registerOutParameter(1, OracleTypes.INTEGER);
            callStmt.setInt(2, recipeId);

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

    private List<RecipeDTO> resultSetToList(ResultSet resultSet) throws SQLException {
        List<RecipeDTO> recipes = new ArrayList<>();
        int recipeId = 0;
        String pfDesignation;
        String unit;
        Float value;

        List<ProductionFactorDTO> productionFactorDTOS = new ArrayList<>();
        List<UnitDTO> units = new ArrayList<>();
        List<Float> values = new ArrayList<>();

        RecipeDTO recipeDTO = new RecipeDTO();
        if (resultSet.next()) {
            recipeId = resultSet.getInt(1);
            pfDesignation = resultSet.getString(2);
            unit = resultSet.getString(3);
            value = resultSet.getFloat(4);
            productionFactorDTOS.add(new ProductionFactorDTO(pfDesignation));
            units.add(new UnitDTO(unit));
            values.add(value);
        }

        int copyRecipeId = recipeId;

        while (true) {
            if (!resultSet.next()) {
                recipeDTO.setId(copyRecipeId);
                recipeDTO.setProductionFactors(productionFactorDTOS);
                recipeDTO.setUnits(units);
                recipeDTO.setValues(values);
                recipes.add(recipeDTO);
                break;
            }
            recipeId = resultSet.getInt(1);
            if (recipeId != copyRecipeId) {
                recipeDTO.setId(copyRecipeId);
                recipeDTO.setProductionFactors(productionFactorDTOS);
                recipeDTO.setUnits(units);
                recipeDTO.setValues(values);
                recipes.add(recipeDTO);
                recipeDTO = new RecipeDTO();
                productionFactorDTOS = new ArrayList<>();
                units = new ArrayList<>();
                values = new ArrayList<>();
                copyRecipeId = recipeId;
            }
            pfDesignation = resultSet.getString(2);
            unit = resultSet.getString(3);
            value = resultSet.getFloat(4);
            productionFactorDTOS.add(new ProductionFactorDTO(pfDesignation));
            units.add(new UnitDTO(unit));
            values.add(value);
        }
        return recipes;
    }

}
