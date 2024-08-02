package LAPR3.controller;

import BDDAD.dataAccess.repositories.IrrigationRepository;
import BDDAD.dataAccess.repositories.Repositories;
import dto.IrrigationDTO;
import dto.mapper.IrrigationMapper;

import java.sql.SQLException;
import java.util.List;

public class ShowFieldbookController {

    private IrrigationRepository irrigationRepository;

    public ShowFieldbookController() {
        irrigationRepository = getIrrigationRepository();
    }

    public IrrigationRepository getIrrigationRepository() {
        if (irrigationRepository == null) {
            irrigationRepository = Repositories.getInstance().getIrrigationRepository();
        }
        return irrigationRepository;
    }
    public List<IrrigationDTO> getFieldbookIrrigations() throws SQLException {
        return IrrigationMapper.toDTOList(irrigationRepository.getFieldbookIrrigations());
    }

}
