package dto;

import java.time.LocalDate;

public class ProductionFactorDTO {
    private Integer id;
    private LocalDate date;
    private String designation;
    private String substance;
    private String type;
    private float percentage;

    public ProductionFactorDTO(Integer id, String designation, String type) {
        this.id = id;
        this.designation = designation;
        this.type = type;
    }

    public ProductionFactorDTO(String designation) {
        this.designation = designation;
    }

    public ProductionFactorDTO(LocalDate date, String designation, String substance, float percentage) {
        this.date = date;
        this.designation = designation;
        this.substance = substance;
        this.percentage = percentage;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public String getSubstance() {
        return substance;
    }

    public void setSubstance(String substance) {
        this.substance = substance;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String toStringPlot() {
        return date + " - " + designation + " - " + substance + " - " + percentage;
    }

    @Override
    public String toString() {
        return "ProductionFactor ID: " + id + ", Designation: " + designation;
    }

}
