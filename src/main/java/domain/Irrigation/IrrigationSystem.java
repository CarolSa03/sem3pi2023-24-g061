package domain.Irrigation;

import BDDAD.dataAccess.repositories.IrrigationRepository;
import domain.DataBase.Sector;
import javafx.util.Pair;
import utils.Utils;
import utils.file.CreateFile;
import utils.file.ReadFile;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * The type Irrigation system.
 */
public class IrrigationSystem {

    private IrrigationPlan irrigationPlan;

    /**
     * Instantiates a new Irrigation system.
     */
    public IrrigationSystem() {
        getIrrigationPlan();
        createIrrigationPlan(ReadFile.readFile("docs/data/", "irrigationPlanFor30Days.txt"));
    }

    /**
     * Gets irrigation plan.
     *
     * @return the irrigation plan
     */
    public IrrigationPlan getIrrigationPlan() {
        if (irrigationPlan == null) irrigationPlan = new IrrigationPlan();
        return irrigationPlan;
    }

    /**
     * Create irrigation plan.
     *
     * @param fileContent the file content
     * @return the irrigation plan
     */
    public IrrigationPlan createIrrigationPlan(List<String> fileContent) {

        LocalDate date = irrigationPlan.getCreationDate();

        String line1 = fileContent.get(0);
        String[] splitLine1 = line1.split(",");

        Map<LocalDate, IrrigationPlanOfDay> irrigations = new TreeMap<>(LocalDate::compareTo);

        for (int i = 0; i < irrigationPlan.getNumberOfDays(); i++) {

            IrrigationPlanOfDay irrigationPlanOfDay = new IrrigationPlanOfDay();

            for (String s : splitLine1) {

                LocalTime begin = LocalTime.parse(Utils.parsedTimeHelper(s), DateTimeFormatter.ofPattern("HH:mm"));
                irrigationPlanOfDay.addCycleBegin(begin);
                LocalTime end = begin;

                for (String line : fileContent.subList(1, fileContent.size())) {

                    String[] split = line.split(",");

                    Sector sector = new Sector(Integer.parseInt(split[0]));
                    Integer durationInMinutes = Integer.parseInt(split[1]);
                    if (durationInMinutes <= 0) {
                        throw new IllegalArgumentException("Duration in minutes must be a positive integer.");
                    }
                    String regularity = split[2];

                    if (checkIfOnInDay(date, regularity)) {
                        end = end.plusMinutes(durationInMinutes);
                        if (isFertigation(split)) {
                            Integer mixId = Integer.parseInt(split[3].substring(3));
                            Integer mixRecurrence = Integer.parseInt(split[4]);
                            if (checkIfFertigationInDay(date, mixRecurrence)) {
                                irrigationPlanOfDay.getIrrigationlist().add(new Fertigation(sector, date, durationInMinutes, begin, end, regularity, mixId, mixRecurrence));
                            } else {
                                irrigationPlanOfDay.getIrrigationlist().add(new Irrigation(sector, date, durationInMinutes, begin, end, regularity));
                            }
                        } else {
                            irrigationPlanOfDay.getIrrigationlist().add(new Irrigation(sector, date, durationInMinutes, begin, end, regularity));
                        }
                        begin = end;
                    } else if (isFertigation(split) && checkIfFertigationInDay(date, Integer.parseInt(split[4]))) {
                        String dateString = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                        throw new IllegalArgumentException("Fertigation is not possible in this day (" + dateString + ").\nThe file provided is not valid and should be corrected.");
                    }

                }

                irrigationPlanOfDay.addCycleEnd(end);

            }

            irrigationPlanOfDay.getIrrigationlist().sort(Comparator.comparing(Irrigation::getStartTime));

            irrigations.put(date, irrigationPlanOfDay);
            date = date.plusDays(1);

        }

        return irrigationPlan = new IrrigationPlan(irrigations);

    }

    /**
     * Is on boolean.
     * Comparator.comparing(Irrigation::getOperationDate).thenComparing(Irrigation::getStartTime)
     *
     * @param date the date
     * @param time the time
     * @return the boolean
     */
    public boolean isOn(LocalDate date, LocalTime time) {

        if (date.isBefore(irrigationPlan.getCreationDate()) || date.isAfter(irrigationPlan.getCreationDate().plusDays(irrigationPlan.getNumberOfDays()))) {
            return false;
        }

        Map<LocalDate, IrrigationPlanOfDay> irrigationsPerDay = irrigationPlan.getIrrigationsPerDay();

        if (!irrigationsPerDay.containsKey(date)) {
            return false;
        } else {
            IrrigationPlanOfDay irrigationPlanOfDay = irrigationsPerDay.get(date);
            if (irrigationPlanOfDay.getIrrigationlist().isEmpty()) {
                return false;
            } else {
                for (Irrigation irrigation : irrigationPlanOfDay.getIrrigationlist()) {
                    if (irrigation.getStartTime().isBefore(time) || Utils.timeEquals(irrigation.getStartTime(), time)) {
                        if (irrigation.getEndTime().isAfter(time) || Utils.timeEquals(irrigation.getEndTime(), time)) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;

    }

    private boolean checkIfOnInDay(LocalDate date, String regularity) {
        if (regularity.equalsIgnoreCase("T")) return true;
        if (regularity.equalsIgnoreCase("P") && date.getDayOfMonth() % 2 == 0) return true;
        if (regularity.equalsIgnoreCase("I") && date.getDayOfMonth() % 2 != 0) return true;
        if (regularity.equalsIgnoreCase("3") && date.getDayOfMonth() % 3 == 0) return true;
        return false;
    }

    private boolean checkIfFertigationInDay(LocalDate date, Integer mixRecurrence) {
        int daysSinceFirst = (int) ChronoUnit.DAYS.between(irrigationPlan.getCreationDate(), date);
        return daysSinceFirst % (mixRecurrence) == 0 || daysSinceFirst == 0;
    }

    /**
     * Gets sector and minutes pair.
     *
     * @param date the date
     * @param time the time
     * @return the sector and minutes pair
     */
    public Pair<Irrigation, Integer> getCurrentIrrigation(LocalDate date, LocalTime time) {

        if (!isOn(date, time)) return null;

        IrrigationPlanOfDay irrigationPlanOfDay = irrigationPlan.getIrrigationsPerDay().get(date);
        Irrigation irrigation;

        for (int i = 0; i < irrigationPlanOfDay.getIrrigationlist().size(); i++) {

            irrigation = irrigationPlanOfDay.getIrrigationlist().get(i);

            if (!checkIfOnInDay(date, irrigation.getRegularity())) continue;

            if (time.isAfter(irrigation.getStartTime()) && time.isBefore(irrigation.getEndTime())) {
                return new Pair<>(irrigation, (int) Duration.between(time, irrigation.getEndTime()).toMinutes());
            } else if (Utils.timeEquals(time, irrigation.getEndTime())) {
                return new Pair<>(irrigation, 0);
            }

        }

        return null;

    }

    private static boolean isFertigation(String[] split) {
        return split.length == 5 && split[3].startsWith("m");
    }

    /**
     * End of irrigation boolean.
     *
     * @return the boolean
     */
    public boolean endOfIrrigation() {
        for (LocalDateTime dateTime : getIrrigationsEnd()) {
            if (Utils.isTodayAndNow(dateTime.toLocalDate(), dateTime.toLocalTime())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Create file boolean.
     *
     * @return the boolean
     */
    public boolean createFile() {

        String filePath = "docs/Sprint3/LAPR3/USLP10/";
        String fileName = "IrrigationPlanData.csv";

        List<String> content = new ArrayList<>();

        content.add("Dia,Sector,Duração,Início,Final");

        for (Map.Entry<LocalDate, IrrigationPlanOfDay> entry : irrigationPlan.getIrrigationsPerDay().entrySet()) {
            LocalDate date = entry.getKey();
            for (Irrigation irrigation : irrigationPlan.getIrrigationsPerDay().get(date).getIrrigationlist()) {
                content.add(irrigation.toString());
            }
        }

        return CreateFile.createFile(filePath, fileName, content);

    }

    /**
     * Gets finished operation.
     *
     * @return the finished operation
     */
    public Irrigation getFinishedOperation() {
        if (getCurrentIrrigation(Utils.getCurrentDateToDay(), Utils.getCurrentTimeToMinutes()).getValue() == 0) return getCurrentIrrigation(Utils.getCurrentDateToDay(), Utils.getCurrentTimeToMinutes()).getKey();
        return null;
    }

    /**
     * Gets irrigations end.
     *
     * @return the irrigations end
     */
    public List<LocalDateTime> getIrrigationsEnd() {
        List<LocalDateTime> irrigationsEnd = new ArrayList<>();
        for (Map.Entry<LocalDate, IrrigationPlanOfDay> entry : irrigationPlan.getIrrigationsPerDay().entrySet()) {
            LocalDate date = entry.getKey();
            for (Irrigation irrigation : irrigationPlan.getIrrigationsPerDay().get(date).getIrrigationlist()) {
                irrigationsEnd.add(LocalDateTime.of(irrigation.getOperationDate(), irrigation.getEndTime()));
            }
        }
        return irrigationsEnd;
    }

    /**
     * Gets next irrigation.
     *
     * @param date the date
     * @param time the time
     * @return the next irrigation
     */
    public Irrigation getNextIrrigation(LocalDate date, LocalTime time) {
        for (Irrigation irrigation : irrigationPlan.getIrrigationsPerDay().get(date).getIrrigationlist()) {
            if (irrigation.getStartTime().isBefore(time) || Utils.timeEquals(irrigation.getStartTime(), time)) continue;
            return irrigation;
        }
        if (irrigationPlan.getIrrigationsPerDay().get(date.plusDays(1)).getIrrigationlist() != null) return irrigationPlan.getIrrigationsPerDay().get(date.plusDays(1)).getIrrigationlist().get(0);
        return null;
    }

    public List<Irrigation> getIrrigationsSinceLast(Irrigation last, Irrigation irrigation) throws SQLException {
        List<Irrigation> irrigationsSinceLast = new ArrayList<>();
        for (Map.Entry<LocalDate, IrrigationPlanOfDay> entry : irrigationPlan.getIrrigationsPerDay().entrySet()) {
            LocalDate date = entry.getKey();
            if (date.isBefore(last.getOperationDate())) continue;
            if (Utils.dateEquals(date, last.getOperationDate())) {
                for (Irrigation i : entry.getValue().getIrrigationlist()) {
                    if (i.getEndTime().isBefore(last.getEndTime()) || Utils.timeEquals(i.getEndTime(), last.getEndTime())) continue;
                    i.setId(Utils.getNextOperationId());
                    irrigationsSinceLast.add(i);
                }
                continue;
            }
            if (date.isAfter(last.getOperationDate()) && (date.isBefore(irrigation.getOperationDate()))) {
                for (Irrigation i : entry.getValue().getIrrigationlist()) {
                    i.setId(Utils.getNextOperationId());
                    irrigationsSinceLast.add(i);
                }
                continue;
            }
            if(Utils.dateEquals(date, irrigation.getOperationDate())) {
                for (Irrigation i : entry.getValue().getIrrigationlist()) {
                    if (i.getEndTime().isAfter(irrigation.getEndTime())) break;
                    i.setId(Utils.getNextOperationId());
                    irrigationsSinceLast.add(i);
                }
                break;
            }
        }
        return irrigationsSinceLast;
    }
}