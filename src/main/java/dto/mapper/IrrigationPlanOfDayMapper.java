package dto.mapper;

import domain.Irrigation.IrrigationPlanOfDay;
import dto.IrrigationPlanOfDayDTO;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

/**
 * The type Irrigation plan of day mapper.
 */
public class IrrigationPlanOfDayMapper {
    /**
     * To dto irrigation plan dto.
     *
     * @param irrigationPlan the irrigation plan
     * @return the irrigation plan dto
     */
    public static IrrigationPlanOfDayDTO toDTO(IrrigationPlanOfDay irrigationPlan) {
        return new IrrigationPlanOfDayDTO(irrigationPlan.getCycleBegins(), irrigationPlan.getCycleEnds(),
                IrrigationMapper.toDTOList(irrigationPlan.getIrrigationlist()));
    }

    /**
     * To entity irrigation plan.
     *
     * @param irrigationPlanDTO the irrigation plan dto
     * @return the irrigation plan
     */
    public static IrrigationPlanOfDay toEntity(IrrigationPlanOfDayDTO irrigationPlanDTO) {
        return new IrrigationPlanOfDay(irrigationPlanDTO.getCycleBegins(), irrigationPlanDTO.getCycleEnds(),
                IrrigationMapper.toEntityList(irrigationPlanDTO.getIrrigationList()));
    }

    /**
     * To dto map map.
     *
     * @param map the map
     * @return the map
     */
    public static Map<LocalDate, IrrigationPlanOfDayDTO> toDTOMap(Map<LocalDate, IrrigationPlanOfDay> map) {
        Map<LocalDate, IrrigationPlanOfDayDTO> mapDTO = new TreeMap<>(LocalDate::compareTo);
        for (Map.Entry<LocalDate, IrrigationPlanOfDay> entry : map.entrySet()) {
            mapDTO.put(entry.getKey(), toDTO(entry.getValue()));
        }
        return mapDTO;
    }

    /**
     * To entity map map.
     *
     * @param mapDTO the map dto
     * @return the map
     */
    public static Map<LocalDate, IrrigationPlanOfDay> toEntityMap(Map<LocalDate, IrrigationPlanOfDayDTO> mapDTO) {
        Map<LocalDate, IrrigationPlanOfDay> map = new TreeMap<>(LocalDate::compareTo);
        for (Map.Entry<LocalDate, IrrigationPlanOfDayDTO> entry : mapDTO.entrySet()) {
            map.put(entry.getKey(), toEntity(entry.getValue()));
        }
        return map;
    }

}
