-- ** functions and procedures **


--ProductionFactorAppliance

create or replace NONEDITIONABLE procedure prcProductionFactorAppliances(pInitialDate in DATE, pFinalDate in DATE, pListProductionFactorAppliances out SYS_REFCURSOR)
as
begin
    open pListProductionFactorAppliances for
        select
            operation.operationDate,
            productionFactor.designation,
            plant.designation
        from
            Operation
                inner join
            ProductionFactorAppliance on productionFactorAppliance.operationId = operation.operationId
                inner join
            ProductionFactor on productionFactor.productionFactorId = productionFactorAppliance.productionFactorId
                inner join
            Crop on crop.cropId = operation.cropId
                inner join
            Plant on plant.plantId = crop.plantId
        where
            operation.operationDate BETWEEN pInitialDate AND pFinalDate;
end;
/

create or replace NONEDITIONABLE procedure prcProductionFactorApplianceRegister(
    pOperationId            Operation.operationId%type,
    pCropId                 Operation.cropId%type,
    pOperationTypeId        Operation.operationTypeId%type,
    pOperationDate          Operation.operationDate%type,
    pAmount                 Operation.amount%type,
    pUnit                   Operation.unit%type,
    pProductionFactorId     ProductionFactorAppliance.productionFactorId%type
)
as
begin

    insert into Operation(operationId, cropId, operationTypeId, operationDate, amount, unit)
    values(pOperationId, pCropId, pOperationTypeId, pOperationDate, pAmount, pUnit);

    insert into ProductionFactorAppliance(operationId, productionFactorId)
    values(pOperationId, pProductionFactorId);
end;
/

create or replace NONEDITIONABLE function fncProductionFactorApplianceDelete(pOperationId   Operation.operationId%type)
    return int
as
begin

    delete from ProductionFactorAppliance
    where operationId = pOperationId;

    delete from Operation
    where operationId = pOperationId;

    return sql%rowcount;
end;
/