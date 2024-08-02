package BDDAD.controller;

import BDDAD.dataAccess.repositories.PlotRepository;
import BDDAD.dataAccess.repositories.Repositories;
import dto.PlotDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class PlotsListController {

    private PlotRepository plotRepository;

    public PlotsListController() {
        plotRepository = getPlotRepository();
    }

    private PlotRepository getPlotRepository() {
        if (Objects.isNull(plotRepository)) {
            Repositories repositories = Repositories.getInstance();
            plotRepository = repositories.getPlotRepository();
        }
        return plotRepository;
    }

    public List<PlotDTO> getPlots() throws SQLException {
        return plotRepository.getPlots();
    }

}
