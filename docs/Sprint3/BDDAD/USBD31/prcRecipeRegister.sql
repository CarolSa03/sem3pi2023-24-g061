SET SERVEROUTPUT ON;

create or replace NONEDITIONABLE FUNCTION fncSplitCommaSeparatedString(pList IN VARCHAR2)
RETURN SYS_REFCURSOR
IS
    vCursor SYS_REFCURSOR;
BEGIN
    OPEN vCursor FOR
    SELECT TO_NUMBER(TRIM(SUBSTR(txt,
                       instr(txt, ',', 1, level) + 1,
                       instr(txt, ',', 1, level + 1) - instr(txt, ',', 1, level) - 1))) AS value
    FROM (SELECT ',' || pList || ',' AS txt FROM dual)
    CONNECT BY level <= LENGTH(pList) - LENGTH(REPLACE(pList, ',')) + 1
    ORDER BY level;

    RETURN vCursor;
END;
/

/
create or replace NONEDITIONABLE PROCEDURE prcRecipeRegister(
    pRecipeId              Recipe.recipeId%TYPE,
    pProductionFactors     VARCHAR2,
    pUnits                 VARCHAR2,
    pAmounts               VARCHAR2
)
AS
    vProductionFactors     SYS_REFCURSOR;
    vUnits                 SYS_REFCURSOR;
    vAmounts               SYS_REFCURSOR;
    vProductionFactorId    ProductionFactorInRecipe.productionFactorId%TYPE;
    vUnitId                ProductionFactorInRecipe.unitId%TYPE;
    vAmount                ProductionFactorInRecipe.amount%TYPE;
BEGIN
    INSERT INTO RECIPE(recipeId) VALUES (pRecipeId);

    vProductionFactors := fncSplitSemiColonSeparatedString(pProductionFactors);
    vUnits := fncSplitSemiColonSeparatedString(pUnits);
    vAmounts := fncSplitSemiColonSeparatedString(pAmounts);

    LOOP
        FETCH vProductionFactors INTO vProductionFactorId;
        FETCH vUnits INTO vUnitId;
        FETCH vAmounts INTO vAmount;

        EXIT WHEN vProductionFactors%NOTFOUND OR vUnits%NOTFOUND OR vAmounts%NOTFOUND;

        INSERT INTO ProductionFactorInRecipe (recipeId, productionFactorId, unitId, amount)
        VALUES (pRecipeId, vProductionFactorId, vUnitId, vAmount);
    END LOOP;

    CLOSE vProductionFactors;
    CLOSE vUnits;
    CLOSE vAmounts;
END;
/

/
DECLARE
    vRecipeId            Recipe.recipeId%TYPE;
    vProductionFactors   VARCHAR2(255);
    vUnits               VARCHAR2(255);
    vAmounts             VARCHAR2(255);

BEGIN
    vRecipeId := 1;
    vProductionFactors := '12;1;3';
    vUnits := '7;7;8';
    vAmounts := '2;5,5;3';

    prcRecipeRegister(vRecipeId, vProductionFactors, vUnits, vAmounts);

    DBMS_OUTPUT.PUT_LINE('Inserted.');

    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('Removed.');

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error occurred: ' || SQLERRM);
END;
/