create or replace NONEDITIONABLE TRIGGER trgUpdateRegisteredTimestampAndLogType
    before insert on OperationLog
    for each row
begin
    if :new.registeredTimestamp is null and :new.logTypeId = 1 then
        :new.registeredTimestamp := CURRENT_TIMESTAMP;
    elsif :old.logTypeId = 1 and :new.logTypeId = 2 then
        :new.registeredTimestamp := :OLD.registeredTimestamp;
    end if;
end;