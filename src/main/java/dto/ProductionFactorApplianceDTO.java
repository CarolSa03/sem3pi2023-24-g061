package dto;

import java.time.LocalDate;

public class ProductionFactorApplianceDTO {
    private LocalDate operationDate;
    private String productionFactorDesignation;
    private String plantDesignation;

    public ProductionFactorApplianceDTO(LocalDate operationDate, String productionFactorDesignation, String plantDesignation) {
        this.operationDate = operationDate;
        this.productionFactorDesignation = productionFactorDesignation;
        this.plantDesignation = plantDesignation;
    }

    public LocalDate getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(LocalDate operationDate) {
        this.operationDate = operationDate;
    }

    public String getProductionFactorDesignation() {
        return productionFactorDesignation;
    }

    public void setProductionFactorDesignation(String productionFactorDesignation) {
        this.productionFactorDesignation = productionFactorDesignation;
    }

    public String getPlantDesignation() {
        return plantDesignation;
    }

    public void setPlantDesignation(String plantDesignation) {
        this.plantDesignation = plantDesignation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductionFactorApplianceDTO other)) return false;
        return getOperationDate().equals(other.getOperationDate()) &&
                getProductionFactorDesignation().equals(other.getProductionFactorDesignation()) &&
                getPlantDesignation().equals(other.getPlantDesignation());
    }

    @Override
    public String toString() {
        String operationDateString = operationDate.getDayOfMonth() + "/" + operationDate.getMonthValue() + "/" + operationDate.getYear();
        return String.format("%s, %s, %s", operationDateString, productionFactorDesignation, plantDesignation);
    }

}
