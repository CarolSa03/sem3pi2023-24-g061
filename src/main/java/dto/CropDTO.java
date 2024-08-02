package dto;

/**
 * The type Crop dto.
 */
public class CropDTO {
    private Integer id;
    private String designation;
    public CropDTO(Integer id, String designation) {
        this.id = id;
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
        this.designation=designation;
    }
}
