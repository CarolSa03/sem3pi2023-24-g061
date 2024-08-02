DROP TABLE Plot CASCADE CONSTRAINTS;

DROP TABLE Operation CASCADE CONSTRAINTS;

DROP TABLE ProductionFactor CASCADE CONSTRAINTS;

DROP TABLE Application CASCADE CONSTRAINTS;

DROP TABLE ProductionFactorType CASCADE CONSTRAINTS;

DROP TABLE Manufacturer CASCADE CONSTRAINTS;

DROP TABLE SubstanceDataSheet CASCADE CONSTRAINTS;

DROP TABLE Substance CASCADE CONSTRAINTS;

DROP TABLE Plant CASCADE CONSTRAINTS;

DROP TABLE Building CASCADE CONSTRAINTS;

DROP TABLE CropType CASCADE CONSTRAINTS;

DROP TABLE Crop CASCADE CONSTRAINTS;

DROP TABLE Format CASCADE CONSTRAINTS;

DROP TABLE Period CASCADE CONSTRAINTS;

DROP TABLE PeriodType CASCADE CONSTRAINTS;

DROP TABLE CropFinalDate CASCADE CONSTRAINTS;

DROP TABLE Product CASCADE CONSTRAINTS;

DROP TABLE Sector CASCADE CONSTRAINTS;

DROP TABLE Warehouse CASCADE CONSTRAINTS;

DROP TABLE Garage CASCADE CONSTRAINTS;

DROP TABLE Stable CASCADE CONSTRAINTS;

DROP TABLE IrrigationSystem CASCADE CONSTRAINTS;

DROP TABLE Mill CASCADE CONSTRAINTS;

DROP TABLE SectorIrrigation CASCADE CONSTRAINTS;

DROP TABLE PlotInSector CASCADE CONSTRAINTS;

DROP TABLE Plantation CASCADE CONSTRAINTS;

DROP TABLE Harvest CASCADE CONSTRAINTS;

DROP TABLE ProductionFactorAppliance CASCADE CONSTRAINTS;

DROP TABLE OperationType CASCADE CONSTRAINTS;

DROP TABLE Fertilization CASCADE CONSTRAINTS;

DROP TABLE FertilizationMode CASCADE CONSTRAINTS;

DROP TABLE SectorFinalDate CASCADE CONSTRAINTS;

DROP TABLE PlantVariety CASCADE CONSTRAINTS;

CREATE TABLE Plot ( 
  plotId      number(10) NOT NULL,  
  designation varchar2(255) NOT NULL UNIQUE,  
  area        number(10) NOT NULL,  
  PRIMARY KEY (plotId));

CREATE TABLE Operation ( 
  operationId     number(10) NOT NULL,  
  cropId          number(10) NOT NULL,  
  operationTypeId number(10) NOT NULL,  
  operationDate   date NOT NULL,  
  amount          number(10) NOT NULL,  
  unit            varchar2(255) NOT NULL,  
  PRIMARY KEY (operationId));

CREATE TABLE ProductionFactor ( 
  productionFactorId     number(10) NOT NULL,  
  applicationId          number(10) NOT NULL,  
  manufacturerId         number(10) NOT NULL,  
  formatId               number(10) NOT NULL,  
  productionFactorTypeId number(10) NOT NULL,  
  designation            varchar2(255) NOT NULL UNIQUE,  
  PRIMARY KEY (productionFactorId));

CREATE TABLE Application ( 
  applicationId number(10) NOT NULL,  
  designation   varchar2(255) NOT NULL UNIQUE,  
  PRIMARY KEY (applicationId));

CREATE TABLE ProductionFactorType ( 
  productionFactorTypeId number(10) NOT NULL,  
  designation            varchar2(255) NOT NULL UNIQUE,  
  PRIMARY KEY (productionFactorTypeId));

CREATE TABLE Manufacturer ( 
  manufacturerId number(10) NOT NULL,  
  designation    varchar2(255) NOT NULL UNIQUE,  
  PRIMARY KEY (manufacturerId));

CREATE TABLE SubstanceDataSheet ( 
  productionFactorId number(10) NOT NULL,  
  substanceId        number(10) NOT NULL,  
  percentage         number(10) NOT NULL,  
  PRIMARY KEY (productionFactorId,  
  substanceId));

CREATE TABLE Substance ( 
  substanceId number(10) NOT NULL,  
  designation varchar2(255) NOT NULL UNIQUE,  
  PRIMARY KEY (substanceId));

CREATE TABLE Plant ( 
  plantId        number(10) NOT NULL,  
  plantVarietyId number(10) NOT NULL,  
  designation    varchar2(255) NOT NULL UNIQUE,  
  PRIMARY KEY (plantId));

CREATE TABLE Building ( 
  buildingId  number(10) NOT NULL,  
  designation varchar2(255) NOT NULL UNIQUE,  
  PRIMARY KEY (buildingId));

CREATE TABLE CropType ( 
  cropTypeId  number(10) NOT NULL,  
  designation varchar2(255) NOT NULL UNIQUE,  
  PRIMARY KEY (cropTypeId));

CREATE TABLE Crop ( 
  cropId      number(10) NOT NULL,  
  cropTypeId  number(10) NOT NULL,  
  plotId      number(10) NOT NULL,  
  plantId     number(10) NOT NULL,  
  initialDate date NOT NULL,  
  amount      number(10) NOT NULL,  
  unit        varchar2(255) NOT NULL,  
  PRIMARY KEY (cropId));

CREATE TABLE Format ( 
  formatId    number(10) NOT NULL,  
  designation varchar2(255) NOT NULL UNIQUE,  
  PRIMARY KEY (formatId));

CREATE TABLE Period ( 
  periodTypeId   number(10) NOT NULL,  
  plantVarietyId number(10) NOT NULL,  
  value          varchar2(255) NOT NULL,  
  PRIMARY KEY (periodTypeId,  
  plantVarietyId));

CREATE TABLE PeriodType ( 
  periodTypeId number(10) NOT NULL,  
  designation  varchar2(255) NOT NULL UNIQUE,  
  PRIMARY KEY (periodTypeId));

CREATE TABLE CropFinalDate ( 
  cropId number(10) NOT NULL,  
  value date NOT NULL,  
  PRIMARY KEY (cropId));

CREATE TABLE Product ( 
  productId   number(10) NOT NULL,  
  plantId     number(10) NOT NULL,  
  designation varchar2(255) NOT NULL UNIQUE,  
  PRIMARY KEY (productId));

CREATE TABLE Sector ( 
  sectorID    number(10) NOT NULL,  
  maximumFlow number(10) NOT NULL, 
  initialDate date NOT NULL,  
  PRIMARY KEY (sectorID));

CREATE TABLE Warehouse ( 
  buildingId number(10) NOT NULL,  
  area       number(10) NOT NULL,  
  PRIMARY KEY (buildingId));

CREATE TABLE Garage ( 
  buildingId number(10) NOT NULL,  
  area       number(10) NOT NULL,  
  doorWidth  number(10) NOT NULL,  
  doorHeight number(10) NOT NULL,  
  PRIMARY KEY (buildingId));

CREATE TABLE Stable ( 
  buildingId      number(10) NOT NULL,  
  area            number(10) NOT NULL,  
  numberOfAnimals number(10) NOT NULL,  
  PRIMARY KEY (buildingId));

CREATE TABLE IrrigationSystem ( 
  buildingId number(10) NOT NULL,  
  PRIMARY KEY (buildingId));

CREATE TABLE Mill ( 
  buildingId number(10) NOT NULL,  
  PRIMARY KEY (buildingId));

CREATE TABLE SectorIrrigation ( 
  operationId number(10) NOT NULL,  
  sectorID    number(10) NOT NULL,  
  PRIMARY KEY (operationId));

CREATE TABLE PlotInSector ( 
  sectorID number(10) NOT NULL,  
  plotId   number(10) NOT NULL,  
  PRIMARY KEY (sectorID,  
  plotId));

CREATE TABLE Plantation ( 
  operationId number(10) NOT NULL,  
  plantId     number(10) NOT NULL,  
  PRIMARY KEY (operationId));

CREATE TABLE Harvest ( 
  operationId number(10) NOT NULL,  
  productId   number(10) NOT NULL,  
  PRIMARY KEY (operationId));

CREATE TABLE ProductionFactorAppliance ( 
  operationId        number(10) NOT NULL,  
  productionFactorId number(10) NOT NULL,  
  PRIMARY KEY (operationId));

CREATE TABLE OperationType ( 
  operationTypeId number(10) NOT NULL,  
  designation     varchar2(255) NOT NULL UNIQUE,  
  PRIMARY KEY (operationTypeId));

CREATE TABLE Fertilization ( 
  operationId         number(10) NOT NULL,  
  fertilizationModeId number(10) NOT NULL,  
  PRIMARY KEY (operationId));

CREATE TABLE FertilizationMode ( 
  fertilizationModeId number(10) NOT NULL,  
  designation         varchar2(255) NOT NULL UNIQUE,  
  PRIMARY KEY (fertilizationModeId));

CREATE TABLE SectorFinalDate ( 
  sectorID number(10) NOT NULL,  
  value date NOT NULL,  
  PRIMARY KEY (sectorID));

CREATE TABLE PlantVariety ( 
  plantVarietyId number(10) NOT NULL,  
  designation    varchar2(255) NOT NULL UNIQUE,  
  species        varchar2(255) NOT NULL,  
  commonName     varchar2(255) NOT NULL,  
  PRIMARY KEY (plantVarietyId));

ALTER TABLE ProductionFactor ADD CONSTRAINT FKProduction735913 FOREIGN KEY (applicationId) REFERENCES Application (applicationId);

ALTER TABLE ProductionFactor ADD CONSTRAINT FKProduction276086 FOREIGN KEY (productionFactorTypeId) REFERENCES ProductionFactorType (productionFactorTypeId);

ALTER TABLE SubstanceDataSheet ADD CONSTRAINT FKSubstanceD106116 FOREIGN KEY (substanceId) REFERENCES Substance (substanceId);

ALTER TABLE ProductionFactor ADD CONSTRAINT FKProduction166048 FOREIGN KEY (manufacturerId) REFERENCES Manufacturer (manufacturerId);

ALTER TABLE ProductionFactor ADD CONSTRAINT FKProduction63694 FOREIGN KEY (formatId) REFERENCES Format (formatId);

ALTER TABLE Crop ADD CONSTRAINT FKCrop949759 FOREIGN KEY (cropTypeId) REFERENCES CropType (cropTypeId);

ALTER TABLE Period ADD CONSTRAINT FKPeriod45901 FOREIGN KEY (periodTypeId) REFERENCES PeriodType (periodTypeId);

ALTER TABLE SubstanceDataSheet ADD CONSTRAINT FKSubstanceD797342 FOREIGN KEY (productionFactorId) REFERENCES ProductionFactor (productionFactorId);

ALTER TABLE Crop ADD CONSTRAINT FKCrop460229 FOREIGN KEY (plantId) REFERENCES Plant (plantId);

ALTER TABLE CropFinalDate ADD CONSTRAINT FKCropFinalD471078 FOREIGN KEY (cropId) REFERENCES Crop (cropId);

ALTER TABLE Product ADD CONSTRAINT FKProduct611234 FOREIGN KEY (plantId) REFERENCES Plant (plantId);

ALTER TABLE Warehouse ADD CONSTRAINT FKWarehouse632881 FOREIGN KEY (buildingId) REFERENCES Building (buildingId);

ALTER TABLE Garage ADD CONSTRAINT FKGarage972841 FOREIGN KEY (buildingId) REFERENCES Building (buildingId);

ALTER TABLE Stable ADD CONSTRAINT FKStable618898 FOREIGN KEY (buildingId) REFERENCES Building (buildingId);

ALTER TABLE IrrigationSystem ADD CONSTRAINT FKIrrigation256341 FOREIGN KEY (buildingId) REFERENCES Building (buildingId);

ALTER TABLE Mill ADD CONSTRAINT FKMill650950 FOREIGN KEY (buildingId) REFERENCES Building (buildingId);

ALTER TABLE Crop ADD CONSTRAINT FKCrop85189 FOREIGN KEY (plotId) REFERENCES Plot (plotId);

ALTER TABLE SectorIrrigation ADD CONSTRAINT FKSectorIrri590653 FOREIGN KEY (operationId) REFERENCES Operation (operationId);

ALTER TABLE SectorIrrigation ADD CONSTRAINT FKSectorIrri124538 FOREIGN KEY (sectorID) REFERENCES Sector (sectorID);

ALTER TABLE PlotInSector ADD CONSTRAINT FKPlotInSect661717 FOREIGN KEY (sectorID) REFERENCES Sector (sectorID);

ALTER TABLE PlotInSector ADD CONSTRAINT FKPlotInSect359347 FOREIGN KEY (plotId) REFERENCES Plot (plotId);

ALTER TABLE Plantation ADD CONSTRAINT FKPlantatio126429 FOREIGN KEY (operationId) REFERENCES Operation (operationId);

ALTER TABLE Plantation ADD CONSTRAINT FKPlantatio807342 FOREIGN KEY (plantId) REFERENCES Plant (plantId);

ALTER TABLE Harvest ADD CONSTRAINT FKHarvest794652 FOREIGN KEY (operationId) REFERENCES Operation (operationId);

ALTER TABLE Harvest ADD CONSTRAINT FKHarvest200185 FOREIGN KEY (productId) REFERENCES Product (productId);

ALTER TABLE ProductionFactorAppliance ADD CONSTRAINT FKProduction330039 FOREIGN KEY (operationId) REFERENCES Operation (operationId);

ALTER TABLE ProductionFactorAppliance ADD CONSTRAINT FKProduction923450 FOREIGN KEY (productionFactorId) REFERENCES ProductionFactor (productionFactorId);

ALTER TABLE Operation ADD CONSTRAINT FKOperation152256 FOREIGN KEY (operationTypeId) REFERENCES OperationType (operationTypeId);

ALTER TABLE Fertilization ADD CONSTRAINT FKFertilizat717114 FOREIGN KEY (operationId) REFERENCES ProductionFactorAppliance (operationId);

ALTER TABLE Fertilization ADD CONSTRAINT FKFertilizat316069 FOREIGN KEY (fertilizationModeId) REFERENCES FertilizationMode (fertilizationModeId);

ALTER TABLE SectorFinalDate ADD CONSTRAINT FKSectorFina701927 FOREIGN KEY (sectorID) REFERENCES Sector (sectorID);

ALTER TABLE Plant ADD CONSTRAINT FKPlant379101 FOREIGN KEY (plantVarietyId) REFERENCES PlantVariety (plantVarietyId);

ALTER TABLE Period ADD CONSTRAINT FKPeriod435912 FOREIGN KEY (plantVarietyId) REFERENCES PlantVariety (plantVarietyId);

ALTER TABLE Operation ADD CONSTRAINT FKOperation814673 FOREIGN KEY (cropId) REFERENCES Crop (cropId);

