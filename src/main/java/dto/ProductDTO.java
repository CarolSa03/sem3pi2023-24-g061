package dto;

public class ProductDTO {
    private Integer id;
    private String designation;
    public ProductDTO(Integer id, String designation) {
        this.id = id;
        this.designation = designation;
    }

    public Integer getId() {
        return id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Override
    public String toString() {
        return id + " - " + designation;
    }

}
