package domain.DataBase;

import java.time.LocalDate;

public class OperationWeeding {
    private int operationId;
    private LocalDate operationDate;
    private int plotId;
    private int cropId;
    private int unitId;
    private int value;

    public OperationWeeding(int operationId, LocalDate operationDate, int plotId, int cropId, int unitId, int value) {
        this.operationId = operationId;
        this.operationDate = operationDate;
        this.plotId = plotId;
        this.cropId = cropId;
        this.unitId = unitId;
        this.value = value;
    }

    public OperationWeeding(LocalDate operationDate, int plotId, int cropId, int unitId, int value) {
        this.operationDate = operationDate;
        this.plotId = plotId;
        this.cropId = cropId;
        this.unitId = unitId;
        this.value = value;
    }

    public OperationWeeding(int plotId) {
        this.plotId = plotId;
    }

    public OperationWeeding() {

    }

    public int getOperationId() {
        return operationId;
    }

    public void setOperationId(int operationId) {
        this.operationId = operationId;
    }

    public LocalDate getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(LocalDate operationDate) {
        this.operationDate = operationDate;
    }

    public int getPlotId() {
        return plotId;
    }

    public void setPlotId(int plotId) {
        this.plotId = plotId;
    }

    public int getCropId() {
        return cropId;
    }

    public void setCropId(int cropId) {
        this.cropId = cropId;
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
}
