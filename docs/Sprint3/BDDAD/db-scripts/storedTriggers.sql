create or replace NONEDITIONABLE trigger trgImpossibleDeleteLog
before delete or update on OperationLog
for each row
declare
    exception_delete exception;
    exception_update exception;
begin
    if deleting then
        raise exception_delete;
    elsif updating then
        raise exception_update;
    end if;

    exception
        when exception_delete then
            raise_application_error(-20001, 'Error: it''s impossible to delete a Log.');
        when exception_update then
            raise_application_error(-20002, 'Error: it''s impossible to edit a Log.');
end;
/

/
create or replace NONEDITIONABLE trigger trgRegisterLogFromOperation
after insert or update on Operation
for each row
begin
    insert into OperationLog(operationId, logTypeId, registeredTimestamp)
    values (:NEW.operationId, 1, CURRENT_TIMESTAMP AT TIME ZONE 'WET');
end;
/

/
create or replace NONEDITIONABLE TRIGGER trgUpdateRegisteredTimestampAndLogType
    before insert on OperationLog
    for each row
begin
    if :new.registeredTimestamp is null and :new.logTypeId = 1 then
        :new.registeredTimestamp := CURRENT_TIMESTAMP AT TIME ZONE 'WET';
    elsif :old.logTypeId = 1 and :new.logTypeId = 2 then
        :new.registeredTimestamp := :OLD.registeredTimestamp;
    end if;    
end;
/

/
CREATE OR REPLACE TRIGGER TRGIMPOSSIBLEDELETEOPERATION 
BEFORE DELETE ON OPERATION 
FOR EACH ROW
BEGIN
  PRCCANCELOPERATION(:OLD.OPERATIONID);
END;
/