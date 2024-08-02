package domain.DataBase;

public class ProductionFactor {
    private Integer productionFactorId;
    private String designation;

    public ProductionFactor(Integer productionFactorId, String designation) {
        this.productionFactorId = productionFactorId;
        this.designation = designation;
    }

    public Integer getProductionFactorId() {
        return productionFactorId;
    }

    public void setProductionFactorId(Integer productionFactorId) {
        this.productionFactorId = productionFactorId;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductionFactor)) return false;
        ProductionFactor productionFactor = (ProductionFactor) o;
        return productionFactorId.equals(productionFactor.productionFactorId);
    }

    @Override
    public int hashCode() {
        return productionFactorId.hashCode();
    }

}
