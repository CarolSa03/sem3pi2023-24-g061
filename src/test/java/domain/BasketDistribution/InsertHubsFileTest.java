package domain.BasketDistribution;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InsertHubsFileTest {

    private final InsertHubsFile insertHubsFile = new InsertHubsFile();
    private final List<String> contentExpected = new ArrayList<>();
    private final List<Hub> hubsExpected = new ArrayList<>();
    private List<Hub> hubs;

    @BeforeEach
    void setUp() {
        Hub hub1 = new Hub(1, "CT1", 39.4667, -8.2, 0,
                LocalTime.of(14, 0), LocalTime.of(17, 0));
        Hub hub2 = new Hub(214, "CT214", 40.0333, -8.3833, 0,
                LocalTime.of(11, 0), LocalTime.of(15, 30));

        contentExpected.add("CT1,14:00,17:00");
        contentExpected.add("CT214,11:00,15:30");
        hubsExpected.add(hub1);
        hubsExpected.add(hub2);

        hubs = HubManager.getHubs();
    }

    @Test
    void readFile() {
        String fileHubs = "docs/Data/hubs_default.csv";
        List<String> content = insertHubsFile.readFile(fileHubs);
        assertEquals(contentExpected, content);
    }

    @Test
    void saveHubs() {
        insertHubsFile.saveHubs(contentExpected);

        assertEquals(hubsExpected.size(), hubs.size(), "Hubs list size should match expected size");

        for (int i = 0; i < hubsExpected.size(); i++) {
            Hub expected = hubsExpected.get(i);
            Hub actual = hubs.get(i);

            assertEquals(expected.getDesignation(), actual.getDesignation(), "Hub designation should match");
            assertEquals(expected.getOperatingHoursStart(), actual.getOperatingHoursStart(), "Operating hours start should match");
            assertEquals(expected.getOperatingHoursEnd(), actual.getOperatingHoursEnd(), "Operating hours end should match");
        }

        hubs.clear();
    }
}