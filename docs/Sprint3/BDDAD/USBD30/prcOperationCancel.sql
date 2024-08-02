/
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