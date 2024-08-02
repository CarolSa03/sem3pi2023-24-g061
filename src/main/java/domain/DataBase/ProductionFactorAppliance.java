package domain.DataBase;

import java.time.LocalDate;

public class ProductionFactorAppliance extends Operation {

    private Integer productionFactorId;

    public ProductionFactorAppliance(Integer operationId, Integer plotId, Integer operationTypeId, LocalDate operationDate, Double amount, String unit, Integer productionFactorId) {
        super(operationId, plotId, operationDate);
        this.productionFactorId = productionFactorId;
    }

    public Integer getProductionFactorId() {
        return productionFactorId;
    }

    public void setProductionFactorId(Integer productionFactorId) {
        this.productionFactorId = productionFactorId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ProductionFactorAppliance productionFactorAppliance)) {
            return false;
        }
        return this.getOperationId().equals(productionFactorAppliance.getOperationId());
    }
}
