-- Get all Production Factors in a Plot
create or replace function fncProductionFactorInPlotList(pPlotId plot.plotId%type, pInitialDate in DATE, pFinalDate in DATE)
return SYS_REFCURSOR
as
l_refcur_productionFactorInPlot SYS_REFCURSOR;
begin
	open l_refcur_productionFactorInPlot for
	select
		operation.operationDate,
		productionFactor.designation,
            	substance.designation,
        	substanceDataSheet.percentage
	from
		Plot
	inner join
		Operation on operation.plotId = plot.plotId
	inner join
		ProductionFactorAppliance on productionFactorAppliance.operationId = operation.operationId
        inner join
            ProductionFactor on productionFactor.productionFactorId = productionFactorAppliance.productionFactorId
	inner join
            SubstanceDataSheet on substanceDataSheet.productionFactorId = productionFactor.productionFactorId
        inner join
		Substance on substance.substanceId = substanceDataSheet.substanceId
        where
		operation.operationDate BETWEEN pInitialDate AND pFinalDate
		AND plot.plotId = pPlotId;
        
    return l_refcur_productionFactorInPlot;
end;
/

--  Test function
declare
	vListProductionFactor SYS_REFCURSOR;
    	vOperationDate operation.operationDate%TYPE;
    	vProductionFactorDesignation productionFactor.designation%TYPE;
	vSubstancePercentage substanceDataSheet.percentage%TYPE;	
	vSubstanceDesignation substance.designation%TYPE;	

begin
	vListProductionFactor := fncProductionFactorInPlotList(105, TO_DATE('01/01/2010','DD/MM/YYYY'), TO_DATE('01/01/2023','DD/MM/YYYY'));
	
	loop
		fetch vListProductionFactor INTO vOperationDate, vProductionFactorDesignation, vSubstanceDesignation, vSubstancePercentage;

		exit when vListProductionFactor%NOTFOUND;	

		DBMS_OUTPUT.PUT_LINE('Date: '  ||vOperationDate||  ', Designation: '  			||vProductionFactorDesignation||  ', Substance: ' || vSubstanceDesignation 		|| ', Percentage: ' || vSubstancePercentage);
	end loop;
end;
/