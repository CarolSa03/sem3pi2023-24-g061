create or replace NONEDITIONABLE trigger trgRegisterLogFromOperation
after insert or update on Operation
for each row
begin
    insert into OperationLog(operationId, logTypeId, registeredTimestamp)
    values (:NEW.operationId, 1, CURRENT_TIMESTAMP);
end;