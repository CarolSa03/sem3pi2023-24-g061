package domain.Irrigation;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The type Irrigation plan.
 */
public class IrrigationPlanOfDay {

    private List<LocalTime> cycleBegins;
    private List<LocalTime> cycleEnds;
    private List<Irrigation> irrigationList;

    /**
     * Instantiates a new Irrigation plan.
     *
     * @param cycleBegins     the cycle begins
     * @param cycleEnds       the cycle ends
     * @param irrigationList   the irrigation list
     */
    public IrrigationPlanOfDay(List<LocalTime> cycleBegins, List<LocalTime> cycleEnds, List<Irrigation> irrigationList) {
        setCycleBegins(cycleBegins);
        setCycleEnds(cycleEnds);
        setIrrigationList(irrigationList);
    }

    /**
     * Instantiates a new Irrigation plan.
     */
    public IrrigationPlanOfDay() {
        setCycleBegins(new ArrayList<>());
        setCycleEnds(new ArrayList<>());
        setIrrigationList(new ArrayList<>());
    }

    public List<LocalTime> getCycleBegins() {
        return cycleBegins;
    }

    public void setCycleBegins(List<LocalTime> cycleBegins) {
        int i = 0;
        if (cycleEnds == null || cycleEnds.isEmpty()) {
            this.cycleBegins = new ArrayList<>();
            return;
        }
        LocalTime previousEndTime = cycleEnds.get(i);
        for (LocalTime begin : cycleBegins) {
            if (begin.isBefore(previousEndTime)) {
                throw new IllegalArgumentException("Cycle's begin must be after previous cycle's end.");
            } else if (begin.isBefore(cycleBegins.get(cycleBegins.indexOf(begin) - 1))) {
                throw new IllegalArgumentException("Cycle's begin must be after previous cycle's begin.");
            } else if (i++ < cycleEnds.size()) previousEndTime = cycleEnds.get(i);
            else break;
        }
        this.cycleBegins = cycleBegins;
    }

    public List<LocalTime> getCycleEnds() {
        return cycleEnds;
    }

    public void setCycleEnds(List<LocalTime> cycleEnds) {
        this.cycleEnds = cycleEnds;
    }

    /**
     * Gets irrigationlist.
     *
     * @return the irrigationlist
     */
    public List<Irrigation> getIrrigationlist() {
        return irrigationList;
    }

    /**
     * Sets irrigation list.
     *
     * @param irrigationList the irrigation list
     */
    public void setIrrigationList(List<Irrigation> irrigationList) {
        this.irrigationList = irrigationList;
    }

    public void addCycleBegin(LocalTime cycleBegin) {
        if (cycleBegin == null) throw new IllegalArgumentException("Cycle begin cannot be null.");
        if (cycleBegins == null) cycleBegins = new ArrayList<>();
        cycleBegins.add(cycleBegin);
        if (!cycleEnds.isEmpty() && cycleBegin.isBefore(cycleEnds.get(cycleBegins.indexOf(cycleBegin) - 1))) {
            throw new IllegalArgumentException("Cycle's begin must be after previous cycle's end.");
        }
        if (cycleBegins.size() > 1 && cycleBegin.isBefore(cycleBegins.get(cycleBegins.indexOf(cycleBegin) - 1))) {
            throw new IllegalArgumentException("Cycle's begin must be after previous cycle's begin.");
        }
    }

    public void addCycleEnd(LocalTime cycleEnd) {
        if (cycleEnd == null) throw new IllegalArgumentException("Cycle end cannot be null.");
        if (cycleEnds == null) cycleEnds = new ArrayList<>();
        cycleEnds.add(cycleEnd);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof IrrigationPlanOfDay other)) {
            return false;
        }
        return irrigationList.equals(other.irrigationList) &&
                cycleBegins.equals(other.cycleBegins) &&
                cycleEnds.equals(other.cycleEnds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cycleBegins, cycleEnds, irrigationList);
    }

}

