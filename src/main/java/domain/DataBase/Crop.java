package domain.DataBase;

/**
 * The type Crop.
 */
public class Crop {

    private Integer cropId;
//    private Integer cropTypeId;
//    private Integer plotId;
//    private Integer plantId;

    private String cropType;
    private String cropCycle;

    /**
     * Instantiates a new Crop.
     *
     * @param cropType  the crop type
     * @param cropCycle the crop cycle
     */
    public Crop(String cropType, String cropCycle) {
        setCropType(cropType);
        setCropCycle(cropCycle);
    }

    /**
     * Instantiates a new Crop.
     */
    public Crop() {
        setCropType(null);
        setCropCycle(null);
    }

    /**
     * Gets crop type.
     *
     * @return the crop type
     */
    public String getCropType() {
        return cropType;
    }

    /**
     * Sets crop type.
     *
     * @param cropType the crop type
     */
    public void setCropType(String cropType) {
        if (cropType == null) this.cropType = "No crop type assigned";
        else this.cropType = cropType;
    }

    /**
     * Gets crop cycle.
     *
     * @return the crop cycle
     */
    public String getCropCycle() {
        return cropCycle;
    }

    /**
     * Sets crop cycle.
     *
     * @param cropCycle the crop cycle
     */
    public void setCropCycle(String cropCycle) {
        if (cropCycle == null) this.cropCycle = "No crop cycle assigned";
        else this.cropCycle = cropCycle;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Crop other)) {
            return false;
        }
        return this.cropCycle.equals(other.cropCycle) && this.cropType.equals(other.cropType);
    }

}