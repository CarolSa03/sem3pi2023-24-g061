create or replace NONEDITIONABLE procedure prcIrrigationOperation(
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
