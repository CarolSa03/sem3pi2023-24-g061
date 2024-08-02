package domain.Irrigation;

import domain.DataBase.Sector;
import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.file.ReadFile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class IrrigationSystemTest {

    private IrrigationSystem irrigationSystem;
    private List<String> fileContent;

    @BeforeEach
    void setUp() {
        irrigationSystem = new IrrigationSystem();
        fileContent = ReadFile.readFile("docs/data/", "irrigationPlanFor30Days.txt");
        irrigationSystem.createIrrigationPlan(fileContent);
    }

    @Test
    void testIfOn() {
        LocalDate date = LocalDate.of(2023, 12, 25);
        LocalTime time = LocalTime.of(8, 33);
        boolean expected = true;
        boolean actual = irrigationSystem.isOn(date, time);
        assertEquals(expected, actual);
    }

    @Test
    void testIfOff() {
        LocalDate date = LocalDate.of(2021, 6, 1);
        LocalTime time = LocalTime.of(21, 12);
        boolean expected = false;
        boolean actual = irrigationSystem.isOn(date, time);
        assertEquals(expected, actual);
    }

    @Test
    void getCurrentIrrigationWithoutRegularityRestriction() {
        LocalDate date = LocalDate.of(2023, 12, 21);
        LocalTime time = LocalTime.of(8, 33);
        Pair<Irrigation, Integer> expected = new Pair<>(new Irrigation(new Sector(10), date, 14, LocalTime.of(8, 30), LocalTime.of(8, 44)), 11);
        Pair<Irrigation, Integer> actual = irrigationSystem.getCurrentIrrigation(date, time);
        assertEquals(expected, actual);
    }

    @Test
    void getCurrentIrrigationWithRegularityRestriction() {
        LocalDate date = LocalDate.of(2023, 12, 25);
        LocalTime time = LocalTime.of(9, 12);
        Pair<Irrigation, Integer> expected = new Pair<>(new Irrigation(new Sector(22), date, 25, LocalTime.of(8, 52), LocalTime.of(9, 17)), 5);
        Pair<Irrigation, Integer> actual = irrigationSystem.getCurrentIrrigation(date, time);
        assertEquals(expected, actual);
    }

    @Test
    void checkThatDurationCantBeNegative() {
        fileContent = List.of("8:30,17:00", "10,-14,T", "11,8,T", "21,25,P", "22,25,I", "41,7,T", "42,10,3");
        assertThrowsExactly(IllegalArgumentException.class, () -> irrigationSystem.createIrrigationPlan(fileContent), "Duration in minutes must be a positive integer.");
    }

    @Test
    void checkThatSecondCycleBeginCantBeBeforeFirstCycleEnd() {
        fileContent = List.of("8:30,9:00", "10,14,T", "11,8,T", "21,25,P", "22,25,I", "41,7,T", "42,10,3");
        assertThrowsExactly(IllegalArgumentException.class, () -> irrigationSystem.createIrrigationPlan(fileContent), "Cycle's begin must be after previous cycle's end.");
    }

    @Test
    void checkThatSecondCycleBeginCantBeBeforeFirstCycleBegin() {
        fileContent = List.of("8:30,8:00", "10,14,T", "11,8,T", "21,25,P", "22,25,I", "41,7,T", "42,10,3");
        assertThrowsExactly(IllegalArgumentException.class, () -> irrigationSystem.createIrrigationPlan(fileContent), "Cycle's begin must be after previous cycle's begin.");
    }

}