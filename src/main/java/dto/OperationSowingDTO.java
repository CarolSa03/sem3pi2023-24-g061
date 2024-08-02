package dto;

import java.time.LocalDate;

public class OperationSowingDTO {
    private LocalDate operationDate;
    private Double amount;

    private String unit;
    private String plotDesignation;
    private String plantDesignation;


    public OperationSowingDTO(LocalDate operationDate, Double amount, String unit, String plotDesignation,String plantDesignation) {
        this.operationDate = operationDate;
        this.amount = amount;
        this.unit = unit;
        this.plotDesignation = plotDesignation;
        this.plantDesignation = plantDesignation;
    }

    public LocalDate getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(LocalDate operationDate) {
        this.operationDate = operationDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPlantDesignation() {
        return plantDesignation;
    }

    public void setPlantDesignation(String plantDesignation) {
        this.plantDesignation = plantDesignation;
    }

    public String getPlotDesignation() {
        return plotDesignation;
    }

    public void setPlotDesignation(String plotDesignation) {
        this.plotDesignation = plotDesignation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OperationSowingDTO other)) return false;
        return getOperationDate().equals(other.getOperationDate()) &&
                getAmount().equals(other.getAmount()) &&
                getUnit().equals(other.getUnit()) &&
                getPlantDesignation().equals(other.getPlantDesignation()) &&
                getPlotDesignation().equals(other.getPlotDesignation());
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s, %s", operationDate, amount, unit, plotDesignation, plantDesignation);
    }
}
