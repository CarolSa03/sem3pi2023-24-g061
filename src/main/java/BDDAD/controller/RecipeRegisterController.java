package BDDAD.controller;

import BDDAD.dataAccess.repositories.RecipeRepository;
import BDDAD.dataAccess.repositories.Repositories;
import utils.Utils;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class RecipeRegisterController {

    private RecipeRepository recipeRepository;

    public RecipeRegisterController() {
        recipeRepository = getRecipeRepository();
    }

    private RecipeRepository getRecipeRepository() {
        if (Objects.isNull(recipeRepository)) {
            Repositories repositories = Repositories.getInstance();
            recipeRepository = repositories.getRecipeRepository();
        }
        return recipeRepository;
    }

    public void recipeRegister(Integer recipeId, List<Integer> productionFactorIds, List<Integer> unitIds, List<Float> values) throws SQLException {
        recipeRepository.recipeRegister(recipeId, Utils.createStringFromListSeparatedBySemiColon(productionFactorIds), Utils.createStringFromListSeparatedBySemiColon(unitIds), Utils.createStringFromListSeparatedBySemiColon(values));
    }

    public int recipeDelete(Integer receipeId) throws SQLException {
        return recipeRepository.recipeDelete(receipeId);
    }
}
