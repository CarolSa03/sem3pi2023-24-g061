package domain.Irrigation;

import domain.DataBase.ProductionFactor;

import java.util.ArrayList;
import java.util.List;

public class Recipe {

    private Integer id;
    private List<ProductionFactor> productionFactors;

    public Recipe(Integer id, List<ProductionFactor> productionFactors) {
        this.id = id;
        this.productionFactors = productionFactors;
    }

    public Recipe(Integer id) {
        this.id = id;
        this.productionFactors = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public List<ProductionFactor> getProductionFactors() {
        return productionFactors;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setProductionFactors(List<ProductionFactor> productionFactors) {
        this.productionFactors = productionFactors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recipe recipe)) return false;
        return id.equals(recipe.id);
    }
}
