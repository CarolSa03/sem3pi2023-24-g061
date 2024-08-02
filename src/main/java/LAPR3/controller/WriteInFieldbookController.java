package LAPR3.controller;

import BDDAD.dataAccess.repositories.IrrigationRepository;
import BDDAD.dataAccess.repositories.IrrigationSystemRepository;
import BDDAD.dataAccess.repositories.Repositories;
import domain.DataBase.Fieldbook;
import domain.Irrigation.IrrigationSystem;
import dto.IrrigationDTO;
import dto.mapper.IrrigationMapper;

import java.sql.SQLException;

public class WriteInFieldbookController {

    private final IrrigationSystem irrigationSystem;
    private IrrigationSystemRepository irrigationSystemRepository;
    private IrrigationRepository irrigationRepository;

    public WriteInFieldbookController() {
        irrigationSystemRepository = getIrrigationSystemRepository();
        irrigationSystem = irrigationSystemRepository.getIrrigationSystem();
        irrigationRepository = getIrrigationRepository();
    }

    public IrrigationSystemRepository getIrrigationSystemRepository() {
        if (irrigationSystemRepository == null) {
            irrigationSystemRepository = Repositories.getInstance().getIrrigationSystemRepository();
        }
        return irrigationSystemRepository;
    }

    public IrrigationRepository getIrrigationRepository() {
        if (irrigationRepository == null) {
            irrigationRepository = Repositories.getInstance().getIrrigationRepository();
        }
        return irrigationRepository;
    }

    public boolean registerInFieldbook(IrrigationDTO irrigationDTO) throws SQLException {
        return Fieldbook.registerInFieldbook(getIrrigationRepository(), irrigationSystem, IrrigationMapper.toEntity(irrigationDTO));
    }

    public IrrigationDTO getFinishedOperation() {
        return IrrigationMapper.toDTO(irrigationSystem.getFinishedOperation());
    }

}