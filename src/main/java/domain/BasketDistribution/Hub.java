package domain.BasketDistribution;

import java.time.LocalTime;
import java.util.Objects;

/**
 * The type Hub.
 */
public class Hub extends Local {

    private int collaborators;
    private LocalTime operatingHoursStart;
    private LocalTime operatingHoursEnd;

    /**
     * Instantiates a new Hub.
     *
     * @param id                  the id
     * @param local               the local
     * @param lat                 the lat
     * @param lng                 the lng
     * @param collaborators       the collaborators
     * @param operatingHoursStart the operating hours start
     * @param operatingHoursEnd   the operating hours end
     */
    public Hub(Integer id, String local, double lat, double lng, int collaborators, LocalTime operatingHoursStart, LocalTime operatingHoursEnd) {
        super(id, local, lat, lng);
        this.collaborators = collaborators;
        this.operatingHoursStart = operatingHoursStart;
        this.operatingHoursEnd = operatingHoursEnd;
    }

    public Hub(Integer id, String local){
        super(id, local);
    }

    public Hub() {
    }

    /**
     * Gets collaborators.
     *
     * @return the collaborators
     */
    public int getCollaborators() {
        return collaborators;
    }

    /**
     * The setter for the collaborators
     * number
     *
     * @param collaborators int
     */
    public void setCollaborators(int collaborators) {
        this.collaborators = collaborators;
    }

    /**
     * Gets operating hours start.
     *
     * @return the operating hours start
     */
    public LocalTime getOperatingHoursStart() {
        return operatingHoursStart;
    }

    /**
     * The setter for the operating hours start
     *
     * @param operatingHoursStart LocalTime
     */
    public void setOperatingHoursStart(LocalTime operatingHoursStart) {
        this.operatingHoursStart = operatingHoursStart;
    }

    /**
     * Gets operating hours end.
     *
     * @return the operating hours end
     */
    public LocalTime getOperatingHoursEnd() {
        return operatingHoursEnd;
    }

    /**
     * The setter for the operating hours end
     *
     * @param operatingHoursEnd LocalTime
     */
    public void setOperatingHoursEnd(LocalTime operatingHoursEnd) {
        this.operatingHoursEnd = operatingHoursEnd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), collaborators, operatingHoursStart, operatingHoursEnd);
    }

    @Override
    public String toString() {
        return "Hub{" + super.toString() +
                ", collaborators=" + collaborators +
                ", operatingHoursStart=" + operatingHoursStart +
                ", operatingHoursEnd=" + operatingHoursEnd +
                '}';
    }
}
