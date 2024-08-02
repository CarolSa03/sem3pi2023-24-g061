package BDDAD.ui;

import BDDAD.controller.RecipeRegisterController;
import utils.Utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipeRegisterUI implements Runnable {

    private final RecipesListUI recipesListUI;
    private final ProductionFactorsListUI productionFactorsListUI;
    private final UnitsListUI unitsListUI;
    private final RecipeRegisterController controller;

    public RecipeRegisterUI() {
        recipesListUI = new RecipesListUI();
        productionFactorsListUI = new ProductionFactorsListUI();
        unitsListUI = new UnitsListUI();
        controller = new RecipeRegisterController();
    }

    @Override
    public void run() {
        try {
            System.out.println("###RECIPE REGISTER###\n");

            recipesListUI.run();
            Integer receipeId = Utils.readDifferentIdFromConsole("Recipe ID (different from the already existing ones): ", Utils.getIds(recipesListUI.getRecipes()));

            List<Integer> productionFactorIds = new ArrayList<>();
            List<Integer> unitIds = new ArrayList<>();
            List<Float> values = new ArrayList<>();

            boolean end = false;
            while (!end) {
                productionFactorsListUI.run();
                Integer productionFactorId = Utils.readIdFromConsole("Production factor ID: ", Utils.getIds(productionFactorsListUI.getProductionFactors()));
                while (productionFactorIds.contains(productionFactorId)) {
                    System.out.println("Production factor already added!");
                    productionFactorId = Utils.readIdFromConsole("Production factor ID: ", Utils.getIds(productionFactorsListUI.getProductionFactors()));
                }
                productionFactorIds.add(productionFactorId);
                unitsListUI.runRatioUnits();
                unitIds.add(Utils.readIdFromConsole("Unit ID: ", Utils.getIds(unitsListUI.getRatioUnits())));
                values.add(Utils.readFloatFromConsole("Amount: "));
                if (!Utils.confirm("Do you want to add another production factor to this recipe? (Yes/No)")) {
                    end = true;
                }
            }

            controller.recipeRegister(receipeId, productionFactorIds, unitIds, values);
            System.out.println("Recipe registered successfully!\n");

            if (Utils.confirm("Do you want to delete it? (Yes/No)")) {
                if (controller.recipeDelete(receipeId) > 0) System.out.println("Recipe deleted successfully!\n");
                else System.out.println("Recipe not deleted!\n");
            }
        } catch (SQLException e) {
            System.out.println("Recipe not registered!\n " + e.getMessage());
        }
    }
}