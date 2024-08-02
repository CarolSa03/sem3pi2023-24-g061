create or replace NONEDITIONABLE FUNCTION fncAreaUnits
RETURN SYS_REFCURSOR
AS
  l_refcur_units SYS_REFCURSOR;
BEGIN
  OPEN l_refcur_units FOR
    SELECT
      unit.unitId,
      unit.designation,
      unitType.designation
    FROM
      Unit
    INNER JOIN
      UnitType ON unitType.unitTypeId = unit.unitTypeId
    WHERE
      unitType.designation = 'Área'
    ORDER BY
      unit.unitId ASC;
  RETURN l_refcur_units;
END;
/

/
create or replace NONEDITIONABLE FUNCTION FNCCROPIRRIGATIONREGISTERED(
  POPERATIONID IN NUMBER,
  PCROPID IN NUMBER)
RETURN NUMBER
AS
    VCOUNT NUMBER;
BEGIN
  SELECT COUNT(*)
  INTO VCOUNT
  FROM CROPIRRIGATION
  WHERE OPERATIONID = POPERATIONID
  AND CROPID = PCROPID;
  RETURN VCOUNT;
END FNCCROPIRRIGATIONREGISTERED;
/

/
create or replace NONEDITIONABLE FUNCTION fncCropOfProduct(pPlotId Crop.plotId%type, pProductId Product.productId%type)
RETURN SYS_REFCURSOR
AS
  l_refcur_crops SYS_REFCURSOR;
BEGIN
  OPEN l_refcur_crops FOR
    SELECT
      crop.cropId,
      plant.designation
    FROM
      Crop
    INNER JOIN
      Plant ON plant.plantId = crop.plantId
    INNER JOIN
      Product ON product.plantId = plant.plantId
    WHERE
      crop.plotId = pPlotId
      AND product.productId = pProductId
    ORDER BY
      crop.cropId ASC;
  RETURN l_refcur_crops;
END;
/

/
create or replace NONEDITIONABLE FUNCTION fncCropsWithHigherWateringInYear(pYear IN NUMBER)
RETURN SYS_REFCURSOR
AS
  l_refcur_crops SYS_REFCURSOR;
BEGIN
  OPEN l_refcur_crops FOR
    SELECT
      Plant.designation
    FROM
      Crop
    INNER JOIN
      Plant ON Plant.plantId = Crop.plantId
    INNER JOIN
      CropIrrigation ON CropIrrigation.cropId = Crop.cropId
    INNER JOIN
      Operation ON Operation.operationId = CropIrrigation.operationId
    INNER JOIN
      OperationAmount ON OperationAmount.operationId = Operation.operationId
    WHERE
      EXTRACT(YEAR FROM operationDate) = pYear
      AND OperationAmount.unitId = 6;
  RETURN l_refcur_crops;
END;
/

/
create or replace NONEDITIONABLE FUNCTION fncCropsInPlot(pPlotId crop.plotId%type)
RETURN SYS_REFCURSOR
AS
  l_refcur_crops SYS_REFCURSOR;
BEGIN
  OPEN l_refcur_crops FOR
    SELECT
      crop.cropId,
      plant.designation
    FROM
      Crop
    INNER JOIN
      Plant ON plant.plantId = crop.plantId
    WHERE
      crop.plotId = pPlotId
    ORDER BY
      crop.cropId ASC;
  RETURN l_refcur_crops;
END;
/

/
create or replace NONEDITIONABLE FUNCTION fncCropsInSector(pSectorId CropInSector.sectorId%type)
RETURN SYS_REFCURSOR
AS
  l_refcur_crops SYS_REFCURSOR;
BEGIN
  OPEN l_refcur_crops FOR
    SELECT
      Crop.cropId,
      Plant.designation
    FROM
      Crop
    INNER JOIN
      Plant ON Crop.plantId = Plant.plantId
    INNER JOIN
      CropInSector ON CropInSector.cropId = Crop.cropId
    WHERE
      CropInSector.sectorId = pSectorId
    ORDER BY
      Crop.cropId ASC;
  RETURN l_refcur_crops;
END;
/

/
create or replace NONEDITIONABLE FUNCTION fncFertigations
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

/
create or replace NONEDITIONABLE FUNCTION fncFertilizationModes
RETURN SYS_REFCURSOR
AS
  l_refcur_fertilizationModes SYS_REFCURSOR;
BEGIN
  OPEN l_refcur_fertilizationModes FOR
    SELECT
      *
    FROM
      FertilizationMode
    ORDER BY
      fertilizationMode.fertilizationModeId ASC;
  RETURN l_refcur_fertilizationModes;
END;
/

/
create or replace NONEDITIONABLE FUNCTION FNCFERTILIZATIONREGISTERED(
  POPERATIONID IN NUMBER,
  PPRODUCTIONFACTORID IN NUMBER)
RETURN NUMBER
AS
    VCOUNT NUMBER;
BEGIN
  SELECT COUNT(*)
  INTO VCOUNT
  FROM FERTILIZATION
  WHERE OPERATIONID = POPERATIONID
  AND PRODUCTIONFACTORID = PPRODUCTIONFACTORID;
  RETURN VCOUNT;
END FNCFERTILIZATIONREGISTERED;
/

/
create or replace NONEDITIONABLE FUNCTION fncGenerateOperationId
RETURN NUMBER
AS
  vOperationId NUMBER;
BEGIN
    SELECT MAX(operationId)
    INTO vOperationId
    FROM Operation;
    RETURN NVL(vOperationId + 1, 1);
END fncGenerateOperationId;
/

/
create or replace NONEDITIONABLE FUNCTION fncGetAmountInLiters(
    pRecipeId ProductionFactorInRecipe.recipeId%TYPE, 
    pProductionFactorId ProductionFactorInRecipe.productionFactorId%TYPE, 
    pAreaUsed OperationAmount.value%type
)
RETURN NUMBER
AS
    vAmountInLiters NUMBER;
BEGIN
    SELECT amount * pAreaUsed INTO vAmountInLiters
    FROM productionfactorinrecipe
    WHERE recipeid = pRecipeId AND productionfactorid = pProductionFactorId;

    RETURN vAmountInLiters;
end;
/

/
create or replace NONEDITIONABLE FUNCTION FNCGETOPERATIONSSOWING (
  pPlotId crop.plotId%type,
  pInitialDate IN DATE,
  pFinalDate IN DATE
)
RETURN SYS_REFCURSOR
AS
  l_refcur_operationsSowing SYS_REFCURSOR;
BEGIN
  OPEN l_refcur_operationsSowing FOR
    SELECT
      op.operationDate,
      oa.value AS operation_amount, 
      u.designation AS unit_designation, 
      COALESCE(pt.designation, 'Sem Parcela') AS plot_designation,
      COALESCE(pl.designation, 'Sem Cultura') AS plant_designation
    FROM
      Operation op
      LEFT JOIN Sowing sow ON sow.operationId = op.operationId
      INNER JOIN OperationAmount oa ON oa.operationId = op.operationId
      INNER JOIN Plot pt ON pt.plotid = op.plotid
      INNER JOIN Unit u ON u.unitId = oa.unitId
      LEFT JOIN Crop cr ON cr.cropId = sow.cropId
      LEFT JOIN Plant pl ON pl.plantId = cr.plantId
    WHERE
      op.operationDate BETWEEN pInitialDate AND pFinalDate
      AND op.plotId = pPlotId
      AND NOT EXISTS (SELECT 1 FROM operationLog
                        WHERE operationLog.operationId = op.operationId
                        AND operationLog.logTypeId = 2) 
    ORDER BY
      op.operationDate ASC;  
  RETURN l_refcur_operationsSowing;
END FNCGETOPERATIONSSOWING;
/

/
create or replace NONEDITIONABLE FUNCTION fncGetOperationTypes(pOperationId Operation.operationId%type)
RETURN VARCHAR2 AS
    vOperationType VARCHAR2(20);
BEGIN
    SELECT operationType INTO vOperationType
    FROM (
             SELECT DISTINCT 'Plantation' as operationType FROM Operation, Plantation WHERE pOperationId = Plantation.operationId
             UNION
             SELECT DISTINCT 'SoilIncorporation' as operationType FROM Operation, SoilIncorporation WHERE pOperationId = SoilIncorporation.operationId
             UNION
             SELECT DISTINCT 'Sowing' as operationType FROM Operation, Sowing WHERE pOperationId = Sowing.operationId
             UNION
             SELECT DISTINCT 'CropIrrigation' as operationType FROM Operation, CropIrrigation WHERE pOperationId = CropIrrigation.operationId
             UNION
             SELECT DISTINCT 'SectorIrrigation' as operationType FROM Operation, SectorIrrigation WHERE pOperationId = SectorIrrigation.operationId
             UNION
             SELECT DISTINCT 'Pruning' as operationType FROM Operation, Pruning WHERE pOperationId = Pruning.operationId
             UNION
             SELECT DISTINCT 'Weeding' as operationType FROM Operation, Weeding WHERE pOperationId = Weeding.operationId
             UNION
             SELECT DISTINCT 'Harvest' as operationType FROM Operation, Harvest WHERE pOperationId = Harvest.operationId
             UNION
             SELECT DISTINCT 'CropFertilization' as operationType FROM Operation, CropFertilization WHERE pOperationId = CropFertilization.operationId
             UNION
             SELECT DISTINCT 'ProductionFactorAppliance' as operationType FROM Operation, ProductionFactorAppliance WHERE pOperationId = ProductionFactorAppliance.operationId
         )
    WHERE ROWNUM = 1;

    RETURN vOperationType;
END;
/

/
create or replace NONEDITIONABLE function fncHarvestOperations(pInitialDate in DATE, pFinalDate in DATE)
return SYS_REFCURSOR
as
    pListProductionFactorAppliances SYS_REFCURSOR;
begin
    open pListProductionFactorAppliances for
        select
            Operation.operationDate,
            ProductionFactor.designation,
            Plant.designation
        from
            Operation
        inner join
            ProductionFactorAppliance on ProductionFactorAppliance.operationId = Operation.operationId
        inner join
            ProductionFactor on ProductionFactor.productionFactorId = ProductionFactorAppliance.productionFactorId
        inner join
            Harvest on Harvest.operationId = Operation.operationId
        inner join
            Crop on Crop.cropId = Harvest.cropId
        inner join
            Plant on Plant.plantId = Crop.plantId
        where
            Operation.operationDate BETWEEN pInitialDate AND pFinalDate
            AND NOT EXISTS (SELECT 1 FROM operationLog
                            WHERE operationLog.operationId = operation.operationId
                            AND operationLog.logTypeId = 2);
end;
/

/
create or replace NONEDITIONABLE FUNCTION FNCHARVESTSLIST(
    agriculturalPlotName IN Plot.plotid%TYPE,
    beginDate IN DATE,
    endDate IN DATE
)
RETURN SYS_REFCURSOR
IS
    resultList SYS_REFCURSOR;
    agriculturalPlotExists NUMBER := 0;
    var_agriculturaPLotID Operation.operationId%TYPE;
BEGIN
    SELECT COUNT(*) INTO agriculturalPlotExists
    FROM Plot
    WHERE LOWER(Plot.designation) LIKE LOWER(agriculturalPlotName);

    IF agriculturalPlotExists = 0 THEN
        DBMS_OUTPUT.PUT_LINE('Agricultural plot not found with the provided name.');
    ELSE
        SELECT plotId INTO var_agriculturaPlotID
        FROM Plot
        WHERE LOWER(Plot.designation) LIKE LOWER(agriculturalPlotName);

        OPEN resultList FOR
            SELECT Plant.plantvarietyId, Product.designation
            FROM Harvest
            JOIN Operation ON Harvest.operationId = Operation.operationId
            JOIN Crop ON Harvest.cropId = Crop.cropId
            JOIN Product ON Harvest.productId = Product.productId
            JOIN Plant ON Product.plantId = Plant.plantId
            WHERE Operation.operationDate BETWEEN TO_DATE(beginDate, 'DD/Mon/YYYY') AND TO_DATE(endDate, 'DD/Mon/YYYY')
              AND Crop.plotId = var_agriculturaPLotID
              AND NOT EXISTS (SELECT 1 FROM operationLog
                                WHERE operationLog.operationId = operation.operationId
                                AND operationLog.logTypeId = 2)
            GROUP BY plant.plantvarietyId, Product.designation;
    END IF;

    RETURN resultList;
END FNCHARVESTSLIST;
/

/
create or replace NONEDITIONABLE function fncIrrigations
return SYS_REFCURSOR
as
    l_refcur_irrigations SYS_REFCURSOR;
begin
    open l_refcur_irrigations for
        select
            SectorIrrigation.sectorId,
            Operation.operationDate,
            NVL(OperationAmount.value,0),
            SectorIrrigation.initialTimestamp,
            NVL(SectorIrrigationWithRecipe.recipeId,0)
        from
            Operation
        inner join
            OperationAmount on Operation.operationId = OperationAmount.operationId
        inner join
            SectorIrrigation on Operation.operationId = SectorIrrigation.operationId
        left join
            SectorIrrigationWithRecipe on SectorIrrigationWithRecipe.operationId = SectorIrrigation.operationId  
        where
            NOT EXISTS (SELECT 1 FROM operationLog
                        WHERE operationLog.operationId = operation.operationId
                        AND operationLog.logTypeId = 2);
    return l_refcur_irrigations;
end;
/

/
create or replace NONEDITIONABLE function fncIrrigationsInFieldbook
return SYS_REFCURSOR
as
    l_refcur_irrigations SYS_REFCURSOR;
begin
    open l_refcur_irrigations for
        select
            SectorIrrigation.sectorId,
            Operation.operationDate,
            NVL(OperationAmount.value,0),
            SectorIrrigation.initialTimestamp,
            NVL(SectorIrrigationWithRecipe.recipeId,0)
        from
            Operation
        left join
            OperationAmount on Operation.operationId = OperationAmount.operationId
        inner join
            SectorIrrigation on Operation.operationId = SectorIrrigation.operationId
        inner join
            SectorIrrigationInFieldbook on SectorIrrigationInFieldbook.operationId = SectorIrrigation.operationId
        left join
            SectorIrrigationWithRecipe on SectorIrrigationWithRecipe.operationId = SectorIrrigation.operationId  
        where
            SectorIrrigationInFieldbook.fieldbookId = 1
        and NOT EXISTS (SELECT 1 FROM operationLog
                        WHERE operationLog.operationId = operation.operationId
                        AND operationLog.logTypeId = 2)
        order by
            operation.operationId asc;
    return l_refcur_irrigations;
end;
/

/
create or replace NONEDITIONABLE FUNCTION fncLastIrrigationInFieldbook
RETURN SYS_REFCURSOR
AS
    l_refcur_irrigations SYS_REFCURSOR;
BEGIN
    OPEN l_refcur_irrigations FOR
        select
            SectorIrrigation.sectorId,
            Operation.operationDate,
            OperationAmount.value,
            SectorIrrigation.initialTimestamp,
            NVL(SectorIrrigationWithRecipe.recipeId,0)
        from
            Operation
        inner join
            OperationAmount on Operation.operationId = OperationAmount.operationId
        inner join
            SectorIrrigation on Operation.operationId = SectorIrrigation.operationId
        inner join
            SectorIrrigationInFieldbook on SectorIrrigationInFieldbook.operationId = SectorIrrigation.operationId
        left join
            SectorIrrigationWithRecipe on SectorIrrigationWithRecipe.operationId = SectorIrrigation.operationId    
        where
            SectorIrrigationInFieldbook.fieldbookId = 1
        and
            not exists (select 1 
                        from operationLog 
                        where operationLog.operationId = operation.operationId 
                        and operationLog.logTypeId = 2)
        and operation.operationId = (select max(operationId)
                                from SectorIrrigationInFieldbook);
    RETURN l_refcur_irrigations;
END;
/

/
create or replace NONEDITIONABLE function fncOperations
return SYS_REFCURSOR
as
    l_refcur_operations SYS_REFCURSOR;
begin
    open l_refcur_operations for
        select * from Operation;
    return l_refcur_operations;
end;
/

/
create or replace NONEDITIONABLE FUNCTION FNCOPERATIONSOFTODAY
RETURN SYS_REFCURSOR
AS
    L_REFCUR_OPERATIONS SYS_REFCURSOR;
BEGIN
    OPEN L_REFCUR_OPERATIONS FOR
        SELECT
            OPERATION.OPERATIONID,
            OPERATIONLOG.REGISTEREDTIMESTAMP
        FROM
            OPERATION
        INNER JOIN
            OPERATIONLOG ON OPERATION.OPERATIONID = OPERATIONLOG.OPERATIONID
        WHERE
            OPERATIONLOG.LOGTYPEID = 1
            AND TRUNC(OPERATIONLOG.REGISTEREDTIMESTAMP) = TRUNC(CURRENT_DATE);
    RETURN L_REFCUR_OPERATIONS;
END;
/

/
create or replace NONEDITIONABLE FUNCTION FNCPFAREGISTERED(
  POPERATIONID IN NUMBER,
  PPRODUCTIONFACTORID IN NUMBER)
RETURN NUMBER
AS
    VCOUNT NUMBER;
BEGIN
  SELECT COUNT(*)
  INTO VCOUNT
  FROM PRODUCTIONFACTORAPPLIANCE
  WHERE OPERATIONID = POPERATIONID
  AND PRODUCTIONFACTORID = PPRODUCTIONFACTORID;
  RETURN VCOUNT;
END FNCPFAREGISTERED;
/

/
create or replace NONEDITIONABLE function fncPlotIdCropId (pCropId Weeding.cropId%type)
return number is
	vPlotId NUMBER;
begin
	select plotId into vPlotId from crop
		where crop.cropId = pCropId;

	return vPlotId;
end;
/

/
create or replace NONEDITIONABLE FUNCTION fncPlots
RETURN SYS_REFCURSOR
AS
  l_refcur_plots SYS_REFCURSOR;
BEGIN
  OPEN l_refcur_plots FOR
    SELECT
      *
    FROM
      Plot  
    ORDER BY
      plot.plotId ASC;
  RETURN l_refcur_plots;
END;
/

/
create or replace NONEDITIONABLE FUNCTION fncPlotsInSector(pSectorId CropInSector.sectorId%type)
RETURN SYS_REFCURSOR
AS
  l_refcur_plots SYS_REFCURSOR;
BEGIN
  OPEN l_refcur_plots FOR
    SELECT DISTINCT
      Plot.plotId,
      Plot.designation,
      Plot.areaInHa
    FROM
      Plot
    INNER JOIN
      Crop ON Crop.plotId = Plot.plotId
    INNER JOIN
      CropInSector ON CropInSector.cropId = Crop.cropId
    WHERE
      CropInSector.sectorId = pSectorId
    ORDER BY
      Plot.plotId ASC;
  RETURN l_refcur_plots;
END;
/

/
create or replace NONEDITIONABLE FUNCTION fncProductionFactorAppliances(pPlotId crop.plotId%type, pInitialDate in DATE, pFinalDate in DATE)
RETURN SYS_REFCURSOR
AS
  l_refcur_productionFactorAppliances SYS_REFCURSOR;
BEGIN
  OPEN l_refcur_productionFactorAppliances FOR
    SELECT
      operation.operationDate,
      productionFactor.designation,
      COALESCE(plant.designation, 'Sem cultura')
    FROM
      Operation
      INNER JOIN ProductionFactorAppliance ON productionFactorAppliance.operationId = operation.operationId
      INNER JOIN ProductionFactor ON productionFactor.productionFactorId = productionFactorAppliance.productionFactorId
      LEFT JOIN CropFertilization ON cropFertilization.operationId = operation.operationId
      LEFT JOIN Crop ON crop.cropId = cropFertilization.cropId
      LEFT JOIN Plant ON plant.plantId = crop.plantId
    WHERE
      operation.operationDate BETWEEN pInitialDate AND pFinalDate
      AND operation.plotId = pPlotId
      AND NOT EXISTS (SELECT 1 FROM operationLog
                WHERE operationLog.operationId = operation.operationId
                AND operationLog.logTypeId = 2)
    ORDER BY
      operation.operationDate ASC;
  RETURN l_refcur_productionFactorAppliances;
END;
/

/
create or replace NONEDITIONABLE function fncProductionFactorInPlotList(pPlotId Operation.plotId%type, pInitialDate in DATE, pFinalDate in DATE)
return SYS_REFCURSOR
as
    pListProductionFactor SYS_REFCURSOR;
begin
    open pListProductionFactor for
        select
            operation.operationDate,
            productionFactor.designation,
            substance.designation,
            substanceDataSheet.percentage
        from
            Plot
        inner join
            Crop on crop.plotId = Plot.plotId
        inner join
            Operation on operation.operationId = Crop.cropId
        inner join
            ProductionFactorAppliance on productionFactorAppliance.operationId = operation.operationId
        inner join
            ProductionFactor on productionFactor.productionFactorId = productionFactorAppliance.productionFactorId
        inner join
            SubstanceDataSheet on substanceDataSheet.productionFactorId = productionFactor.productionFactorId
        inner join
            Substance on substance.substanceId = substanceDataSheet.substanceId
        where
            operation.operationDate BETWEEN pInitialDate AND pFinalDate
            AND Plot.plotId = pPlotId
            AND NOT EXISTS (SELECT 1 FROM operationLog
                            WHERE operationLog.operationId = operation.operationId
                            AND operationLog.logTypeId = 2);      
end;
/

/
create or replace NONEDITIONABLE FUNCTION fncProductionFactors
RETURN SYS_REFCURSOR
AS
  l_refcur_productionFactors SYS_REFCURSOR;
BEGIN
  OPEN l_refcur_productionFactors FOR
    SELECT
      productionFactor.productionFactorId,
      productionFactor.designation,
      productionFactorType.designation
    FROM
      ProductionFactor
    INNER JOIN
      ProductionFactorType ON productionFactorType.productionFactorTypeId = productionFactor.productionFactorTypeId
    ORDER BY
      productionFactor.productionFactorId ASC;
  RETURN l_refcur_productionFactors;
END;
/

/
create or replace NONEDITIONABLE FUNCTION fncProductionFactorsInRecipe(pRecipeId ProductionFactorInRecipe.recipeId%type)
RETURN SYS_REFCURSOR
AS
  l_refcur_productionFactors SYS_REFCURSOR;
BEGIN
  OPEN l_refcur_productionFactors FOR
    SELECT
      ProductionFactorInRecipe.productionFactorId,
      ProductionFactorInRecipe.unitId,
      ProductionFactorInRecipe.amount,
      NVL(Fertilization.fertilizationModeId,0)
    FROM
      ProductionFactorInRecipe
    INNER JOIN
      ProductionFactorAppliance ON ProductionFactorAppliance.productionFactorId = ProductionFactorInRecipe.productionFactorId
    LEFT JOIN
      Fertilization ON Fertilization.operationId = ProductionFactorAppliance.operationId
    ORDER BY
      ProductionFactorInRecipe.productionFactorId ASC;
  RETURN l_refcur_productionFactors;
END;
/

/
create or replace NONEDITIONABLE FUNCTION fncProducts
RETURN SYS_REFCURSOR
AS
  l_refcur_products SYS_REFCURSOR;
BEGIN
  OPEN l_refcur_products FOR
    SELECT
      productId,
      designation
    FROM
      Product  
    ORDER BY
      productId ASC;
  RETURN l_refcur_products;
END;
/

/
create or replace NONEDITIONABLE FUNCTION fncQuantityUnits
RETURN SYS_REFCURSOR
AS
  l_refcur_units SYS_REFCURSOR;
BEGIN
  OPEN l_refcur_units FOR
    SELECT
      unit.unitId,
      unit.designation,
      unitType.designation
    FROM
      Unit
    INNER JOIN
      UnitType ON unitType.unitTypeId = unit.unitTypeId
    WHERE
      unitType.designation = 'Quantidade'
    ORDER BY
      unit.unitId ASC;
  RETURN l_refcur_units;
END;
/

/
create or replace NONEDITIONABLE FUNCTION fncRatioUnits
RETURN SYS_REFCURSOR
AS
  l_refcur_units SYS_REFCURSOR;
BEGIN
  OPEN l_refcur_units FOR
    SELECT
      unit.unitId,
      unit.designation,
      unitType.designation
    FROM
      Unit
    INNER JOIN
      UnitType ON unitType.unitTypeId = unit.unitTypeId
    WHERE
      unitType.designation = 'Rácio'
    ORDER BY
      unit.unitId ASC;
  RETURN l_refcur_units;
END;
/

/
create or replace NONEDITIONABLE function fncRecipeDelete(pRecipeId   Recipe.recipeId%type)
return int
as
begin
    delete ProductionFactorInRecipe where ProductionFactorInRecipe.recipeId=pRecipeId;
    delete recipe where recipe.recipeId=pRecipeId;

    return sql%rowcount;
end;
/

/
create or replace NONEDITIONABLE function fncRecipes
return SYS_REFCURSOR
as
    l_refcur_recipes SYS_REFCURSOR;
begin
    open l_refcur_recipes for
        select
            Recipe.recipeId,
            ProductionFactor.designation,
            Unit.designation,
            ProductionFactorInRecipe.amount
        from
            Recipe
        inner join
            ProductionFactorInRecipe on ProductionFactorInRecipe.recipeId = Recipe.recipeId
        inner join
            ProductionFactor on ProductionFactorInRecipe.productionFactorId = ProductionFactor.productionFactorId
        inner join
            Unit on Unit.unitId = ProductionFactorInRecipe.unitId
        order by
            Recipe.recipeId;
    return l_refcur_recipes;
end;
/

/
CREATE OR REPLACE FUNCTION fncSectors
RETURN SYS_REFCURSOR
AS
  l_refcur_sectors SYS_REFCURSOR;
BEGIN
  OPEN l_refcur_sectors FOR
    SELECT
      *
    FROM
      Sector  
    ORDER BY
      Sector.sectorId ASC;
  RETURN l_refcur_sectors;
END;
/

/
create or replace NONEDITIONABLE FUNCTION fncSplitSemiColonSeparatedString(pList IN VARCHAR2)
RETURN SYS_REFCURSOR
IS
    vCursor SYS_REFCURSOR;
BEGIN
    OPEN vCursor FOR
    SELECT TO_NUMBER(TRIM(SUBSTR(txt,
                       instr(txt, ';', 1, level) + 1,
                       instr(txt, ';', 1, level + 1) - instr(txt, ';', 1, level) - 1))) AS value
    FROM (SELECT ';' || pList || ';' AS txt FROM dual)
    CONNECT BY level <= LENGTH(pList) - LENGTH(REPLACE(pList, ';')) + 1
    ORDER BY level;

    RETURN vCursor;
END;
/

/
create or replace NONEDITIONABLE FUNCTION fncTimeUnits
RETURN SYS_REFCURSOR
AS
  l_refcur_units SYS_REFCURSOR;
BEGIN
  OPEN l_refcur_units FOR
    SELECT
      unit.unitId,
      unit.designation,
      unitType.designation
    FROM
      Unit
    INNER JOIN
      UnitType ON unitType.unitTypeId = unit.unitTypeId
    WHERE
      unitType.designation = 'Tempo'
    ORDER BY
      unit.unitId ASC;
  RETURN l_refcur_units;
END;
/

/
create or replace NONEDITIONABLE FUNCTION fncUnits
RETURN SYS_REFCURSOR
AS
  l_refcur_units SYS_REFCURSOR;
BEGIN
  OPEN l_refcur_units FOR
    SELECT
      unit.unitId,
      unit.designation,
      unitType.designation
    FROM
      Unit
    INNER JOIN
      UnitType ON unitType.unitTypeId = unit.unitTypeId
    ORDER BY
      unit.unitId ASC;
  RETURN l_refcur_units;
END;
/

/
create or replace NONEDITIONABLE function fncWeedingOperations (pInitialDate in DATE, pFinalDate in DATE)
return sys_refcursor
as
  l_refcur_weedingOperations sys_refcursor;
begin
    open l_refcur_weedingOperations for
        select
            operation.operationDate,
            crop.plotId,
            weeding.cropId,
            operationAmount.value,
            operationAmount.unitId
        from
            weeding
            join operation on weeding.operationId = operation.operationId
            join crop on weeding.cropId = crop.cropId
            join operationAmount on operation.operationId = operationAmount.operationId
            where
                operation.operationDate between pInitialDate and pFinalDate
                and not exists (select 1 from operationLog
                        where operationLog.operationId = operation.operationId
                        and operationLog.logTypeId = 2)
            order by
                weeding.operationId;

    return l_refcur_weedingOperations;
end;
/