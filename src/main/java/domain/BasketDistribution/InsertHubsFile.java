package domain.BasketDistribution;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

public class InsertHubsFile {

    private static final LinkedList<Local> localsList = BasketDistribution.getLocalsList();

    /**
     * This method reads the file and returns the content.
     *
     * @param file the file
     * @return the content
     */
    public List<String> readFile(String file) { // O(n)
        List<String> content;

        if (file == null || file.isEmpty()) {
            String path = "docs/Data/";
            content = utils.file.ReadFile.readFile(path, "hubs.csv"); // O(n)
        } else {
            content = utils.file.ReadFile.readFile(file, ""); // O(n)
        }

        return content;
    }


    /**
     * This method saves the hubs in the hubs list.
     *
     * @param content the content
     */
    public void saveHubs(List<String> content) { // O(n * m)
        HubManager.getHubs().clear(); // O(1)

        for (String line : content) { // O(m)
            String[] data = line.split(",");

            Hub hub = new Hub();

            hub.setDesignation(data[0]);
            hub.setId(Integer.parseInt(data[0].substring(2)));
            hub.setCollaborators(hub.getId());
            hub.setOperatingHoursStart(LocalTime.parse(data[1]));
            hub.setOperatingHoursEnd(LocalTime.parse(data[2]));

            for (Local local : localsList) { // O(n)
                if (local.getDesignation().equals(hub.getDesignation())) {
                    hub.setLat(local.getLat());
                    hub.setLng(local.getLng());
                    break;
                }
            }

            HubManager.getHubs().add(hub);
        }
    }
}
