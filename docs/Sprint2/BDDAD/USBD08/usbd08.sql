select max(PF.application) as Maximum_Application
    from ProductionFactor PF
    inner join ProductionFactorOperation PFO on PF.designation = PFO.designation
    inner join Operation OP on PFO.operationId = OP.operationId
    where OP.operationDate between date '2010-01-01' and date '2020-12-31';