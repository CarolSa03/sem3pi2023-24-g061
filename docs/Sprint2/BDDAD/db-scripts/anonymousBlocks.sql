-- Anonymous Blocks to test functions and procedures


-- ProductionFactorAppliance

-- fncProductionFactorAppliances
declare
	vListProductionFactorAppliances SYS_REFCURSOR;
    vOperationDate operation.operationDate%TYPE;
    vProductionFactorDesignation productionFactor.designation%TYPE;
	vCropDesignation plant.designation%TYPE;

begin
	prcProductionFactorAppliances(TO_DATE('01/01/2010','DD/MM/YYYY'), TO_DATE('01/01/2023','DD/MM/YYYY'), vListProductionFactorAppliances);
	
	loop
		fetch vListProductionFactorAppliances INTO vOperationDate, vProductionFactorDesignation, vCropDesignation;

		exit when vListProductionFactorAppliances%NOTFOUND;	

		DBMS_OUTPUT.PUT_LINE('Date: '  ||vOperationDate||  ', Designation: '  			||vProductionFactorDesignation||  ', Crop Designation: ' || vCropDesignation);
	end loop;
end;
/

