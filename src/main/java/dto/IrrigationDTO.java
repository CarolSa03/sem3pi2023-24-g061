package dto;

import utils.Utils;

import java.time.LocalDate;
import java.time.LocalTime;

public class IrrigationDTO {

    private SectorDTO sector;
    private LocalDate operationDate;
    private Integer durationInMinutes;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer mixId;

    public IrrigationDTO(SectorDTO sector, LocalDate operationDate, Integer durationInMinutes, LocalTime startTime, LocalTime endTime, Integer mixId) {
        this.sector = sector;
        this.operationDate = operationDate;
        this.durationInMinutes = durationInMinutes;
        this.startTime = startTime;
        this.endTime = endTime;
        this.mixId = mixId;
    }

    public IrrigationDTO(SectorDTO sector, LocalDate operationDate, Integer durationInMinutes, LocalTime startTime, LocalTime endTime) {
        this.sector = sector;
        this.operationDate = operationDate;
        this.durationInMinutes = durationInMinutes;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public IrrigationDTO() {
    }

    public SectorDTO getSector() {
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
    public Integer getMixId() {
        return mixId;
    }

    public void setSector(SectorDTO sector) {
        this.sector = sector;
    }

    public void setOperationDate(LocalDate operationDate) {
        this.operationDate = operationDate;
    }

    public void setDurationInMinutes(Integer durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
    public void setMixId(Integer mixId) {
        this.mixId = mixId;
    }

    @Override
    public String toString() {
        String dateString = Utils.parsedDateHelper(operationDate.getDayOfMonth() + "/" + operationDate.getMonthValue() + "/" + operationDate.getYear());
        String startTimeString = Utils.parsedTimeHelper(startTime.getHour() + ":" + startTime.getMinute());
        String endTimeString = Utils.parsedTimeHelper(endTime.getHour() + ":" + endTime.getMinute());
        String returnString = dateString + "," + sector.getId() + "," + durationInMinutes + "," + startTimeString + "," + endTimeString;
        if (mixId != null) returnString += ",mix" + mixId;
        return returnString;
    }

}
