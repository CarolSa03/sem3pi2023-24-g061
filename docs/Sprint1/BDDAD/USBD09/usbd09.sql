SELECT PF.PRODUCTIONFACTORTYPE, COUNT(PF.APPLICATION) AS NUM_OF_APPLICATIONS
FROM PRODUCTIONFACTOR PF
JOIN PRODUCTIONFACTOROPERATION PFO ON PF.DESIGNATION = PFO.DESIGNATION
JOIN OPERATION OP ON OP.OPERATIONID = PFO.OPERATIONID
WHERE OP.OPERATIONDATE BETWEEN DATE '2010-01-01' AND DATE '2020-12-31'
GROUP BY PF.PRODUCTIONFACTORTYPE
ORDER BY PF.PRODUCTIONFACTORTYPE ASC;