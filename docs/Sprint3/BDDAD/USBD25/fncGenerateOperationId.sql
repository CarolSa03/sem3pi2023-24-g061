SET SERVEROUTPUT ON;

CREATE SEQUENCE operationIdGenerator
    START WITH 482
    INCREMENT BY 1
    NOCACHE;
/

/
create or replace NONEDITIONABLE FUNCTION fncGenerateOperationId
RETURN NUMBER
AS
  vOperationId NUMBER;
BEGIN
  SELECT operationIdGenerator.nextval INTO vOperationId FROM dual;
  RETURN vOperationId;
END fncGenerateOperationId;
/

/
DECLARE
    TYPE OperationRecord IS RECORD (
        operationId OPERATION.OPERATIONID%TYPE,
        registeredTimestamp OPERATIONLOG.REGISTEREDTIMESTAMP%TYPE
    );

    CURSOR l_refcur_operations IS
        SELECT
            OPERATION.OPERATIONID,
            OPERATIONLOG.REGISTEREDTIMESTAMP
        FROM
            OPERATION
        INNER JOIN
            OPERATIONLOG ON OPERATION.OPERATIONID = OPERATIONLOG.OPERATIONID
        WHERE
            OPERATIONLOG.LOGTYPEID = 1
            AND TRUNC(OPERATIONLOG.REGISTEREDTIMESTAMP) = TRUNC(CURRENT_DATE);

    recOperations OperationRecord;
BEGIN
    OPEN l_refcur_operations;

    LOOP
        FETCH l_refcur_operations INTO recOperations;
        EXIT WHEN l_refcur_operations%NOTFOUND;

        DBMS_OUTPUT.PUT_LINE('Operation ID: ' || recOperations.operationId ||
                             ', Registered Timestamp: ' || recOperations.registeredTimestamp);
    END LOOP;

    CLOSE l_refcur_operations;
END;
/
