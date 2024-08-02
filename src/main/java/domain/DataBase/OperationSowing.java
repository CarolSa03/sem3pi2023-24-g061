package domain.DataBase;

import java.time.LocalDate;

public class OperationSowing extends Operation {
    private Integer plotId;
    private Integer cropId;
    private Double amount;
    private Integer unitId;

    public OperationSowing(Integer operationId, Integer operationTypeId, LocalDate operationDate, Integer plotId, Integer cropId, Double amount, Integer unitId) {
        super(operationId, operationTypeId, operationDate);
        this.plotId = plotId;
        this.cropId = cropId;
        this.amount = amount;
        this.unitId = unitId;
    }

    public OperationSowing(LocalDate operationDate, Integer operationId, Integer cropId) {
        super(operationId, cropId, operationDate);
    }

    @Override
    public Integer getPlotId() {
        return plotId;
    }

    @Override
    public void setPlotId(Integer plotId) {
        this.plotId = plotId;
    }

    public Integer getCropId() {
        return cropId;
    }

    public void setCropId(Integer cropId) {
        this.cropId = cropId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getUnit() {
        return unitId;
    }

    public void setUnit(Integer unitId) {
        this.unitId = unitId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof OperationSowing operationSowing)) {
            return false;
        }
        return this.getOperationId().equals(operationSowing.getOperationId());
    }
}
