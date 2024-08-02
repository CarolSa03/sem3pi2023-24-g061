package BDDAD.controller;

import BDDAD.dataAccess.repositories.RecipeRepository;
import BDDAD.dataAccess.repositories.Repositories;
import dto.RecipeDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class RecipesListController {

    private RecipeRepository recipeRepository;

    public RecipesListController() {
        recipeRepository = getRecipeRepository();
    }

    private RecipeRepository getRecipeRepository() {
        if (Objects.isNull(recipeRepository)) {
            Repositories repositories = Repositories.getInstance();
            recipeRepository = repositories.getRecipeRepository();
        }
        return recipeRepository;
    }

    public List<RecipeDTO> getRecipes() throws SQLException {
        return recipeRepository.getRecipes();
    }

}
