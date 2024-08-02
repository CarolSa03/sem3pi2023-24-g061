package domain.BasketDistribution;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HubManagerTest {
    private final BasketDistributionMap basketDistributionMapClass = new BasketDistributionMap();
    private final BasketDistribution basketDistribution = new BasketDistribution();
    private LinkedList<Local> localsList = new LinkedList<>();
    private LinkedList<Distance> distancesList = new LinkedList<>();
    private List<Hub> hubs = new ArrayList<>();
    private Hub hub;
    private Local local;
    @BeforeEach
    void setUp() {
        hub = new Hub(1, "Test", 1.0, 1.0, 1, LocalTime.now(), LocalTime.now());
        local = new Local(1, "Test", 1.0, 1.0);
    }

    @Test
    void getHubsList() {
        String file_locals = "docs/Data/tests_data/locais_test.csv";
        basketDistribution.createLocalsList(new File(file_locals), "docs/Data/tests_data/");
        String file_distances = "docs/Data/tests_data/distancias_test.csv";
        basketDistribution.createDistancesList(new File(file_distances), "docs/Data/tests_data/");

        basketDistributionMapClass.createMap();

        localsList = BasketDistribution.getLocalsList();
        distancesList = BasketDistribution.getDistancesList();

        HubManager.setHubsList(3, new HubComparatorByDefinedCriteria());
        hubs = HubManager.getHubs();
        assertEquals(3, hubs.size());
        hubs.clear();
    }

    @Test
    void localToHub() {
        Hub hub = HubManager.localToHub(local);
        assertEquals(local.getId(), hub.getId());
        assertEquals(local.getDesignation(), hub.getDesignation());
        assertEquals(local.getLat(), hub.getLat());
        assertEquals(local.getLng(), hub.getLng());
        assertEquals(local.getId(), hub.getCollaborators());
    }

    @Test
    void hubToLocal() {
        Local local = HubManager.hubToLocal(hub);
        assertEquals(hub.getId(), local.getId());
        assertEquals(hub.getDesignation(), local.getDesignation());
        assertEquals(hub.getLat(), local.getLat());
        assertEquals(hub.getLng(), local.getLng());
    }
}