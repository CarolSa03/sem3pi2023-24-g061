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
        insert into Fertilization(operationId, fertilizationModeId)
        values (pOperationId, pFertilizationModeId);

        if pCropId != 0 then
            insert into CropFertilization(operationId, cropId)
            values (pOperationId, pCropId);
        end if;
    end if;
end;

DECLARE
    vOperationId            Operation.operationId%TYPE := 348;
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