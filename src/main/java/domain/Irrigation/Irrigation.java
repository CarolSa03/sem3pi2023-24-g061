package domain.Irrigation;

import domain.DataBase.Sector;
import utils.Utils;

import java.time.LocalDate;
import java.time.LocalTime;

public class Irrigation {

    private Integer id;
    private Sector sector;
    private LocalDate operationDate;
    private Integer durationInMinutes;
    private LocalTime startTime;
    private LocalTime endTime;
    private String regularity;

    public Irrigation(Sector sector, LocalDate operationDate, Integer durationInMinutes, LocalTime startTime, LocalTime endTime, String regularity) {
        this.sector = sector;
        this.operationDate = operationDate;
        this.durationInMinutes = durationInMinutes;
        this.startTime = startTime;
        this.endTime = endTime;
        this.regularity = regularity;
    }

    public Irrigation(Sector sector, LocalDate operationDate, Integer durationInMinutes, LocalTime startTime, LocalTime endTime) {
        this.sector = sector;
        this.operationDate = operationDate;
        this.durationInMinutes = durationInMinutes;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Irrigation() {
    }

    public Integer getId() {
        return id;
    }

    public Sector getSector() {
        return sector;
    }

    public LocalDate getOperationDate() {
        return operationDate;
    }

    public Integer getDurationInMinutes() {
        return durationInMinutes;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public String getRegularity() {
        return regularity;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public void setOperationDate(LocalDate operationDate) {
        this.operationDate = operationDate;
    }

    public void setDurationInMinutes(Integer durationInMinutes) {
        if (durationInMinutes < 0) throw new IllegalArgumentException("Duration in minutes must be a positive integer.");
        this.durationInMinutes = durationInMinutes;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setRegularity(String regularity) {
        if (!regularity.equalsIgnoreCase("T") && !regularity.equalsIgnoreCase("P") && !regularity.equalsIgnoreCase("I") && !regularity.equalsIgnoreCase("3")) {
            throw new IllegalArgumentException("Regularity must be T, P, I or 3.");
        }
        this.regularity = regularity;
    }

    @Override
    public String toString() {
        String dateString = Utils.parsedDateHelper(operationDate.getDayOfMonth() + "/" + operationDate.getMonthValue() + "/" + operationDate.getYear());
        String startTimeString = Utils.parsedTimeHelper(startTime.getHour() + ":" + startTime.getMinute());
        String endTimeString = Utils.parsedTimeHelper(endTime.getHour() + ":" + endTime.getMinute());
        return dateString + "," + sector.getId() + "," + durationInMinutes + "," + startTimeString + "," + endTimeString;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Irrigation other)) return false;
        return this.getSector().equals(other.getSector()) &&
                Utils.dateEquals(this.getOperationDate(), other.getOperationDate()) &&
                this.getDurationInMinutes().equals(other.getDurationInMinutes()) &&
                Utils.timeEquals(this.getStartTime(), other.getStartTime());
    }
}