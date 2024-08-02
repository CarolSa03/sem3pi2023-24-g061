package dto;

import java.util.List;

public class RecipeDTO {

    private Integer id;
    private List<ProductionFactorDTO> productionFactors;
    private List<UnitDTO> units;
    private List<Float> values;

    public RecipeDTO(Integer id, List<ProductionFactorDTO> productionFactors, List<UnitDTO> units, List<Float> values) {
        this.id = id;
        this.productionFactors = productionFactors;
        this.units = units;
        this.values = values;
    }

    public RecipeDTO() {
    }

    public Integer getId() {
        return id;
    }

    public List<ProductionFactorDTO> getProductionFactors() {
        return productionFactors;
    }

    public List<UnitDTO> getUnits() {
        return units;
    }

    public List<Float> getValues() {
        return values;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setProductionFactors(List<ProductionFactorDTO> productionFactors) {
        this.productionFactors = productionFactors;
    }

    public void setUnits(List<UnitDTO> units) {
        this.units = units;
    }

    public void setValues(List<Float> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        StringBuilder returnString = new StringBuilder("Recipe ID: " + id + "\n");
        for (int i = 0; i < productionFactors.size(); i++) {
            returnString.append(productionFactors.get(i).getDesignation()).append(" ").append(values.get(i)).append(" ").append(units.get(i).getDesignation()).append("\n");
        }
        return returnString.toString();
    }

}
