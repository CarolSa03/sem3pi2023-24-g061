-- Register Weeding Operation Procedure
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

-- Delete Weeding Operation
create or replace NONEDITIONABLE function prcOperationWeedingDelete(pOperationId Weeding.operationId%type)
return int
as
begin
    delete from Weeding
    where Weeding.operationId = pOperationId;

    delete from OperationAmount
    where OperationAmount.operationId = pOperationId;

    delete from Operation
    where Operation.operationId = pOperationId;

    return sql%rowcount;
end;


-- Test procedure
declare
	vOperationId    Operation.operationId%type := 349;
	vOperationDate  Operation.operationDate%type := SYSDATE;
	vPlotId         Operation.plotId%type := 102;
	vCropId			Weeding.cropId%type := 11;
	vUnitId         Unit.unitId%type := 1;
	vValue          OperationAmount.value%type := 1.1;
 
begin
	prcOperationWeedingRegister(
	    pOperationId => vOperationId,
		pOperationDate => vOperationDate,        	
		pPlotId => vPlotId,
		pCropId => vCropId,
        pUnitId => vUnitId,
        pValue => vValue
	);

	DBMS_OUTPUT.PUT_LINE('Inserted.'); 
	
	rollback;
	DBMS_OUTPUT.PUT_LINE('Removed.');
    
	exception
		when others then
        	DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
end;
/