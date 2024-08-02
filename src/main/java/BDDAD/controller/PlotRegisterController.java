package BDDAD.controller;

import BDDAD.dataAccess.repositories.PlotRepository;
import BDDAD.dataAccess.repositories.Repositories;

import java.sql.SQLException;
import java.util.Objects;

public class PlotRegisterController {

    private PlotRepository plotRepository;

    public PlotRegisterController() {
        plotRepository = getPlotRepository();
    }

    private PlotRepository getPlotRepository() {
        if (Objects.isNull(plotRepository)) {
            Repositories repositories = Repositories.getInstance();
            plotRepository = repositories.getPlotRepository();
        }
        return plotRepository;
    }

    public void plotRegister(Integer plotId, String designation, float area) throws SQLException {
        plotRepository.plotRegister(plotId, designation, area);
    }

}