package BDDAD.ui;

import BDDAD.controller.RecipesListController;
import dto.RecipeDTO;

import java.sql.SQLException;
import java.util.List;

public class RecipesListUI implements Runnable {
    private final RecipesListController controller;

    public RecipesListUI() {
        controller = new RecipesListController();
    }

    public void run() {
        try {
            List<RecipeDTO> recipes = controller.getRecipes();
            System.out.println("List of Recipes:");
            for (RecipeDTO recipe: recipes) {
                System.out.println(recipe);
            }
        } catch (SQLException e) {
            System.out.println("Database not return the recipes list!\n" + e.getMessage());
        }
    }

    public List<RecipeDTO> getRecipes() throws SQLException {
        return controller.getRecipes();
    }
}
