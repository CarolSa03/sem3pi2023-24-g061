package dto;

import java.time.LocalDate;
import java.util.Objects;

public class OperationWeedingDTO {
    private LocalDate operationDate;
    private int plotId;
    private int cropId;
    private int unitId;
    private int value;

    public OperationWeedingDTO(int plotId) {
        this.plotId = plotId;
    }

    public OperationWeedingDTO() {
    }

    public int getPlotId() {
        return plotId;
    }

    public void setPlotId(int plotId) {
        this.plotId = plotId;
    }

    public LocalDate getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(LocalDate operationDate) {
        this.operationDate = operationDate;
    }

    public int getCropId() {
        return cropId;
    }

    public void setCropId(int crop) {
        this.cropId = crop;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationWeedingDTO that = (OperationWeedingDTO) o;
        return plotId == that.plotId && cropId == that.cropId && unitId == that.unitId && value == that.value && Objects.equals(operationDate, that.operationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationDate, plotId, cropId, unitId, value);
    }

    @Override
    public String toString() {
        String operationDateString = operationDate.getDayOfMonth() + "/" + operationDate.getMonthValue() + "/" + operationDate.getYear();
        return String.format("%-10s %-10s %-10s %-10s %-10s", operationDateString, plotId, cropId, value, unitId);
    }
}