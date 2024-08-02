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
            raise_application_error(-20002, 'Error: it''s impossible to edit a Log.');
end;