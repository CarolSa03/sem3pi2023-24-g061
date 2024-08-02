SET SERVEROUTPUT ON;

/
DECLARE
    vRecipeId            Recipe.recipeId%TYPE;
    vProductionFactors   VARCHAR2(255);
    vUnits               VARCHAR2(255);
    vAmounts             VARCHAR2(255);

BEGIN
    vRecipeId := 1;
    vProductionFactors := '12;1;3';
    vUnits := '7;7;8';
    vAmounts := '2;5,5;3';

    prcRecipeRegister(vRecipeId, vProductionFactors, vUnits, vAmounts);

    DBMS_OUTPUT.PUT_LINE('Inserted.');

    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('Removed.');

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error occurred: ' || SQLERRM);
END;
/

/
DECLARE
    vResultSet SYS_REFCURSOR;
    vOperationDate operation.operationDate%TYPE;
    vProductionFactorDesignation productionFactor.designation%TYPE;
    vPlantDesignation plant.designation%TYPE;
BEGIN
    vResultSet := fncProductionFactorAppliances(105, TO_DATE('01/01/2019','DD/MM/YYYY'), TO_DATE('06/07/2023', 'DD/MM/YYYY'));
    
    LOOP
        FETCH vResultSet INTO vOperationDate, vProductionFactorDesignation, vPlantDesignation;
        EXIT WHEN vResultSet%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE(vOperationDate || ', ' || vProductionFactorDesignation || ', ' || vPlantDesignation);
    END LOOP;

END;
/

/
DECLARE
    vOperationId            Operation.operationId%TYPE := fncGenerateOperationId;
    vPlotId                 Operation.plotId%TYPE := 105;
    vOperationDate          Operation.operationDate%TYPE := SYSDATE;
    vQuantityValue          OperationAmount.value%TYPE := 0;
    vAreaValue              OperationAmount.value%TYPE := 1.1;
    vQuantityUnitId         OperationAmount.value%TYPE := 0;
    vAreaUnitId             OperationAmount.value%TYPE := 1;
    vProductionFactorId     ProductionFactorAppliance.productionFactorId%TYPE := 12;
    vFertilizationModeId    Fertilization.fertilizationModeId%TYPE := 1;
    vCropId                 CropFertilization.cropid%TYPE := 0;
BEGIN
    prcProductionFactorApplianceRegister(
        pOperationId => vOperationId,
        pPlotId => vPlotId,
        pOperationDate => vOperationDate,
        pQuantityValue => vQuantityValue,
        pAreaValue => vAreaValue,
        pQuantityUnitId => vQuantityUnitId,
        pAreaUnitId => vAreaUnitId,
        pProductionFactorId => vProductionFactorId,
        pFertilizationModeId => vFertilizationModeId,
        pCropId => vCropId
    );
    
    DBMS_OUTPUT.PUT_LINE('Inserted.');

    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('Removed.');

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
/

/
DECLARE
    vOperationId            Operation.operationId%TYPE := fncGenerateOperationId;
    vSectorId               SectorIrrigation.sectorId%TYPE := 10;
    vOperationDate          Operation.operationDate%TYPE := TO_DATE('30/12/2023','DD/MM/YYYY');
    vTimeUnitId             OperationAmount.unitId%TYPE := 6;
    vDurationInMinutes      OperationAmount.value%TYPE := 14;
    vInitialTimestamp       SectorIrrigation.initialTimestamp%TYPE := TO_TIMESTAMP('08:30','HH24:MI');
    vFieldbookId            SectorIrrigationInFieldbook.fieldbookId%TYPE := 1;
    vRecipeId               ProductionFactorInRecipe.recipeId%TYPE := 10;
    vAreaUsed               OperationAmount.value%TYPE := 0.0;
    vAreaUnitId             OperationAmount.unitId%TYPE := 9;
    vPlotId                 Plot.plotId%TYPE := 0;
    vPlotDesignation        Plot.designation%TYPE;
    vPlotAreaInHa           Plot.areaInHa%TYPE;
    vPlotsCursor            SYS_REFCURSOR;
BEGIN

    vPlotsCursor := fncPlotsInSector(vSectorId);
    
    LOOP
        FETCH vPlotsCursor into vPlotId, vPlotDesignation, vPlotAreaInHa;
        EXIT WHEN vPlotId != 0;
    END LOOP;
    
    prcSectorIrrigationRegister(
        pOperationId => vOperationId,
        pPlotId => vPlotId,
        pOperationDate => vOperationDate,
        pTimeUnitId => vTimeUnitId,
        pDurationInMinutes => vDurationInMinutes,
        pSectorId => vSectorId,
        pInitialTimestamp => vInitialTimestamp,
        pFieldbookId => vFieldbookId,
        pRecipeId => vRecipeId,
        pAreaUsed => vAreaUsed,
        pAreaUnitId => vAreaUnitId
    );
    
    DBMS_OUTPUT.PUT_LINE('Inserted.');

    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('Removed.');

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
/

/
DECLARE
  l_refcur SYS_REFCURSOR;
  l_CropIrrigationOperationId NUMBER;
  l_CropFertilizationOperationId NUMBER;
  l_designation VARCHAR2(255);
  l_recipeId NUMBER;
  l_DurationInMinutes VARCHAR2(255);
  l_PlotAreaInHaFertigated VARCHAR2(255);
  l_ProductionFactorDesignation VARCHAR2(255);
  l_amountInLiters NUMBER;
BEGIN
  l_refcur := fncFertigations;

  LOOP
    FETCH l_refcur INTO l_CropIrrigationOperationId, l_CropFertilizationOperationId, l_designation, l_recipeId, l_DurationInMinutes, l_PlotAreaInHaFertigated, l_ProductionFactorDesignation, l_amountInLiters;
    EXIT WHEN l_refcur%NOTFOUND;

    DBMS_OUTPUT.PUT_LINE('CropIrrigationOperationId: ' || l_CropIrrigationOperationId);
    DBMS_OUTPUT.PUT_LINE('CropFertilizationOperationId: ' || l_CropFertilizationOperationId);
    DBMS_OUTPUT.PUT_LINE('Plant Designation: ' || l_designation);
    DBMS_OUTPUT.PUT_LINE('RecipeId: ' || l_recipeId);
    DBMS_OUTPUT.PUT_LINE('DurationInMinutes: ' || l_DurationInMinutes);
    DBMS_OUTPUT.PUT_LINE('PlotAreaInHaFertigated: ' || l_PlotAreaInHaFertigated);
    DBMS_OUTPUT.PUT_LINE('ProductionFactor Designation: ' || l_ProductionFactorDesignation);
    DBMS_OUTPUT.PUT_LINE('AmountInLiters: ' || l_amountInLiters);
    DBMS_OUTPUT.PUT_LINE('-----------------------------------');
  END LOOP;

  CLOSE l_refcur;
END;
/

/
DECLARE
  l_cursor SYS_REFCURSOR;
  l_designation VARCHAR2(255);
BEGIN
  l_cursor := fncCropsWithHigherWateringInYear(2023);
  
  LOOP
    FETCH l_cursor INTO l_designation;
    EXIT WHEN l_cursor%NOTFOUND;
    DBMS_OUTPUT.PUT_LINE('Designation: ' || l_designation);
  END LOOP;

  CLOSE l_cursor;
END;
/

/
DECLARE
    test_operationId Operation.operationId%TYPE;
BEGIN
    test_operationId := fncGenerateOperationId;
    INSERT INTO OPERATION(operationId, plotId, operationDate)
        VALUES (test_operationId, 105, SYSDATE);
    PRCCANCELOPERATION(test_operationId);
    ROLLBACK;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE(SQLERRM);
END;
/

/
DECLARE
    test_operationId Operation.operationId%TYPE;
BEGIN
PRCCANCELOPERATION(1);
ROLLBACK;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE(SQLERRM);
END;
/

/
create an anonimous block  for "create or replace NONEDITIONABLE FUNCTION fncFertigations
RETURN SYS_REFCURSOR
AS
  l_refcur_operations SYS_REFCURSOR;
BEGIN
  OPEN l_refcur_operations FOR
    SELECT
      CropIrrigation.operationId AS "CropIrrigationOperationId",
      CropFertilization.operationId AS "CropFertilizationOperationId",
      Plant.designation,
      SectorIrrigationWithRecipe.recipeId,
      oa1.value AS "DurationInMinutes",
      oa2.value AS "PlotAreaInHaFertigated",
      ProductionFactor.designation,
      ProductionFactorUsedInFertigation.amountInLiters
    FROM
      CropIrrigation
    INNER JOIN
      CropFertilization ON CropFertilization.cropId = CropIrrigation.cropId
    INNER JOIN
      Crop ON Crop.cropId = CropIrrigation.cropId
    INNER JOIN
      Plant ON Plant.plantId = Crop.plantId
    INNER JOIN
      ProductionFactor ON ProductionFactor.productionFactorId = CropFertilization.productionFactorId
    INNER JOIN
      ProductionFactorUsedInFertigation ON ProductionFactor.productionFactorId = ProductionFactorUsedInFertigation.productionFactorId
    INNER JOIN
      SectorIrrigationWithRecipe ON SectorIrrigationWithRecipe.operationId = CropIrrigation.operationId
    INNER JOIN
      OperationAmount oa1 ON oa1.operationId = CropIrrigation.cropId AND oa1.unitId = 6
    INNER JOIN
      OperationAmount oa2 ON oa2.operationId = CropIrrigation.cropId AND oa2.unitId = 9
    ORDER BY
      CropIrrigation.operationId ASC;
  RETURN l_refcur_operations;
END;
/