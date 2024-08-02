package BDDAD.controller;

import BDDAD.dataAccess.repositories.PlotRepository;
import BDDAD.dataAccess.repositories.Repositories;

import java.sql.SQLException;
import java.util.Objects;

public class PlotDeleteController {

    private PlotRepository plotRepository;

    public PlotDeleteController() {
        plotRepository = getPlotRepository();
    }

    private PlotRepository getPlotRepository() {
        if (Objects.isNull(plotRepository)) {
            Repositories repositories = Repositories.getInstance();
            plotRepository = repositories.getPlotRepository();
        }
        return plotRepository;
    }

    public int plotDelete(Integer plotId) throws SQLException {
        return plotRepository.plotDelete(plotId);
    }

}
