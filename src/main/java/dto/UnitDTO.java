package dto;

public class UnitDTO {
    private Integer id;
    private String designation;
    private String unitType;
    public UnitDTO(Integer id, String designation, String unitType) {
        this.id = id;
        this.designation = designation;
        this.unitType = unitType;
    }
    public UnitDTO(String designation) {
        this.designation = designation;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getDesignation() {
        return designation;
    }
    public void setDesignation(String designation) {
        this.designation = designation;
    }
    public String getUnitType() {
        return unitType;
    }
    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    @Override
    public String toString() {
        return "Unit ID: " + id + ", Designation: " + designation;
    }
}
