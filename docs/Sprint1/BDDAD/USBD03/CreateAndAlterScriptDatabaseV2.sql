CREATE TABLE Application (
  application varchar2(255) NOT NULL, 
  PRIMARY KEY (application));
CREATE TABLE Building (
  buildingId   number(10) NOT NULL, 
  designation  varchar2(255) NOT NULL, 
  dimension    number(10), 
  unit         varchar2(255), 
  buildingType varchar2(255) NOT NULL, 
  PRIMARY KEY (buildingId));
CREATE TABLE BuildingType (
  buildingType varchar2(255) NOT NULL, 
  PRIMARY KEY (buildingType));
CREATE TABLE Crop (
  cropId       number(10) NOT NULL, 
  plantVariety varchar2(255) NOT NULL, 
  cropType     varchar2(255) NOT NULL, 
  PRIMARY KEY (cropId));
CREATE TABLE CropType (
  cropType varchar2(255) NOT NULL, 
  PRIMARY KEY (cropType));
CREATE TABLE DataSheet (
  dataSheetId number(10) NOT NULL, 
  designation varchar2(255) NOT NULL, 
  PRIMARY KEY (dataSheetId));
CREATE TABLE Format (
  format varchar2(255) NOT NULL, 
  PRIMARY KEY (format));
CREATE TABLE InstalledCrop (
  installedCropId number(10) NOT NULL, 
  cropId          number(10) NOT NULL, 
  plotId          number(10) NOT NULL, 
  operationId     number(10) NOT NULL, 
  initialDate     date NOT NULL, 
  finalDate       date, 
  PRIMARY KEY (installedCropId, 
  cropId, 
  plotId, 
  operationId));
CREATE TABLE Manufacturer (
  manufacturer varchar2(255) NOT NULL, 
  PRIMARY KEY (manufacturer));
CREATE TABLE Operation (
  operationId     number(10) NOT NULL, 
  operationDate   date NOT NULL, 
  amount          number(10) NOT NULL, 
  operationTypeId number(10) NOT NULL, 
  plotId          number(10) NOT NULL, 
  PRIMARY KEY (operationId));
CREATE TABLE OperationType (
  operationTypeId number(10) NOT NULL, 
  operationType   varchar2(255) NOT NULL, 
  unit            varchar2(255), 
  PRIMARY KEY (operationTypeId));
CREATE TABLE OperationTypeMode (
  operationTypeMode varchar2(255) NOT NULL, 
  operationTypeId   number(10) NOT NULL, 
  PRIMARY KEY (operationTypeMode));
CREATE TABLE Plant (
  plantVariety     varchar2(255) NOT NULL, 
  species          varchar2(255) NOT NULL, 
  commonName       varchar2(255) NOT NULL, 
  plantationPeriod varchar2(255), 
  pruningPeriod    varchar2(255), 
  floweringPeriod  varchar2(255), 
  harvestPeriod    varchar2(255), 
  PRIMARY KEY (plantVariety));
CREATE TABLE Plot (
  plotId      number(10) NOT NULL, 
  designation varchar2(255) NOT NULL, 
  area        double precision NOT NULL, 
  PRIMARY KEY (plotId));
CREATE TABLE ProductionFactor (
  designation          varchar2(255) NOT NULL, 
  application          varchar2(255) NOT NULL, 
  productionFactorType varchar2(255) NOT NULL, 
  manufacturer         varchar2(255) NOT NULL, 
  format               varchar2(255) NOT NULL, 
  PRIMARY KEY (designation));
CREATE TABLE ProductionFactorOperation (
  operationId number(10) NOT NULL, 
  designation varchar2(255) NOT NULL, 
  PRIMARY KEY (operationId, 
  designation));
CREATE TABLE ProductionFactorType (
  productionFactorType varchar2(255) NOT NULL, 
  PRIMARY KEY (productionFactorType));
CREATE TABLE Substance (
  substance varchar2(255) NOT NULL, 
  PRIMARY KEY (substance));
CREATE TABLE SubstanceDataSheet (
  dataSheetId number(10) NOT NULL, 
  substance   varchar2(255) NOT NULL, 
  percentage  number(10) NOT NULL, 
  PRIMARY KEY (dataSheetId, 
  substance));
ALTER TABLE InstalledCrop ADD CONSTRAINT FKInstalledC371492 FOREIGN KEY (plotId) REFERENCES Plot (plotId);
ALTER TABLE Operation ADD CONSTRAINT FKOperation152256 FOREIGN KEY (operationTypeId) REFERENCES OperationType (operationTypeId);
ALTER TABLE ProductionFactorOperation ADD CONSTRAINT FKProduction955268 FOREIGN KEY (designation) REFERENCES ProductionFactor (designation);
ALTER TABLE ProductionFactorOperation ADD CONSTRAINT FKProduction113235 FOREIGN KEY (operationId) REFERENCES Operation (operationId);
ALTER TABLE ProductionFactor ADD CONSTRAINT FKProduction490203 FOREIGN KEY (application) REFERENCES Application (application);
ALTER TABLE ProductionFactor ADD CONSTRAINT FKProduction702566 FOREIGN KEY (productionFactorType) REFERENCES ProductionFactorType (productionFactorType);
ALTER TABLE DataSheet ADD CONSTRAINT FKDataSheet104350 FOREIGN KEY (designation) REFERENCES ProductionFactor (designation);
ALTER TABLE SubstanceDataSheet ADD CONSTRAINT FKSubstanceD758738 FOREIGN KEY (substance) REFERENCES Substance (substance);
ALTER TABLE SubstanceDataSheet ADD CONSTRAINT FKSubstanceD936108 FOREIGN KEY (dataSheetId) REFERENCES DataSheet (dataSheetId);
ALTER TABLE Crop ADD CONSTRAINT FKCrop321008 FOREIGN KEY (plantVariety) REFERENCES Plant (plantVariety);
ALTER TABLE OperationTypeMode ADD CONSTRAINT FKOperationT752459 FOREIGN KEY (operationTypeId) REFERENCES OperationType (operationTypeId);
ALTER TABLE Building ADD CONSTRAINT FKBuilding55088 FOREIGN KEY (buildingType) REFERENCES BuildingType (buildingType);
ALTER TABLE Operation ADD CONSTRAINT FKOperation480290 FOREIGN KEY (plotId) REFERENCES Plot (plotId);
ALTER TABLE ProductionFactor ADD CONSTRAINT FKProduction61842 FOREIGN KEY (manufacturer) REFERENCES Manufacturer (manufacturer);
ALTER TABLE Crop ADD CONSTRAINT FKCrop672864 FOREIGN KEY (cropType) REFERENCES CropType (cropType);
ALTER TABLE InstalledCrop ADD CONSTRAINT FKInstalledC705875 FOREIGN KEY (cropId) REFERENCES Crop (cropId);
ALTER TABLE InstalledCrop ADD CONSTRAINT FKInstalledC294225 FOREIGN KEY (operationId) REFERENCES Operation (operationId);
ALTER TABLE ProductionFactor ADD CONSTRAINT FKProduction315585 FOREIGN KEY (format) REFERENCES Format (format);
