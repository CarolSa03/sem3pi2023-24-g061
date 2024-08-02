package domain.DataBase;

import java.time.LocalDate;

public class Fertilization extends ProductionFactorAppliance {
    private Integer fertilizationModeId;

    public Fertilization(Integer operationId, Integer cropId, Integer operationTypeId, LocalDate operationDate, Double amount, String unit, Integer productionFactorId, Integer fertilizationModeId) {
        super(operationId, cropId, operationTypeId, operationDate, amount, unit, productionFactorId);
        this.fertilizationModeId = fertilizationModeId;
    }

    public Integer getFertilizationModeId() {
        return fertilizationModeId;
    }

    public void setFertilizationModeId(Integer fertilizationModeId) {
        this.fertilizationModeId = fertilizationModeId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Fertilization fertilization)) {
            return false;
        }
        return this.getOperationId().equals(fertilization.getOperationId());
    }

}

