package dto;

/**
 * The type Plot dto.
 */
public class PlotDTO {
    private Integer id;
    private String designation;
    private float area;

    /**
     * Instantiates a new Plot dto.
     *
     * @param id          the id
     * @param designation the designation
     * @param area        the area
     */
    public PlotDTO(Integer id, String designation, float area) {
        setId(id);
        setDesignation(designation);
        setArea(area);
    }

    /**
     * Instantiates a new Plot dto.
     *
     * @param designation the designation
     */
    public PlotDTO(String designation) {
        setDesignation(designation);
        setArea(0);
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets designation.
     *
     * @return the designation
     */
    public String getDesignation() {
        return designation;
    }

    /**
     * Sets designation.
     *
     * @param designation the designation
     */
    public void setDesignation(String designation) {
        this.designation = designation;
    }

    /**
     * Gets area.
     *
     * @return the area
     */
    public float getArea() {
        return area;
    }

    /**
     * Sets area.
     *
     * @param area the area
     */
    public void setArea(float area) {
        this.area = area;
    }

}
