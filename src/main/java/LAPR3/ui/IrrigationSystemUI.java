package LAPR3.ui;

import LAPR3.controller.IrrigationSystemController;
import dto.IrrigationDTO;
import dto.IrrigationPlanDTO;
import javafx.util.Pair;
import utils.Utils;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Class Irrigation System UI.
 */
public class IrrigationSystemUI implements Runnable {

    private final IrrigationSystemController controller = new IrrigationSystemController();

    public void run() {

        System.out.println("###Irrigation System Controller Simulation###\n");

        boolean option = Utils.confirm("Do you want to import a file with the irrigation plan? (Yes/No).If so, take in mind that the file must be placed in folder docs/data.\n" +
                "If you want to see the simulation for file irrigationPlanFor30Days.txt, the system will import that information.");

        String fileName = null;
        if (option) {
            fileName = Utils.readLineFromConsole("Please specify the file name: ");
        }

        IrrigationPlanDTO irrigationPlanDTO = controller.getIrrigationPlanDTO(fileName);

        LocalDate irrigationPlanCreationDate = irrigationPlanDTO.getCreationDate();
        int numberOfDays = irrigationPlanDTO.getNumberOfDays();

        LocalDate date = Utils.getCurrentDateToDay();
        LocalTime time = Utils.getCurrentTimeToMinutes();

        boolean specificDateTimeSlot = Utils.confirm("Do you want to check if the system is on for a specific date/time slot? (Yes/No)\nIf so, enter the date and time; else, the system will show you the current status.\n");

        if (specificDateTimeSlot) {
            date = Utils.readDateFromConsole("Enter the date (DD/MM/YYYY):\nPlease take in mind that the irrigation plan is valid for " + numberOfDays + " days, starting " + irrigationPlanCreationDate + ".");
            time = Utils.readTimeFromConsole("Enter the time (HH:MM):\nPlease take in mind that it must be a value between 00:00 and 23:59.");
        } else {
            System.out.println("The system will show you its current status.");
        }

        if (controller.isOn(date, time)) {
            Pair<IrrigationDTO, Integer> currentIrrigation = controller.getCurrentIrrigation(date, time);
            showStatus(date, time, irrigationPlanDTO, currentIrrigation);
        } else {
            System.out.println("Irrigation system is off for the desired date and time.");
            IrrigationDTO nextIrrigation = controller.getNextIrrigation(date, time);
            if (nextIrrigation != null) {
                String dateToPrint = Utils.parsedDateHelper(nextIrrigation.getOperationDate().getDayOfMonth() + "/" + nextIrrigation.getOperationDate().getMonthValue() + "/" + nextIrrigation.getOperationDate().getYear());
                String timeToPrint = Utils.parsedTimeHelper(nextIrrigation.getStartTime().getHour() + ":" + nextIrrigation.getStartTime().getMinute());
                System.out.println("Next irrigation will take place at " + dateToPrint + " at " + timeToPrint + "h in sector " + nextIrrigation.getSector().getId() + ".");
            } else {
                System.out.println("There are no more irrigations scheduled.");
            }
        }

        if (controller.createFile()) {
            System.out.println("\nFile with the irrigation plan created successfully.\nIt can be found at docs/Sprint3/LAPR3/USLP10 under the name irrigationPlanData.csv.");
        } else {
            System.out.println("\nFile with the irrigation plan not created.");
        }

    }

    private void showStatus(LocalDate date, LocalTime time, IrrigationPlanDTO irrigationPlanDTO, Pair<IrrigationDTO, Integer> currentIrrigation) {
        int cycle = 0;
        for (int i = 1; i <= irrigationPlanDTO.getIrrigationsPerDay().get(date).getCycleBegins().size(); i++) {
            LocalTime begin = irrigationPlanDTO.getIrrigationsPerDay().get(date).getCycleBegins().get(i-1);
            LocalTime end = irrigationPlanDTO.getIrrigationsPerDay().get(date).getCycleEnds().get(i-1);
            if (time.isAfter(begin) && time.isBefore(end) || Utils.timeEquals(time, begin) || Utils.timeEquals(time, end)) {
                cycle = i;
                break;
            }
        }
        System.out.println("The system is currently in the " + Utils.getOrdinal(cycle) + " cycle.");
        System.out.printf("Currently irrigated sector: %d.\n", currentIrrigation.getKey().getSector().getId());
        System.out.printf("Minutes to finish irrigation of sector %d: %d minutes.\n", currentIrrigation.getKey().getSector().getId(), currentIrrigation.getValue());
    }

}