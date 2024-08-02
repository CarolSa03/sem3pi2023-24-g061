package LAPR3.ui;

import LAPR3.controller.ShowFieldbookController;
import dto.IrrigationDTO;
import main.menu.MainMenuUI;

import java.sql.SQLException;

public class ShowFieldbookUI implements Runnable {

    private final ShowFieldbookController controller;

    public ShowFieldbookUI() {
        controller = new ShowFieldbookController();
    }

    @Override
    public void run() {
        System.out.println("\n###FIELDBOOK CONTENT###\n");
        try {
            for (IrrigationDTO i : controller.getFieldbookIrrigations()) {
                System.out.println(i);
            }
        } catch (SQLException e) {
            System.out.println("Error while reading fieldbook content!\n" + e.getMessage());
        }
        new MainMenuUI().run();
    }
}
