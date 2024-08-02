package BDDAD.ui;

import BDDAD.controller.DatabaseConnectionTestController;
import BDDAD.dataAccess.DatabaseConnection;

import java.sql.SQLException;

public class DatabaseConnectionTestUI implements Runnable {

    private DatabaseConnectionTestController controller;

    public DatabaseConnectionTestUI() {
        controller = new DatabaseConnectionTestController();
    }

    public void run() {
        int result = 0;
        try {
            result = controller.DatabaseConnectionTest();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(result== DatabaseConnection.CONNECTION_SUCCESS)
            System.out.println("Connected to the database.");
        else
            System.out.println("Not connected to the database!");
    }
}
