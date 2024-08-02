package dto;

import java.time.LocalDate;
import java.util.Map;

/**
 * The type Irrigation plan dto.
 */
public class IrrigationPlanDTO {
    private LocalDate creationDate;
    private int numberOfDays;
    private Map<LocalDate, IrrigationPlanOfDayDTO> irrigationsPerDay;

    /**
     * Instantiates a new Irrigation plan dto.
     *
     * @param creationDate      the creation date
     * @param numberOfDays      the number of days
     * @param irrigationsPerDay the irrigations per day
     */
    public IrrigationPlanDTO(LocalDate creationDate, Integer numberOfDays, Map<LocalDate, IrrigationPlanOfDayDTO> irrigationsPerDay) {
        setCreationDate(creationDate);
        setNumberOfDays(numberOfDays);
        setIrrigationsPerDay(irrigationsPerDay);
    }

    /**
     * Gets creation date.
     *
     * @return the creation date
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Sets creation date.
     *
     * @param creationDate the creation date
     */
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Gets number of days.
     *
     * @return the number of days
     */
    public int getNumberOfDays() {
        return numberOfDays;
    }

    /**
     * Sets number of days.
     *
     * @param numberOfDays the number of days
     */
    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    /**
     * Gets irrigations per day.
     *
     * @return the irrigations per day
     */
    public Map<LocalDate, IrrigationPlanOfDayDTO> getIrrigationsPerDay() {
        return irrigationsPerDay;
    }

    /**
     * Sets irrigations per day.
     *
     * @param irrigationsPerDay the irrigations per day
     */
    public void setIrrigationsPerDay(Map<LocalDate, IrrigationPlanOfDayDTO> irrigationsPerDay) {
        this.irrigationsPerDay = irrigationsPerDay;
    }
}
