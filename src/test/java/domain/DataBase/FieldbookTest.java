package domain.DataBase;

import BDDAD.dataAccess.DatabaseConnection;
import BDDAD.dataAccess.repositories.IrrigationRepository;
import BDDAD.dataAccess.repositories.IrrigationSystemRepository;
import BDDAD.dataAccess.repositories.Repositories;
import domain.Irrigation.Fertigation;
import domain.Irrigation.Irrigation;
import domain.Irrigation.IrrigationSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.file.ReadFile;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FieldbookTest {

    private static final String fieldbookPath = "docs/Sprint3/LAPR3/USLP11/";
    private static final String fieldbookFile = "fieldbook.csv";
    private Irrigation irrigation;
    private IrrigationRepository irrigationRepository;
    private IrrigationSystem irrigationSystem;
    private DatabaseConnection db;

    @BeforeEach
    void setUp() throws SQLException {
        db = new DatabaseConnection();
        db.getConnection();
        irrigation = new Fertigation(new Sector(10), LocalDate.of(2024, 1, 12), 14, LocalTime.of(8, 30), LocalTime.of(8, 44), "T", 10, 7);
        irrigationRepository = Repositories.getInstance().getIrrigationRepository();
        IrrigationSystemRepository irrigationSystemRepository = Repositories.getInstance().getIrrigationSystemRepository();
        irrigationSystem = irrigationSystemRepository.getIrrigationSystem();
    }

    @Test
    void writeOneIrrigationInFieldbook() throws SQLException {

        boolean expected = true;
        boolean actual = Fieldbook.writeInFieldbook(irrigation);

        String expectedString = "12/01/2024,10,14,08:30,08:44,mix10";
        String actualString = ReadFile.readFile(fieldbookPath, fieldbookFile).getLast();

        assertEquals(expected, actual);
        assertEquals(expectedString, actualString);

        db.closeConnection();

    }

    @Test
    void registerIrrigationsSinceLastInFieldbook() throws SQLException {

        List<Irrigation> irrigationsSinceLast = irrigationSystem.getIrrigationsSinceLast(irrigationRepository.getLastIrrigationFromFieldbook(), irrigation);

        boolean expected = true;
        boolean actual = Fieldbook.registerInFieldbook(irrigationRepository, irrigationSystem, irrigation);

        assertEquals(expected, actual);

        List<String> fileContent = ReadFile.readFile(fieldbookPath, fieldbookFile);
        int j = fileContent.size() - irrigationsSinceLast.size() - 1;
        for (Irrigation i : irrigationsSinceLast) {
            String expectedString = i.toString();
            String actualString = fileContent.get(fileContent.size() - j - 1);
            assertEquals(expectedString, actualString);
            j--;
        }

        db.closeConnection();

    }

}