CREATE TABLE Plot (
  id          number(10) GENERATED AS IDENTITY, 
  designation varchar2(255), 
  area        double precision, 
  PRIMARY KEY (id));
CREATE TABLE InstalledCrop (
  Cropid number(10) NOT NULL, 
  Plotid number(10) NOT NULL, 
  PRIMARY KEY (Cropid, 
  Plotid));
CREATE TABLE Crop (
  id           number(10) GENERATED AS IDENTITY, 
  designation  number(10), 
  name         number(10), 
  TypeCropid   number(10) NOT NULL, 
  Plantvariety varchar2(255) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE TypeCrop (
  id   number(10) GENERATED AS IDENTITY, 
  type number(10), 
  PRIMARY KEY (id));
CREATE TABLE Operation (
  id                   number(10) GENERATED AS IDENTITY, 
  date                 number(10), 
  amount               number(10), 
  Cropid               number(10) NOT NULL, 
  TypeOperationid      number(10) NOT NULL, 
  PlotCropCropid       number(10) NOT NULL, 
  PlotCropPlotid       number(10) NOT NULL, 
  InstalledCrop2Cropid number(10) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE TypeOperation (
  id   number(10) GENERATED AS IDENTITY, 
  unit number(10), 
  PRIMARY KEY (id));
CREATE TABLE ProductionFactorOperation (
  ProductionFactorid number(10) NOT NULL, 
  Operationid        number(10) NOT NULL, 
  PRIMARY KEY (ProductionFactorid, 
  Operationid));
CREATE TABLE ProductionFactor (
  id                       number(10) GENERATED AS IDENTITY, 
  commercialName           varchar2(255) NOT NULL, 
  ApplicationColumn        varchar2(255) NOT NULL, 
  ProductionFactorTypetype varchar2(255) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE Application (
  application varchar2(255) NOT NULL, 
  PRIMARY KEY (application));
CREATE TABLE ProductionFactorType (
  type varchar2(255) NOT NULL, 
  PRIMARY KEY (type));
CREATE TABLE DataSheet (
  id                   number(10) GENERATED AS IDENTITY, 
  formulation          varchar2(255) NOT NULL, 
  Manufactormanufactor number(10) NOT NULL, 
  ProductionFactorid   number(10) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE Manufactor (
  manufactor number(10) GENERATED AS IDENTITY, 
  PRIMARY KEY (manufactor));
CREATE TABLE SubstanceDataSheet (
  Substanceid number(10) NOT NULL, 
  DataSheetid number(10) NOT NULL, 
  PRIMARY KEY (Substanceid, 
  DataSheetid));
CREATE TABLE Substance2 (
  id        number(10) GENERATED AS IDENTITY, 
  substance number(10), 
  quantity  number(10), 
  unit      number(10), 
  PRIMARY KEY (id));
CREATE TABLE Plant (
  variety          varchar2(255) NOT NULL, 
  specie           varchar2(255) NOT NULL, 
  name             varchar2(255) NOT NULL, 
  plantationPeriod number(10) NOT NULL, 
  pruningPeriod    number(10) NOT NULL, 
  floweringPeriod  number(10) NOT NULL, 
  harvestPeriod    number(10) NOT NULL, 
  PRIMARY KEY (variety));
CREATE TABLE mode (
  id              number(10) GENERATED AS IDENTITY, 
  TypeOperationid number(10) NOT NULL, 
  PRIMARY KEY (id));
ALTER TABLE InstalledCrop ADD CONSTRAINT FKInstalledC560983 FOREIGN KEY (Cropid) REFERENCES Crop (id);
ALTER TABLE InstalledCrop ADD CONSTRAINT FKInstalledC584552 FOREIGN KEY (Plotid) REFERENCES Plot (id);
ALTER TABLE Crop ADD CONSTRAINT FKCrop313017 FOREIGN KEY (TypeCropid) REFERENCES TypeCrop (id);
ALTER TABLE Operation ADD CONSTRAINT FKOperation669781 FOREIGN KEY (Cropid) REFERENCES Crop (id);
ALTER TABLE Operation ADD CONSTRAINT FKOperation295216 FOREIGN KEY (TypeOperationid) REFERENCES TypeOperation (id);
ALTER TABLE ProductionFactorOperation ADD CONSTRAINT FKProduction657726 FOREIGN KEY (ProductionFactorid) REFERENCES ProductionFactor (id);
ALTER TABLE ProductionFactorOperation ADD CONSTRAINT FKProduction687992 FOREIGN KEY (Operationid) REFERENCES Operation (id);
ALTER TABLE ProductionFactor ADD CONSTRAINT FKProduction916604 FOREIGN KEY (ApplicationColumn) REFERENCES Application (application);
ALTER TABLE ProductionFactor ADD CONSTRAINT FKProduction229151 FOREIGN KEY (ProductionFactorTypetype) REFERENCES ProductionFactorType (type);
ALTER TABLE DataSheet ADD CONSTRAINT FKDataSheet647931 FOREIGN KEY (Manufactormanufactor) REFERENCES Manufactor (manufactor);
ALTER TABLE DataSheet ADD CONSTRAINT FKDataSheet508645 FOREIGN KEY (ProductionFactorid) REFERENCES ProductionFactor (id);
ALTER TABLE SubstanceDataSheet ADD CONSTRAINT FKSubstanceD660609 FOREIGN KEY (Substanceid) REFERENCES Substance2 (id);
ALTER TABLE SubstanceDataSheet ADD CONSTRAINT FKSubstanceD493131 FOREIGN KEY (DataSheetid) REFERENCES DataSheet (id);
ALTER TABLE Crop ADD CONSTRAINT FKCrop552410 FOREIGN KEY (Plantvariety) REFERENCES Plant (variety);
ALTER TABLE Operation ADD CONSTRAINT FKOperation977692 FOREIGN KEY (InstalledCrop2Cropid, PlotCropPlotid) REFERENCES InstalledCrop (Cropid, Plotid);
ALTER TABLE mode ADD CONSTRAINT FKmode949315 FOREIGN KEY (TypeOperationid) REFERENCES TypeOperation (id);
