package main;

import BDDAD.dataAccess.DatabaseConnection;
import domain.Irrigation.IrrigationMonitor;
import domain.Irrigation.IrrigationSystem;
import main.menu.MainMenuUI;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        try {
            loadProperties();

            String ipAddress = System.getProperty("database.inet");
            InetAddress.getByName(ipAddress);

            IrrigationMonitor irrigationMonitor = new IrrigationMonitor(new IrrigationSystem().getIrrigationsEnd());
            irrigationMonitor.run();

            MainMenuUI menu = new MainMenuUI();
            menu.run();

            irrigationMonitor.shutDown();
            DatabaseConnection.getInstance().closeConnection();
        } catch (UnknownHostException e) {
            System.out.println("\nDatabase Server not reachable!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadProperties() throws IOException {
        Properties properties = new Properties(System.getProperties());

        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("application.properties");
        properties.load(inputStream);
        inputStream.close();

        System.setProperties(properties);
    }

}
