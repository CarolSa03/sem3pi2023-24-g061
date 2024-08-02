package BDDAD.ui;

import BDDAD.controller.UnitsListController;
import dto.UnitDTO;

import java.sql.SQLException;
import java.util.List;

public class UnitsListUI implements Runnable {
    private final UnitsListController controller;

    public UnitsListUI() {
        controller = new UnitsListController();
    }

    public void run() {
        try {
            System.out.println("List of Units");
            List<UnitDTO> units = getUnits();
            System.out.printf("%-13s %-15s %-13s\n","Unit Id","Designation","Type of Unit");
            for (UnitDTO u: units) {
                System.out.printf("%-13d %-15s %-13s\n", u.getId(), u.getDesignation(), u.getUnitType());
            }
        } catch (SQLException e) {
            System.out.println("Database not return the units list!\n" + e.getMessage());
        }
    }

    public void runQuantityUnits() {
        try {
            System.out.println("List of Quantity Units");
            List<UnitDTO> units = getQuantityUnits();
            System.out.printf("%-13s %-15s\n","UnitId","Designation");
            for (UnitDTO u: units) {
                System.out.printf("%-13d %-15s\n", u.getId(), u.getDesignation());
            }
        } catch (SQLException e) {
            System.out.println("Database not return the quantity units list!\n" + e.getMessage());
        }
    }

    public void runAreaUnits() {
        try {
            System.out.println("List of Area Units");
            List<UnitDTO> units = getAreaUnits();
            System.out.printf("%-13s %-15s\n","UnitId","Designation");
            for (UnitDTO u: units) {
                System.out.printf("%-13d %-15s\n", u.getId(), u.getDesignation());
            }
        } catch (SQLException e) {
            System.out.println("Database not return the area units list!\n" + e.getMessage());
        }
    }

    public void runTimeUnits() {
        try {
            System.out.println("List of Time Units");
            List<UnitDTO> units = getTimeUnits();
            System.out.printf("%-13s %-15s\n","UnitId","Designation");
            for (UnitDTO u: units) {
                System.out.printf("%-13d %-15s\n", u.getId(), u.getDesignation());
            }
        } catch (SQLException e) {
            System.out.println("Database not return the time units list!\n" + e.getMessage());
        }
    }

    public void runRatioUnits() {
        try {
            System.out.println("List of Ratio Units");
            List<UnitDTO> units = getRatioUnits();
            System.out.printf("%-13s %-15s\n","UnitId","Designation");
            for (UnitDTO u: units) {
                System.out.printf("%-13d %-15s\n", u.getId(), u.getDesignation());
            }
        } catch (SQLException e) {
            System.out.println("Database not return the ratio units list!\n" + e.getMessage());
        }
    }

    public List<UnitDTO> getUnits() throws SQLException {
        return controller.getUnits();
    }

    public List<UnitDTO> getQuantityUnits() throws SQLException {
        return controller.getQuantityUnits();
    }

    public List<UnitDTO> getAreaUnits() throws SQLException {
        return controller.getAreaUnits();
    }

    public List<UnitDTO> getTimeUnits() throws SQLException {
        return controller.getTimeUnits();
    }

    public List<UnitDTO> getRatioUnits() throws SQLException {
        return controller.getRatioUnits();
    }

}
