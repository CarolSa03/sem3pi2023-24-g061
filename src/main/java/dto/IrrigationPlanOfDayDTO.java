package dto;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The type Irrigation plan.
 */
public class IrrigationPlanOfDayDTO {

    private List<LocalTime> cycleBegins;
    private List<LocalTime> cycleEnds;
    private List<IrrigationDTO> irrigationList;

    /**
     * Instantiates a new Irrigation plan.
     *
     * @param cycleBegins     the cycle begins
     * @param cycleEnds       the cycle ends
     * @param irrigationList   the irrigation list
     */
    public IrrigationPlanOfDayDTO(List<LocalTime> cycleBegins, List<LocalTime> cycleEnds, List<IrrigationDTO> irrigationList) {
        setCycleBegins(cycleBegins);
        setCycleEnds(cycleEnds);
        setIrrigationList(irrigationList);
    }

    /**
     * Instantiates a new Irrigation plan.
     */
    public IrrigationPlanOfDayDTO() {
        setCycleBegins(new ArrayList<>());
        setCycleEnds(new ArrayList<>());
        setIrrigationList(new ArrayList<>());
    }

    public List<LocalTime> getCycleBegins() {
        return cycleBegins;
    }

    public void setCycleBegins(List<LocalTime> cycleBegins) {
        this.cycleBegins = cycleBegins;
    }

    public List<LocalTime> getCycleEnds() {
        return cycleEnds;
    }

    public void setCycleEnds(List<LocalTime> cycleEnds) {
        this.cycleEnds = cycleEnds;
    }

    public List<IrrigationDTO> getIrrigationList() {
        return irrigationList;
    }

    public void setIrrigationList(List<IrrigationDTO> irrigationList) {
        this.irrigationList = irrigationList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IrrigationPlanOfDayDTO)) return false;
        IrrigationPlanOfDayDTO that = (IrrigationPlanOfDayDTO) o;
        return Objects.equals(getCycleBegins(), that.getCycleBegins()) &&
                Objects.equals(getCycleEnds(), that.getCycleEnds()) &&
                Objects.equals(getIrrigationList(), that.getIrrigationList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCycleBegins(), getCycleEnds(), getIrrigationList());
    }

}

