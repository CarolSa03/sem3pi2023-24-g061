package ESINF.ui;

import ESINF.controller.InsertHubsFileController;
import domain.BasketDistribution.Hub;
import utils.Utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class InsertHubsFileUI implements Runnable {

    private static final String FILE_DEFAULT = "docs/Data/hubs_default.csv";
    private static final InsertHubsFileController controller = new InsertHubsFileController();

    @Override
    public void run() {
        System.out.println("Insert Hubs\n");

        LinkedList<String> options = new LinkedList<>();

        options.add("Default File");
        options.add("Custom File");

        List<String> fileContent = new ArrayList<>();

        switch (Utils.showAndSelectIndex(options, "Select File")) {
            case 0:
                System.out.println("Default File Selected");
                fileContent = controller.readFile(FILE_DEFAULT);
                break;
            case 1:
                System.out.println("Custom File Selected");
                String filePath = Utils.readLineFromConsole("Insert File Path: ");
                fileContent = controller.readFile(filePath);
                break;
        }

        controller.saveHubs(fileContent);

        List<Hub> hubs = controller.getHubs();

        if (hubs.isEmpty()) System.out.println("\nNo Hubs inserted!");
        else System.out.println("\n" + hubs.size() + " Hubs inserted successfully!");

        for (Hub hub : hubs) {
            System.out.println(hub.toString() + " - " + hub.getId());
        }
    }
}
