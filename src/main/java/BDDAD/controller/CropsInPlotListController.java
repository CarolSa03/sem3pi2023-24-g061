package BDDAD.controller;

import BDDAD.dataAccess.repositories.CropRepository;
import BDDAD.dataAccess.repositories.Repositories;
import dto.CropDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class CropsInPlotListController {
    private CropRepository cropRepository = new CropRepository();

    public CropsInPlotListController() {
        cropRepository = getCropRepository();
    }

    private CropRepository getCropRepository() {
        if (Objects.isNull(cropRepository)) {
            Repositories repositories = Repositories.getInstance();
            cropRepository = repositories.getCropRepository();
        }
        return cropRepository;
    }

    public List<CropDTO> getCropsInPlot(Integer plotId) throws SQLException {
        return cropRepository.getCropsInPlot(plotId);
    }
}
