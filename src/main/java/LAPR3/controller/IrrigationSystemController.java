package LAPR3.controller;

import BDDAD.dataAccess.repositories.IrrigationSystemRepository;
import BDDAD.dataAccess.repositories.Repositories;
import domain.Irrigation.IrrigationSystem;
import dto.IrrigationDTO;
import dto.IrrigationPlanDTO;
import dto.mapper.IrrigationMapper;
import dto.mapper.IrrigationPlanMapper;
import javafx.util.Pair;
import utils.file.ReadFile;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The type Irrigation system controller.
 */
public class IrrigationSystemController {

    private IrrigationSystemRepository irrigationSystemRepository;
    private final IrrigationSystem irrigationSystem;

    /**
     * Instantiates a new Irrigation system controller.
     */
    public IrrigationSystemController() {
        irrigationSystemRepository = getIrrigationSystemRepository();
        irrigationSystem = irrigationSystemRepository.getIrrigationSystem();
    }

    public IrrigationSystemRepository getIrrigationSystemRepository() {
        if (irrigationSystemRepository == null) {
            irrigationSystemRepository = Repositories.getInstance().getIrrigationSystemRepository();
        }
        return irrigationSystemRepository;
    }

    /**
     * Gets irrigation plan dto.
     *
     * @return the irrigation plan dto
     */
    public IrrigationPlanDTO getIrrigationPlanDTO(String fileName) {
        if (fileName == null) fileName = "irrigationPlanFor30Days.txt";
        return IrrigationPlanMapper.toDTO(irrigationSystem.createIrrigationPlan(ReadFile.readFile("docs/data/", fileName)));
    }

    /**
     * Is on boolean.
     *
     * @param currentDate the current date
     * @param currentTime the current time
     * @return the boolean
     */
    public boolean isOn(LocalDate currentDate, LocalTime currentTime) {
        return irrigationSystem.isOn(currentDate, currentTime);
    }

    /**
     * Gets getCurrentlyIrrigatedSectorDTO.
     *
     * @param date the date
     * @param time the time
     * @return the getCurrentlyIrrigatedSectorDTO
     */
    public Pair<IrrigationDTO, Integer> getCurrentIrrigation(LocalDate date, LocalTime time) {
        return new Pair<>(IrrigationMapper.toDTO(irrigationSystem.getCurrentIrrigation(date, time).getKey()), irrigationSystem.getCurrentIrrigation(date, time).getValue());
    }

    public boolean createFile() {
        return irrigationSystem.createFile();
    }

    public IrrigationDTO getNextIrrigation(LocalDate date, LocalTime time) {
        if (irrigationSystem.getNextIrrigation(date, time) != null) return IrrigationMapper.toDTO(irrigationSystem.getNextIrrigation(date, time));
        return null;
    }
}