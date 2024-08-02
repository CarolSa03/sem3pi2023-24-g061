package domain.Irrigation;

import domain.DataBase.Sector;

import java.time.LocalDate;
import java.time.LocalTime;

public class Fertigation extends Irrigation {

    private Recipe mix;
    private Integer mixRecurrence;

    public Fertigation(Sector sector, LocalDate operationDate, Integer durationInMinutes, LocalTime startTime, LocalTime endTime, String regularity, Integer mixId, Integer mixRecurrence) {
        super(sector, operationDate, durationInMinutes, startTime, endTime, regularity);
        this.mix = new Recipe(mixId);
        this.mixRecurrence = mixRecurrence;
    }

    public Fertigation(Sector sector, LocalDate operationDate, Integer durationInMinutes, LocalTime startTime, LocalTime endTime, Integer mixId, Integer mixRecurrence) {
        super(sector, operationDate, durationInMinutes, startTime, endTime);
        this.mix = new Recipe(mixId);
        this.mixRecurrence = mixRecurrence;
    }

    public Fertigation(Irrigation irrigation, Integer mixId) {
        super(irrigation.getSector(), irrigation.getOperationDate(), irrigation.getDurationInMinutes(), irrigation.getStartTime(), irrigation.getEndTime(), irrigation.getRegularity());
        this.mix = new Recipe(mixId);
    }

    public Recipe getMix() {
        return mix;
    }

    public void setMix(Recipe mix) {
        if (mix.getId() <= 0) throw new IllegalArgumentException("Mix id must be a positive integer.");
        this.mix = mix;
    }

    public Integer getMixRecurrence() {
        return mixRecurrence;
    }

    public void setMixRecurrence(Integer mixRecurrence) {
        if (mixRecurrence < 0) throw new IllegalArgumentException("Mix recurrence must be a positive integer.");
        this.mixRecurrence = mixRecurrence;
    }

    @Override
    public String toString() {
        return super.toString() + ",mix" + mix.getId();
    }

}
