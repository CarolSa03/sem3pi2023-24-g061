package domain.Irrigation;

import java.time.LocalDate;
import java.util.Map;

/**
 * The type Irrigation plan.
 */
public class IrrigationPlan {

    private static final LocalDate creationDate = LocalDate.of(2023, 12, 20);
    private static final int numberOfDays = 30;
    private Map<LocalDate, IrrigationPlanOfDay> irrigationsPerDay;

    public IrrigationPlan(Map<LocalDate, IrrigationPlanOfDay> irrigationsPerDay) {
        this.irrigationsPerDay = irrigationsPerDay;
    }

    public IrrigationPlan() {
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public Map<LocalDate, IrrigationPlanOfDay> getIrrigationsPerDay() {
        return irrigationsPerDay;
    }

    public void setIrrigationsPerDay(Map<LocalDate, IrrigationPlanOfDay> irrigationsPerDay) {
        this.irrigationsPerDay = irrigationsPerDay;
    }

}
