package ESINF.controller;

import domain.BasketDistribution.Hub;
import domain.BasketDistribution.HubManager;
import domain.BasketDistribution.InsertHubsFile;

import java.util.List;

public class InsertHubsFileController {
    private final InsertHubsFile insertHubsFile = new InsertHubsFile();
    public List<String> readFile(String filepath) {
        return insertHubsFile.readFile(filepath);
    }

    public void saveHubs(List<String> fileContent) {
        insertHubsFile.saveHubs(fileContent);
    }

    public List<Hub> getHubs() {
        return HubManager.getHubs();
    }
}
