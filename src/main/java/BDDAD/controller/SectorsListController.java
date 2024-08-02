package BDDAD.controller;

import BDDAD.dataAccess.repositories.Repositories;
import BDDAD.dataAccess.repositories.SectorRepository;
import dto.SectorDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class SectorsListController {

    private SectorRepository sectorRepository;

    public SectorsListController() {
        sectorRepository = getSectorRepository();
    }

    private SectorRepository getSectorRepository() {
        if (Objects.isNull(sectorRepository)) {
            Repositories repositories = Repositories.getInstance();
            sectorRepository = repositories.getSectorRepository();
        }
        return sectorRepository;
    }

    public List<SectorDTO> getSectors() throws SQLException {
        return sectorRepository.getSectors();
    }

}
