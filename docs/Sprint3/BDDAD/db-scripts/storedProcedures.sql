create or replace NONEDITIONABLE PROCEDURE PRCCANCELOPERATION (operationId_in IN Operation.operationId%TYPE) AS
    v_operationDate Operation.operationDate%TYPE;
    v_count         int;
begin
  select operationDate 
  into v_operationDate
  from Operation
  where operationId = operationId_in;

  v_count := fncGetOperationTypes(operationId_in);

  if SYSDATE <= v_operationDate + 3 and v_count = 0 then
    insert into OperationLog(operationId, logTypeId, registeredTimestamp)
    values (operationId_in, 2, CURRENT_TIMESTAMP AT TIME ZONE 'WET');

    commit;
  else
    RAISE_APPLICATION_ERROR(-20004, 'The operation cannot be canceled: it is either outside the allowed timeframe or has dependent operations.');
  end if;

exception
  when NO_DATA_FOUND then
    RAISE_APPLICATION_ERROR(-20005, 'The specified operation could not be found.');
  when others then
    rollback;
    raise;
end;
/

/
create or replace NONEDITIONABLE procedure prcCropIrrigationRegister(
    pOperationId    CropIrrigation.operationId%type,
	pCropId			CropIrrigation.cropId%type,
    pOperationDate  Operation.operationDate%type,
	pPlotId			Operation.plotId%type,
	pUnitId			OperationAmount.unitId%type,
	pValue			OperationAmount.value%type
)
as
begin
    insert into Operation(operationId, plotId, operationDate)
    values (pOperationId, pPlotId, pOperationDate);

    insert into CropIrrigation (operationId, cropId)
    values (pOperationId, pCropId);

    insert into OperationAmount (operationId, unitId, value)
    values (pOperationId, pUnitId, pValue);
end;
/

/
create or replace NONEDITIONABLE procedure prcHarvestRegister(
    pOperationId        Operation.operationId%type,
    pOperationDate      Operation.operationDate%type,
    pPlotId             Operation.plotId%type,
    pProductId          Harvest.productId%type,
    pQuantity           OperationAmount.value%type,
    pUnitId             OperationAmount.unitId%type,
    pCropId             Harvest.cropId%type
)
as
    vCount              INTEGER;
begin

    select count(*)
    into vCount
    from crop
    inner join plant on crop.plantId = plant.plantId
    inner join product on product.plantId = plant.plantId
    where product.productId = pProductId;

    if vCount = 0 then
        raise_application_error(-20002, 'There are no crops in selected plot that give such product.');
    end if;

    insert into Operation(operationId, plotId, operationDate)
    values(pOperationId, pPlotId, pOperationDate);

    insert into OperationAmount(operationId, unitId, value)
    values(pOperationId, pUnitId, pQuantity);

    insert into Harvest(operationId, cropId, productId)
    values(pOperationId, pCropId, pProductId);
end;
/

/
create or replace NONEDITIONABLE procedure prcOperationWeedingRegister(
	pOperationId	Operation.operationId%type,
	pOperationDate  Operation.operationDate%type,
	pPlotId			Operation.plotId%type,
	pCropId			Weeding.cropId%type,
	pUnitId			OperationAmount.unitId%type,
	pValue			OperationAmount.value%type
)
as
begin
	insert into Operation(operationId, plotId, operationDate)
	values(pOperationId, pPlotId, pOperationDate);

	insert into OperationAmount(operationId, unitId, value)
	values (pOperationId, pUnitId, pValue);	

	insert into Weeding(operationId, cropId)
	values (pOperationId, pCropId);
end;
/

/
create or replace NONEDITIONABLE procedure prcProductionFactorApplianceRegister(
    pOperationId            Operation.operationId%type,
    pPlotId                 Operation.plotId%type,
    pOperationDate          Operation.operationDate%type,
    pQuantityValue          OperationAmount.value%type,
    pAreaValue              OperationAmount.value%type,
    pQuantityUnitId         Unit.unitId%type,
    pAreaUnitId             Unit.unitId%type,
    pProductionFactorId     ProductionFactorAppliance.productionFactorId%type,
    pFertilizationModeId    Fertilization.fertilizationModeId%type,
    pCropId                 CropFertilization.cropid%type
)
as
    vPlotArea               Plot.areaInHa%type;
begin
    select areaInHa
    into vPlotArea
    from Plot
    where plotId = pPlotId;

    if pAreaValue > vPlotArea then
        raise_application_error(-20001, 'The area of the plot where the production factor is being applied can´t be greater than the plot´s area');
    end if;

    insert into Operation(operationId, plotId, operationDate)
    values(pOperationId, pPlotId, pOperationDate);

    insert into ProductionFactorAppliance(operationId, productionFactorId)
    values(pOperationId, pProductionFactorId);

    if pQuantityUnitId != 0 then
        insert into OperationAmount(operationId, unitId, value)
        values(pOperationId, pQuantityUnitId, pQuantityValue);
    end if;

    if pAreaUnitId != 0 then
        insert into OperationAmount(operationId, unitId, value)
        values(pOperationId, pAreaUnitId, pAreaValue);
    end if; 

    if pFertilizationModeId != 0 then
        insert into Fertilization(operationId, productionFactorId, fertilizationModeId)
        values (pOperationId, pProductionFactorId, pFertilizationModeId);

        if pCropId != 0 then
            insert into CropFertilization(operationId, productionFactorId, cropId)
            values (pOperationId, pProductionFactorId, pCropId);
        end if;
    end if;
end;
/

/
create or replace NONEDITIONABLE PROCEDURE prcRecipeRegister(
    pRecipeId              Recipe.recipeId%TYPE,
    pProductionFactors     VARCHAR2,
    pUnits                 VARCHAR2,
    pAmounts               VARCHAR2
)
AS
    vProductionFactors     SYS_REFCURSOR;
    vUnits                 SYS_REFCURSOR;
    vAmounts               SYS_REFCURSOR;
    vProductionFactorId    ProductionFactorInRecipe.productionFactorId%TYPE;
    vUnitId                ProductionFactorInRecipe.unitId%TYPE;
    vAmount                ProductionFactorInRecipe.amount%TYPE;
BEGIN
    INSERT INTO RECIPE(recipeId) VALUES (pRecipeId);

    vProductionFactors := fncSplitSemiColonSeparatedString(pProductionFactors);
    vUnits := fncSplitSemiColonSeparatedString(pUnits);
    vAmounts := fncSplitSemiColonSeparatedString(pAmounts);

    LOOP
        FETCH vProductionFactors INTO vProductionFactorId;
        FETCH vUnits INTO vUnitId;
        FETCH vAmounts INTO vAmount;

        EXIT WHEN vProductionFactors%NOTFOUND OR vUnits%NOTFOUND OR vAmounts%NOTFOUND;

        INSERT INTO ProductionFactorInRecipe (recipeId, productionFactorId, unitId, amount)
        VALUES (pRecipeId, vProductionFactorId, vUnitId, vAmount);
    END LOOP;

    CLOSE vProductionFactors;
    CLOSE vUnits;
    CLOSE vAmounts;
END;
/

/
create or replace NONEDITIONABLE procedure prcSectorIrrigationRegister(
    pOperationId            Operation.operationId%type,
    pPlotId                 Operation.plotId%type,
    pOperationDate          Operation.operationDate%type,
    pTimeUnitId             OperationAmount.unitId%type,
    pDurationInMinutes      OperationAmount.value%type,
    pSectorId               SectorIrrigation.sectorId%type,
    pInitialTimestamp       SectorIrrigation.initialTimestamp%type,
    pFieldbookId            SectorIrrigationInFieldbook.fieldbookId%type,
    pRecipeId               ProductionFactorInRecipe.recipeId%type,
    pAreaUsed               OperationAmount.value%type,
    pAreaUnitId             OperationAmount.unitId%type
)
as
    vRecipeProductionFactorsCursor  SYS_REFCURSOR;
    vProductionFactorId             ProductionFactorInRecipe.productionFactorId%type;
    vUnitId                         ProductionFactorInRecipe.unitId%type;
    vAmount                         ProductionFactorInRecipe.amount%type;
    vFertilizationModeId            Fertilization.fertilizationModeId%type;
    vCropsCursor                    SYS_REFCURSOR;
    vCropId                         Crop.cropId%type;
    vLiters                         ProductionFactorUsedInFertigation.amountInLiters%type;
    vPFARegistered                  NUMBER;
    vFertilizationRegistered        NUMBER;
    vCropIrrigationRegistered       NUMBER;
    vPlotArea                       Plot.areaInHa%type;
begin   

    select areaInHa
    into vPlotArea
    from Plot
    where plotId = pPlotId;

    if pAreaUsed > vPlotArea then
        RAISE_APPLICATION_ERROR(-20010, 'The area (in ha) where the fertigation occurs can not be greater than the plot´s area.');
    end if;

    insert into Operation(operationId, plotId, operationDate)
    values (pOperationId, pPlotId, pOperationDate);

    insert into OperationAmount(operationId, unitId, value)
    values (pOperationId, pTimeUnitId, pDurationInMinutes);

    insert into SectorIrrigation(operationId, sectorId, initialTimestamp)
    values (pOperationId, pSectorId, pInitialTimestamp);

    if pFieldbookId != 0 then
        insert into SectorIrrigationInFieldbook(operationId, fieldbookId)
        values (pOperationId, pFieldbookId);
    end if;

    if pRecipeId != 0 then

        insert into SectorIrrigationWithRecipe(operationId, recipeId)
        values (pOperationId, pRecipeId);

        vRecipeProductionFactorsCursor := fncProductionFactorsInRecipe(pRecipeId);

        loop
            fetch vRecipeProductionFactorsCursor into vProductionFactorId, vUnitId, vAmount, vFertilizationModeId;
            exit when vRecipeProductionFactorsCursor%NOTFOUND;

            vPFARegistered := fncPFARegistered(pOperationId, vProductionFactorId);
            if vPFARegistered = 0 then
                insert into ProductionFactorAppliance(operationId, productionFactorId)
                values (pOperationId, vProductionFactorId);
            end if;

            if pAreaUsed != 0 then
                vLiters := fncGetAmountInLiters(pRecipeId, vProductionFactorId, pAreaUsed);

                insert into OperationAmount(operationId, unitId, value)
                values (pOperationId, pAreaUnitId, pAreaUsed);

                insert into ProductionFactorUsedInFertigation(operationId, productionFactorId, amountInLiters)
                values (pOperationId, vProductionFactorId, vLiters);
            end if;    

            vCropsCursor := fncCropsInSector(pSectorId);

            loop
                fetch vCropsCursor into vCropId;
                exit when vCropsCursor%NOTFOUND;

                if vFertilizationModeId != 0 then

                    vFertilizationRegistered := fncFertilizationRegistered(pOperationId, vProductionFactorId);
                    if vFertilizationRegistered = 0 then
                        insert into Fertilization(operationId, productionFactorId, fertilizationModeId)
                        values (pOperationId, vProductionFactorId, vFertilizationModeId);
                    end if;    
                    insert into CropFertilization(operationId, productionFactorId, cropId)
                    values(pOperationId, vProductionFactorId, vCropId);
                end if;

                vCropIrrigationRegistered := fncCropIrrigationRegistered(pOperationId, vCropId);
                if vCropIrrigationRegistered = 0 then
                    insert into CropIrrigation(operationId, cropId)
                    values(pOperationId, vCropId);
                end if;    
            end loop;    

            close vCropsCursor;
        end loop;

        close vRecipeProductionFactorsCursor;
    end if;

end;
/

/
create or replace NONEDITIONABLE procedure prcSowingRegister(
    pOperationId            Operation.operationId%type,
    pPlotId                 Operation.plotId%type,
    pOperationDate          Operation.operationDate%type,
    pQuantityValue          OperationAmount.value%type,
    pAreaValue              OperationAmount.value%type,
    pQuantityUnitId         Unit.unitId%type,
    pAreaUnitId             Unit.unitId%type,
    pCropId                 Sowing.cropid%type
)
as
    vPlotArea               Plot.areaInHa%type;
begin
    select areaInHa
    into vPlotArea
    from Plot
    where plotId = pPlotId;

    if pAreaValue > vPlotArea then
        raise_application_error(-20003, 'The area of the plot where the production factor is being applied can´t be greater than the plot´s area');
    end if;

    insert into Operation(operationId, plotId, operationDate)
    values(pOperationId, pPlotId, pOperationDate);

    insert into Sowing(operationId, cropid)
    values(pOperationId, pCropId);

    if pQuantityUnitId != 0 then
        insert into OperationAmount(operationId, unitId, value)
        values(pOperationId, pQuantityUnitId, pQuantityValue);
    end if;

    if pAreaUnitId != 0 then
        insert into OperationAmount(operationId, unitId, value)
        values(pOperationId, pAreaUnitId, pAreaValue);
    end if; 
end;
/