create or replace NONEDITIONABLE FUNCTION fncProductionFactorAppliances(pPlotId crop.plotId%type, pInitialDate in DATE, pFinalDate in DATE)
RETURN SYS_REFCURSOR
AS
  l_refcur_productionFactorAppliances SYS_REFCURSOR;
BEGIN
  OPEN l_refcur_productionFactorAppliances FOR
    SELECT
      operation.operationDate,
      productionFactor.designation,
      COALESCE(plant.designation, 'Sem cultura')
    FROM
      Operation
      INNER JOIN ProductionFactorAppliance ON productionFactorAppliance.operationId = operation.operationId
      INNER JOIN ProductionFactor ON productionFactor.productionFactorId = productionFactorAppliance.productionFactorId
      LEFT JOIN CropFertilization ON cropFertilization.operationId = operation.operationId
      LEFT JOIN Crop ON crop.cropId = cropFertilization.cropId
      LEFT JOIN Plant ON plant.plantId = crop.plantId
    WHERE
      operation.operationDate BETWEEN pInitialDate AND pFinalDate
      AND operation.plotId = pPlotId
    ORDER BY
      operation.operationDate ASC;
  RETURN l_refcur_productionFactorAppliances;
END;

DECLARE
    vResultSet SYS_REFCURSOR;
    vOperationDate operation.operationDate%TYPE;
    vProductionFactorDesignation productionFactor.designation%TYPE;
    vPlantDesignation plant.designation%TYPE;
BEGIN
    vResultSet := fncProductionFactorAppliances(105, TO_DATE('01/01/2019','DD/MM/YYYY'), TO_DATE('06/07/2023', 'DD/MM/YYYY'));
    
    LOOP
        FETCH vResultSet INTO vOperationDate, vProductionFactorDesignation, vPlantDesignation;
        EXIT WHEN vResultSet%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE(vOperationDate || ', ' || vProductionFactorDesignation || ', ' || vPlantDesignation);
    END LOOP;
    
END;
/