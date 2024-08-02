package LAPR3.ui;

import LAPR3.controller.WriteInFieldbookController;
import dto.IrrigationDTO;
import utils.Utils;

import java.time.LocalDate;
import java.time.LocalTime;

public class WriteInFieldbookUI implements Runnable {

    private final WriteInFieldbookController controller = new WriteInFieldbookController();

    @Override
    public void run() {
        try {
            System.out.println("\n###FIELDBOOK###");

            String currentDate = Utils.parsedDateHelper(LocalDate.now().getDayOfMonth() + "/" + LocalDate.now().getMonthValue() + "/" + LocalDate.now().getYear());
            String currentTime = Utils.parsedTimeHelper(LocalTime.now().getHour() + ":" + LocalTime.now().getMinute()) + "h";

            System.out.printf("Current Date and Time: %s, %s\n", currentDate, currentTime);

            IrrigationDTO irrigation = controller.getFinishedOperation();

            System.out.printf("An operation just ended in your irrigation system!\n%s\n", irrigation);

            if (controller.registerInFieldbook(irrigation)) {
                System.out.println("\nIrrigation operation successfully written in fieldbook!\n");
                new ShowFieldbookUI().run();
            }
        } catch (Exception e) {
            System.out.println("\nIrrigation operation not written in fieldbook!\n");
            e.printStackTrace();
        }
    }

}
