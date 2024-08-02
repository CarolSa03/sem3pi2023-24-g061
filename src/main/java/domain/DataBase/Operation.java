package domain.DataBase;

import java.time.LocalDate;

public class Operation {

    private Integer operationId;
    private Integer plotId;
    private LocalDate operationDate;
    public Operation(Integer operationId, Integer plotId, LocalDate operationDate) {
        this.operationId = operationId;
        this.plotId = plotId;
        this.operationDate = operationDate;
    }
    public Operation(Integer operationId) {
        this.operationId = operationId;
    }
    public Integer getOperationId() {
        return operationId;
    }
    public void setOperationId(Integer operationId) {
        this.operationId = operationId;
    }
    public Integer getPlotId() {
        return plotId;
    }
    public void setPlotId(Integer plotId) {
        this.plotId = plotId;
    }
    public LocalDate getOperationDate() {
        return operationDate;
    }
    public void setOperationDate(LocalDate operationDate) {
        this.operationDate = operationDate;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass())
            return false;
        if (this == obj)
            return true;
        Operation o = (Operation) obj;
        return this.operationId.equals(o.operationId);
    }
}
