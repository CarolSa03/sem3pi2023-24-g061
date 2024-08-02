package BDDAD.controller;

import BDDAD.dataAccess.repositories.FertilizationModeRepository;
import BDDAD.dataAccess.repositories.Repositories;
import dto.FertilizationModeDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class FertilizationModesListController {

    private FertilizationModeRepository fertilizationModeRepository;

    public FertilizationModesListController() {
        fertilizationModeRepository = getFertilizationModeRepository();
    }

    private FertilizationModeRepository getFertilizationModeRepository() {
        if (Objects.isNull(fertilizationModeRepository)) {
            Repositories repositories = Repositories.getInstance();
            fertilizationModeRepository = repositories.getFertilizationModeRepository();
        }
        return fertilizationModeRepository;
    }

    public List<FertilizationModeDTO> getFertilizationModes() throws SQLException {
        return fertilizationModeRepository.getFertilizationModes();
    }
}
