package BDDAD.controller;

import BDDAD.dataAccess.repositories.Repositories;
import BDDAD.dataAccess.repositories.UnitRepository;
import dto.UnitDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class UnitsListController {

    private UnitRepository unitRepository;

    public UnitsListController() {
        unitRepository = getUnitRepository();
    }

    private UnitRepository getUnitRepository() {
        if (Objects.isNull(unitRepository)) {
            Repositories repositories = Repositories.getInstance();
            unitRepository = repositories.getUnitRepository();
        }
        return unitRepository;
    }

    public List<UnitDTO> getUnits() throws SQLException {
        return unitRepository.getUnits();
    }

    public List<UnitDTO> getQuantityUnits() throws SQLException {
        return unitRepository.getQuantityUnits();
    }

    public List<UnitDTO> getAreaUnits() throws SQLException {
        return unitRepository.getAreaUnits();
    }

    public List<UnitDTO> getTimeUnits() throws SQLException {
        return unitRepository.getTimeUnits();
    }

    public List<UnitDTO> getRatioUnits() throws SQLException {
        return unitRepository.getRatioUnits();
    }

}