package domain.BasketDistribution;

import java.util.Objects;

public class Local {

    private Integer id;
    private String designation;
    private double lat;
    private double lng;

    public Local(Integer id, String designation, double lat, double lng) {
        setId(id);
        setDesignation(designation);
        setLat(lat);
        setLng(lng);
    }

    public Local(Integer id, String designation) {
        setId(id);
        setDesignation(designation);
    }
    public Local() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        if (id <= 0) throw new IllegalArgumentException("Id must be a positive number.");
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        if (designation == null) {
            designation = "No Location Found";
        }

        this.designation = designation;
    }

    public double getLat() {
        return lat;
    }

    /**
     * The setter for the latitude, checking if its value is between -90 and 90 degrees
     *
     * @param lat double
     */
    public void setLat(double lat) {
        if (lat >= -90 && lat <= 90) {
            this.lat = lat;
        } else {
            throw new IllegalArgumentException("Latitude must be between -90 and 90 degrees.");
        }
    }

    public double getLng() {
        return lng;
    }

    /**
     * The setter for the longitude, checking if its value is between -180 and 180 degrees
     *
     * @param lng double
     */
    public void setLng(double lng) {
        if (lng >= -180 && lng <= 180) {
            this.lng = lng;
        } else {
            throw new IllegalArgumentException("Longitude must be between -180 and 180 degrees.");
        }
    }

    public double getDistance(Local o) {
        return Math.sqrt(Math.pow(this.lat - o.lat, 2) + Math.pow(this.lng - o.lng, 2));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Local local1 = (Local) o;
        return Objects.equals(this.id, local1.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, designation, lat, lng);
    }

    @Override
    public String toString() {
        return designation;
    }
}
