package dto;

import java.time.LocalDate;

/**
 * The type Plot dto.
 */
public class OperationDTO {
    private Integer id;
    private Integer plotId;
    private LocalDate operationDate;
    public OperationDTO(Integer id, Integer plotId, LocalDate operationDate) {
        this.id = id;
        this.plotId = plotId;
        this.operationDate = operationDate;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
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

}
