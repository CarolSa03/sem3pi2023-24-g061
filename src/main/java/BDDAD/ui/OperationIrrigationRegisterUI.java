package BDDAD.ui;

import BDDAD.controller.OperationIrrigationRegisterController;
import BDDAD.controller.RecipeRegisterController;
import utils.Utils;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class OperationIrrigationRegisterUI implements Runnable {
//    create or replace NONEDITIONABLE procedure prcSectorIrrigationRegister(
//            pOperationId            Operation.operationId%type,
//            pPlotId                 Operation.plotId%type,
//            pOperationDate          Operation.operationDate%type,
//            pTimeUnitId             OperationAmount.unitId%type,
//            pDurationInMinutes      OperationAmount.value%type,
//            pSectorId               SectorIrrigation.sectorId%type,
//            pInitialTimestamp       SectorIrrigation.initialTimestamp%type,
//            pFieldbookId            SectorIrrigationInFieldbook.fieldbookId%type,
//            pRecipeId               ProductionFactorInRecipe.recipeId%type,
//            pAreaUsed               OperationAmount.value%type,
//            pAreaUnitId             OperationAmount.unitId%type
//    )

    private final PlotsListUI plotListUI;
    private final RecipesListUI recipesListUI;
    private final SectorsListUI sectorsListUI;
    private final OperationIrrigationRegisterController controller;

    public OperationIrrigationRegisterUI() {
        plotListUI = new PlotsListUI();
        recipesListUI = new RecipesListUI();
        sectorsListUI = new SectorsListUI();
        controller = new OperationIrrigationRegisterController();
    }

    public void run() {
        try {
            System.out.println("###OPERATION IRRIGATION REGISTER###\n");

            int operationId = Utils.getNextOperationId();
            System.out.println("Operation ID: " + operationId);

            LocalDate operationDate = Utils.readDateFromConsole("Operation Date: ");
            float durationInMinutes = Utils.readFloatFromConsole("Duration in Minutes: ");
            plotListUI.run();
            int plotId = plotListUI.getPlots().getFirst().getId();
            sectorsListUI.run();
            int sectorId = Utils.readIdFromConsole("Sector ID: ", Utils.getIds(sectorsListUI.getSectors()));
            LocalTime initialTimestamp = Utils.readTimeFromConsole("Initial Timestamp: ");

            float areaUsed = Utils.readFloatFromConsole("Area [máx. " + plotListUI.getPlots().getFirst().getArea() + "]:");
            while (areaUsed > plotListUI.getPlots().getFirst().getArea()) {
                System.out.println("Area used is bigger than the plot area! [Area: " + plotListUI.getPlots().getFirst().getArea() + "]\n]");
                areaUsed = Utils.readFloatFromConsole("Area: ");
            }

            int recipeId = 0;
            int fieldbookId = 0;
            int timeUnitId = 6;
            int areaUnitId = 1;

            boolean useFertilizers = Utils.confirm("This irrigation operation will use fertilizers? [YES/NO]");

            if (useFertilizers) recipeId = Utils.readIdFromConsole("Recipe ID: ", Utils.getIds(recipesListUI.getRecipes()));

            controller.operationIrrigationRegister(operationId, plotId, operationDate, timeUnitId, durationInMinutes, sectorId, initialTimestamp, fieldbookId, recipeId, areaUsed, areaUnitId);
            System.out.println("Irrigation Operation registered successfully!\n");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
