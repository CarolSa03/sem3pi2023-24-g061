package domain.DataBase;

import java.util.Objects;

/**
 * The type Plot.
 */
public class Plot {

    private Integer id;
    private String designation;
    private float area;
//    private Set<Crop> cropsInPlot;

    /**
     * Instantiates a new Plot.
     *
     * @param id          the id
     * @param designation the designation
     * @param area        the area
     */
    public Plot(Integer id, String designation, float area) {
        setId(id);
        setDesignation(designation);
        setArea(area);
    }

    /**
     * Instantiates a new Plot.
     *
     * @param id the id
     */
    public Plot(Integer id) {
        setId(id);
    }

    /**
     * Instantiates a new Plot.
     *
     * @param designation the designation
     */
    public Plot(String designation) {
        setDesignation(designation);
        setArea(0);
    }

    /**
     * Instantiates a new Plot.
     */
    public Plot() {
        setId(null);
        setDesignation(null);
        setArea(Float.parseFloat(null));
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
        if (id == null) this.id = 0;
        else this.id = id;
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
        if (designation == null) this.designation = "No designation assigned";
        else this.designation = designation;
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
        if (area < 0 || area == Double.parseDouble(null)) this.area = 0;
        else this.area = area;
    }

//    /**
//     * Gets crops in plot.
//     *
//     * @return the crops in plot
//     */
//    public Set<Crop> getCropsInPlot() {
//        return cropsInPlot;
//    }
//
//    /**
//     * Sets crops in plot.
//     *
//     * @param cropsInPlot the crops in plot
//     */
//    public void setCropsInPlot(Set<Crop> cropsInPlot) {
//        this.cropsInPlot = cropsInPlot;
//    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Plot other)) {
            return false;
        }
        return this.id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}