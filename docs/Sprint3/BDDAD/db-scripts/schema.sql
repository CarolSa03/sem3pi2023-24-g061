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
DROP TABLE Crop CASCADE CONSTRAINTS;
DROP TABLE Format CASCADE CONSTRAINTS;
DROP TABLE Period CASCADE CONSTRAINTS;
DROP TABLE PeriodType CASCADE CONSTRAINTS;
DROP TABLE Product CASCADE CONSTRAINTS;
DROP TABLE Sector CASCADE CONSTRAINTS;
DROP TABLE Warehouse CASCADE CONSTRAINTS;
DROP TABLE Garage CASCADE CONSTRAINTS;
DROP TABLE IrrigationSystem CASCADE CONSTRAINTS;
DROP TABLE Mill CASCADE CONSTRAINTS;
DROP TABLE SectorIrrigation CASCADE CONSTRAINTS;
DROP TABLE Plantation CASCADE CONSTRAINTS;
DROP TABLE Harvest CASCADE CONSTRAINTS;
DROP TABLE Fertilization CASCADE CONSTRAINTS;
DROP TABLE FertilizationMode CASCADE CONSTRAINTS;
DROP TABLE PlantVariety CASCADE CONSTRAINTS;
DROP TABLE CropIrrigation CASCADE CONSTRAINTS;
DROP TABLE CropFertilization CASCADE CONSTRAINTS;
DROP TABLE ProductionFactorPH CASCADE CONSTRAINTS;
DROP TABLE Weeding CASCADE CONSTRAINTS;
DROP TABLE SoilIncorporation CASCADE CONSTRAINTS;
DROP TABLE Pruning CASCADE CONSTRAINTS;
DROP TABLE OperationLog CASCADE CONSTRAINTS;
DROP TABLE LogType CASCADE CONSTRAINTS;
DROP TABLE CropFinalDate CASCADE CONSTRAINTS;
DROP TABLE SectorFinalDate CASCADE CONSTRAINTS;
DROP TABLE PlantVarietyType CASCADE CONSTRAINTS;
DROP TABLE OperationAmount CASCADE CONSTRAINTS;
DROP TABLE Unit CASCADE CONSTRAINTS;
DROP TABLE UnitType CASCADE CONSTRAINTS;
DROP TABLE CropAmount CASCADE CONSTRAINTS;
DROP TABLE Sowing CASCADE CONSTRAINTS;
DROP TABLE Recipe CASCADE CONSTRAINTS;
DROP TABLE ProductionFactorInRecipe CASCADE CONSTRAINTS;
DROP TABLE PlantationMeasures CASCADE CONSTRAINTS;
DROP TABLE ProductionFactorAppliance CASCADE CONSTRAINTS;
DROP TABLE Fieldbook CASCADE CONSTRAINTS;
DROP TABLE SectorIrrigationInFieldbook CASCADE CONSTRAINTS;
DROP TABLE SectorIrrigationWithRecipe CASCADE CONSTRAINTS;
DROP TABLE ProductionFactorUsedInFertigation CASCADE CONSTRAINTS;
DROP TABLE CropInSector CASCADE CONSTRAINTS;
CREATE TABLE Plot (
  plotId      number(10) NOT NULL, 
  designation varchar2(255) NOT NULL UNIQUE, 
  areaInHa    float(10) NOT NULL, 
  PRIMARY KEY (plotId));
CREATE TABLE Operation (
  operationId   number(10) NOT NULL, 
  plotId        number(10) NOT NULL, 
  operationDate date NOT NULL, 
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
  percentage         float(10) NOT NULL, 
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
CREATE TABLE Crop (
  cropId      number(10) NOT NULL, 
  plotId      number(10) NOT NULL, 
  plantId     number(10) NOT NULL, 
  initialDate date NOT NULL, 
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
CREATE TABLE Product (
  productId   number(10) NOT NULL, 
  plantId     number(10) NOT NULL, 
  designation varchar2(255) NOT NULL UNIQUE, 
  PRIMARY KEY (productId));
CREATE TABLE Sector (
  sectorID    number(10) NOT NULL, 
  maximumFlow float(10) NOT NULL, 
  initialDate date NOT NULL, 
  PRIMARY KEY (sectorID));
CREATE TABLE Warehouse (
  buildingId number(10) NOT NULL, 
  areaInM2   float(10) NOT NULL, 
  PRIMARY KEY (buildingId));
CREATE TABLE Garage (
  buildingId number(10) NOT NULL, 
  areaInM2   float(10) NOT NULL, 
  PRIMARY KEY (buildingId));
CREATE TABLE IrrigationSystem (
  buildingId number(10) NOT NULL, 
  areaInM3   float(10) NOT NULL, 
  PRIMARY KEY (buildingId));
CREATE TABLE Mill (
  buildingId number(10) NOT NULL, 
  PRIMARY KEY (buildingId));
CREATE TABLE SectorIrrigation (
  operationId      number(10) NOT NULL, 
  sectorID         number(10) NOT NULL, 
  initialTimestamp timestamp(0) NOT NULL, 
  PRIMARY KEY (operationId));
CREATE TABLE CropInSector (
  cropId   number(10) NOT NULL, 
  sectorID number(10) NOT NULL, 
  PRIMARY KEY (cropId, 
  sectorID));
CREATE TABLE Plantation (
  operationId number(10) NOT NULL, 
  cropId      number(10) NOT NULL, 
  PRIMARY KEY (operationId));
CREATE TABLE Harvest (
  operationId number(10) NOT NULL, 
  cropId      number(10) NOT NULL, 
  productId   number(10) NOT NULL, 
  PRIMARY KEY (operationId));
CREATE TABLE Fertilization (
  operationId         number(10) NOT NULL, 
  productionFactorId  number(10) NOT NULL, 
  fertilizationModeId number(10) NOT NULL, 
  PRIMARY KEY (operationId, 
  productionFactorId));
CREATE TABLE FertilizationMode (
  fertilizationModeId number(10) NOT NULL, 
  designation         varchar2(255) NOT NULL UNIQUE, 
  PRIMARY KEY (fertilizationModeId));
CREATE TABLE PlantVariety (
  plantVarietyId     number(10) NOT NULL, 
  plantVarietyTypeId number(10) NOT NULL, 
  designation        varchar2(255) NOT NULL UNIQUE, 
  species            varchar2(255) NOT NULL, 
  commonName         varchar2(255) NOT NULL, 
  PRIMARY KEY (plantVarietyId));
CREATE TABLE CropIrrigation (
  operationId number(10) NOT NULL, 
  cropId      number(10) NOT NULL, 
  PRIMARY KEY (operationId,
  cropId));
CREATE TABLE CropFertilization (
  operationId        number(10) NOT NULL, 
  productionFactorId number(10) NOT NULL, 
  cropId             number(10) NOT NULL, 
  PRIMARY KEY (operationId, 
  productionFactorId,
  cropId));
CREATE TABLE ProductionFactorPH (
  productionFactorId number(10) NOT NULL, 
  value              float(10) NOT NULL, 
  PRIMARY KEY (productionFactorId));
CREATE TABLE Weeding (
  operationId number(10) NOT NULL, 
  cropId      number(10) NOT NULL, 
  PRIMARY KEY (operationId));
CREATE TABLE SoilIncorporation (
  operationId number(10) NOT NULL, 
  cropId      number(10) NOT NULL, 
  PRIMARY KEY (operationId, 
  cropId));
CREATE TABLE Pruning (
  operationId number(10) NOT NULL, 
  cropId      number(10) NOT NULL, 
  PRIMARY KEY (operationId));
CREATE TABLE OperationLog (
  operationId         number(10) NOT NULL, 
  logTypeId           number(10) NOT NULL, 
  registeredTimestamp timestamp(0) NOT NULL, 
  PRIMARY KEY (operationId, 
  logTypeId));
CREATE TABLE LogType (
  logTypeId   number(10) NOT NULL, 
  designation varchar2(255) NOT NULL UNIQUE, 
  PRIMARY KEY (logTypeId));
CREATE TABLE CropFinalDate (
  cropId number(10) NOT NULL, 
  value  date NOT NULL, 
  PRIMARY KEY (cropId));
CREATE TABLE SectorFinalDate (
  sectorID number(10) NOT NULL, 
  value    date NOT NULL, 
  PRIMARY KEY (sectorID));
CREATE TABLE PlantVarietyType (
  plantVarietyTypeId number(10) NOT NULL, 
  designation        varchar2(255) NOT NULL UNIQUE, 
  PRIMARY KEY (plantVarietyTypeId));
CREATE TABLE OperationAmount (
  operationId number(10) NOT NULL, 
  unitId      number(10) NOT NULL, 
  value       float(10) NOT NULL, 
  PRIMARY KEY (operationId, 
  unitId));
CREATE TABLE Unit (
  unitId      number(10) NOT NULL, 
  unitTypeId  number(10) NOT NULL, 
  designation varchar2(255) NOT NULL UNIQUE, 
  PRIMARY KEY (unitId));
CREATE TABLE UnitType (
  unitTypeId  number(10) NOT NULL, 
  designation varchar2(255) NOT NULL UNIQUE, 
  PRIMARY KEY (unitTypeId));
CREATE TABLE CropAmount (
  cropId number(10) NOT NULL, 
  unitId number(10) NOT NULL, 
  value  float(10) NOT NULL, 
  PRIMARY KEY (cropId, 
  unitId));
CREATE TABLE Sowing (
  operationId number(10) NOT NULL, 
  cropId      number(10) NOT NULL, 
  PRIMARY KEY (operationId));
CREATE TABLE Recipe (
  recipeId number(10) NOT NULL, 
  PRIMARY KEY (recipeId));
CREATE TABLE ProductionFactorInRecipe (
  productionFactorId number(10) NOT NULL, 
  recipeId           number(10) NOT NULL, 
  unitId             number(10) NOT NULL, 
  amount             float(10) NOT NULL, 
  PRIMARY KEY (productionFactorId, 
  recipeId));
CREATE TABLE PlantationMeasures (
  operationId          number(10) NOT NULL, 
  compass              number(10) NOT NULL, 
  distanceBetweenLines number(10) NOT NULL, 
  PRIMARY KEY (operationId));
CREATE TABLE ProductionFactorAppliance (
  operationId        number(10) NOT NULL, 
  productionFactorId number(10) NOT NULL, 
  PRIMARY KEY (operationId, 
  productionFactorId));
CREATE TABLE Fieldbook (
  fieldbookId number(10) NOT NULL, 
  PRIMARY KEY (fieldbookId));
CREATE TABLE SectorIrrigationInFieldbook (
  operationId number(10) NOT NULL, 
  fieldbookId number(10) NOT NULL, 
  PRIMARY KEY (operationId, 
  fieldbookId));
CREATE TABLE SectorIrrigationWithRecipe (
  operationId number(10) NOT NULL, 
  recipeId              number(10) NOT NULL, 
  PRIMARY KEY (operationId, 
  recipeId));
CREATE TABLE ProductionFactorUsedInFertigation (
  operationId        number(10) NOT NULL, 
  productionFactorId number(10) NOT NULL, 
  amountInLiters     float(10) NOT NULL, 
  PRIMARY KEY (operationId, 
  productionFactorId));
ALTER TABLE ProductionFactor ADD CONSTRAINT FKProduction735913 FOREIGN KEY (applicationId) REFERENCES Application (applicationId);
ALTER TABLE ProductionFactor ADD CONSTRAINT FKProduction276086 FOREIGN KEY (productionFactorTypeId) REFERENCES ProductionFactorType (productionFactorTypeId);
ALTER TABLE SubstanceDataSheet ADD CONSTRAINT FKSubstanceD106116 FOREIGN KEY (substanceId) REFERENCES Substance (substanceId);
ALTER TABLE ProductionFactor ADD CONSTRAINT FKProduction166048 FOREIGN KEY (manufacturerId) REFERENCES Manufacturer (manufacturerId);
ALTER TABLE ProductionFactor ADD CONSTRAINT FKProduction63694 FOREIGN KEY (formatId) REFERENCES Format (formatId);
ALTER TABLE Period ADD CONSTRAINT FKPeriod45901 FOREIGN KEY (periodTypeId) REFERENCES PeriodType (periodTypeId);
ALTER TABLE SubstanceDataSheet ADD CONSTRAINT FKSubstanceD797342 FOREIGN KEY (productionFactorId) REFERENCES ProductionFactor (productionFactorId);
ALTER TABLE Crop ADD CONSTRAINT FKCrop460229 FOREIGN KEY (plantId) REFERENCES Plant (plantId);
ALTER TABLE Product ADD CONSTRAINT FKProduct611234 FOREIGN KEY (plantId) REFERENCES Plant (plantId);
ALTER TABLE Warehouse ADD CONSTRAINT FKWarehouse632881 FOREIGN KEY (buildingId) REFERENCES Building (buildingId);
ALTER TABLE Garage ADD CONSTRAINT FKGarage972841 FOREIGN KEY (buildingId) REFERENCES Building (buildingId);
ALTER TABLE IrrigationSystem ADD CONSTRAINT FKIrrigation256341 FOREIGN KEY (buildingId) REFERENCES Building (buildingId);
ALTER TABLE Mill ADD CONSTRAINT FKMill650950 FOREIGN KEY (buildingId) REFERENCES Building (buildingId);
ALTER TABLE Crop ADD CONSTRAINT FKCrop85189 FOREIGN KEY (plotId) REFERENCES Plot (plotId);
ALTER TABLE SectorIrrigation ADD CONSTRAINT FKSectorIrri124538 FOREIGN KEY (sectorID) REFERENCES Sector (sectorID);
ALTER TABLE Plantation ADD CONSTRAINT FKPlantation463263 FOREIGN KEY (operationId) REFERENCES Operation (operationId);
ALTER TABLE Harvest ADD CONSTRAINT FKHarvest794652 FOREIGN KEY (operationId) REFERENCES Operation (operationId);
ALTER TABLE Harvest ADD CONSTRAINT FKHarvest200185 FOREIGN KEY (productId) REFERENCES Product (productId);
ALTER TABLE Fertilization ADD CONSTRAINT FKFertilizat298206 FOREIGN KEY (fertilizationModeId) REFERENCES FertilizationMode (fertilizationModeId);
ALTER TABLE Plant ADD CONSTRAINT FKPlant379101 FOREIGN KEY (plantVarietyId) REFERENCES PlantVariety (plantVarietyId);
ALTER TABLE Period ADD CONSTRAINT FKPeriod435912 FOREIGN KEY (plantVarietyId) REFERENCES PlantVariety (plantVarietyId);
ALTER TABLE Operation ADD CONSTRAINT FKOperation480290 FOREIGN KEY (plotId) REFERENCES Plot (plotId);
ALTER TABLE CropIrrigation ADD CONSTRAINT FKCropIrriga59549 FOREIGN KEY (cropId) REFERENCES Crop (cropId);
ALTER TABLE CropFertilization ADD CONSTRAINT FKCropFertil386524 FOREIGN KEY (cropId) REFERENCES Crop (cropId);
ALTER TABLE ProductionFactorPH ADD CONSTRAINT FKProduction805560 FOREIGN KEY (productionFactorId) REFERENCES ProductionFactor (productionFactorId);
ALTER TABLE Harvest ADD CONSTRAINT FKHarvest176837 FOREIGN KEY (cropId) REFERENCES Crop (cropId);
ALTER TABLE Weeding ADD CONSTRAINT FKWeeding544812 FOREIGN KEY (operationId) REFERENCES Operation (operationId);
ALTER TABLE Weeding ADD CONSTRAINT FKWeeding455288 FOREIGN KEY (cropId) REFERENCES Crop (cropId);
ALTER TABLE Pruning ADD CONSTRAINT FKPruning119986 FOREIGN KEY (operationId) REFERENCES Operation (operationId);
ALTER TABLE Pruning ADD CONSTRAINT FKPruning851503 FOREIGN KEY (cropId) REFERENCES Crop (cropId);
ALTER TABLE SoilIncorporation ADD CONSTRAINT FKSoilIncorp677024 FOREIGN KEY (operationId) REFERENCES Operation (operationId);
ALTER TABLE SoilIncorporation ADD CONSTRAINT FKSoilIncorp677125 FOREIGN KEY (cropId) REFERENCES Crop (cropId);
ALTER TABLE OperationLog ADD CONSTRAINT FKOperationL703190 FOREIGN KEY (operationId) REFERENCES Operation (operationId);
ALTER TABLE OperationLog ADD CONSTRAINT FKOperationL527057 FOREIGN KEY (logTypeId) REFERENCES LogType (logTypeId);
ALTER TABLE CropFinalDate ADD CONSTRAINT FKCropFinalD471078 FOREIGN KEY (cropId) REFERENCES Crop (cropId);
ALTER TABLE SectorFinalDate ADD CONSTRAINT FKSectorFina701927 FOREIGN KEY (sectorID) REFERENCES Sector (sectorID);
ALTER TABLE SectorIrrigation ADD CONSTRAINT FKSectorIrri590653 FOREIGN KEY (operationId) REFERENCES Operation (operationId);
ALTER TABLE CropIrrigation ADD CONSTRAINT FKCropIrriga911940 FOREIGN KEY (operationId) REFERENCES Operation (operationId);
ALTER TABLE PlantVariety ADD CONSTRAINT FKPlantVarie184027 FOREIGN KEY (plantVarietyTypeId) REFERENCES PlantVarietyType (plantVarietyTypeId);
ALTER TABLE OperationAmount ADD CONSTRAINT FKOperationA272409 FOREIGN KEY (operationId) REFERENCES Operation (operationId);
ALTER TABLE Unit ADD CONSTRAINT FKUnit409322 FOREIGN KEY (unitTypeId) REFERENCES UnitType (unitTypeId);
ALTER TABLE OperationAmount ADD CONSTRAINT FKOperationA254381 FOREIGN KEY (unitId) REFERENCES Unit (unitId);
ALTER TABLE CropAmount ADD CONSTRAINT FKCropAmount270550 FOREIGN KEY (unitId) REFERENCES Unit (unitId);
ALTER TABLE CropAmount ADD CONSTRAINT FKCropAmount174149 FOREIGN KEY (cropId) REFERENCES Crop (cropId);
ALTER TABLE Sowing ADD CONSTRAINT FKSowing493971 FOREIGN KEY (operationId) REFERENCES Operation (operationId);
ALTER TABLE Sowing ADD CONSTRAINT FKSowing477518 FOREIGN KEY (cropId) REFERENCES Crop (cropId);
ALTER TABLE ProductionFactorInRecipe ADD CONSTRAINT FKProduction363702 FOREIGN KEY (productionFactorId) REFERENCES ProductionFactor (productionFactorId);
ALTER TABLE ProductionFactorInRecipe ADD CONSTRAINT FKProduction61228 FOREIGN KEY (recipeId) REFERENCES Recipe (recipeId);
ALTER TABLE ProductionFactorInRecipe ADD CONSTRAINT FKProduction243500 FOREIGN KEY (unitId) REFERENCES Unit (unitId);
ALTER TABLE PlantationMeasures ADD CONSTRAINT FKPlantation740652 FOREIGN KEY (operationId) REFERENCES Plantation (operationId);
ALTER TABLE Plantation ADD CONSTRAINT FKPlantation536837 FOREIGN KEY (cropId) REFERENCES Crop (cropId);
ALTER TABLE ProductionFactorAppliance ADD CONSTRAINT FKProduction330039 FOREIGN KEY (operationId) REFERENCES Operation (operationId);
ALTER TABLE Fertilization ADD CONSTRAINT FKFertilizat140866 FOREIGN KEY (operationId, productionFactorId) REFERENCES ProductionFactorAppliance (operationId, productionFactorId);
ALTER TABLE CropFertilization ADD CONSTRAINT FKCropFertil168841 FOREIGN KEY (operationId, productionFactorId) REFERENCES Fertilization (operationId, productionFactorId);
ALTER TABLE ProductionFactorAppliance ADD CONSTRAINT FKProduction923450 FOREIGN KEY (productionFactorId) REFERENCES ProductionFactor (productionFactorId);
ALTER TABLE SectorIrrigationInFieldbook ADD CONSTRAINT FKSectorIrri818203 FOREIGN KEY (operationId) REFERENCES SectorIrrigation (operationId);
ALTER TABLE SectorIrrigationInFieldbook ADD CONSTRAINT FKSectorIrri326894 FOREIGN KEY (fieldbookId) REFERENCES Fieldbook (fieldbookId);
ALTER TABLE SectorIrrigationWithRecipe ADD CONSTRAINT FKSectorIrri419323 FOREIGN KEY (operationId) REFERENCES SectorIrrigation (operationId);
ALTER TABLE SectorIrrigationWithRecipe ADD CONSTRAINT FKSectorIrri96265 FOREIGN KEY (recipeId) REFERENCES Recipe (recipeId);
ALTER TABLE ProductionFactorUsedInFertigation ADD CONSTRAINT FKProduction369672 FOREIGN KEY (operationId, productionFactorId) REFERENCES ProductionFactorAppliance (operationId, productionFactorId);
ALTER TABLE CropInSector ADD CONSTRAINT FKCropInSect662429 FOREIGN KEY (sectorID) REFERENCES Sector (sectorID);
ALTER TABLE CropInSector ADD CONSTRAINT FKCropInSect947338 FOREIGN KEY (cropId) REFERENCES Crop (cropId);
COMMIT;