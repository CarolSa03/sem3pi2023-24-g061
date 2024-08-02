package domain.BasketDistribution;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.file.ReadFile;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BasketDistributionTest {

    File fileDistanciasTest;
    File fileLocaisTest;
    List<String> distanciasTest;
    List<String> locaisTest;
    LinkedList<Local> localLinkedList;
    LinkedList<Local> expectedLocalLinkedList;
    LinkedList<Distance> distanceLinkedList;
    LinkedList<Distance> expectedDistanceLinkedList;

    @BeforeEach
    void setUp() {
        fileDistanciasTest = new File("docs/Data/tests_data/distancias_test.csv");
        fileLocaisTest = new File("docs/Data/tests_data/locais_test.csv");

        distanciasTest = ReadFile.readFile(fileDistanciasTest.getPath(), "");
        locaisTest = ReadFile.readFile(fileLocaisTest.getPath(), "");

        expectedLocalLinkedList = new LinkedList<>();
        expectedLocalLinkedList.add(new Local(1, "CT1", -10, 0.01));
        expectedLocalLinkedList.add(new Local(2, "CT2", -10, 0.020));
        expectedLocalLinkedList.add(new Local(3, "CT3", 0, 0));
        expectedLocalLinkedList.add(new Local(4, "CT4", 10, -0.01));
        expectedLocalLinkedList.add(new Local(5, "CT5", 20, -0.02));

        expectedDistanceLinkedList = new LinkedList<>();
        expectedDistanceLinkedList.add(new Distance("CT1", "CT2", 0.01));
        expectedDistanceLinkedList.add(new Distance("CT1", "CT3", 0.015));
        expectedDistanceLinkedList.add(new Distance("CT2", "CT1", 0.01));
        expectedDistanceLinkedList.add(new Distance("CT3", "CT4", 0.05));
        expectedDistanceLinkedList.add(new Distance("CT2", "CT3", 0.025));
        expectedDistanceLinkedList.add(new Distance("CT4", "CT3", 0.05));
        expectedDistanceLinkedList.add(new Distance("CT5", "CT1", 0.01));
        expectedDistanceLinkedList.add(new Distance("CT1", "CT5", 0.01));
    }

    @Test
    void getDistancesList() {
        BasketDistribution basketDistribution = new BasketDistribution();
        basketDistribution.createDistancesList(fileDistanciasTest, "docs/Data/tests_data/");
        distanceLinkedList = BasketDistribution.getDistancesList();
        assertEquals(expectedDistanceLinkedList, distanceLinkedList);
    }

    @Test
    void getLocalsList() {
        BasketDistribution basketDistribution = new BasketDistribution();
        basketDistribution.createLocalsList(fileLocaisTest, "docs/Data/tests_data/");
        localLinkedList = BasketDistribution.getLocalsList();
        assertEquals(expectedLocalLinkedList, localLinkedList);
    }
}