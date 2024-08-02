package dto.mapper;

import domain.Irrigation.IrrigationPlan;
import dto.IrrigationPlanDTO;

/**
 * The type Irrigation plan mapper.
 */
public class IrrigationPlanMapper {
    /**
     * To dto irrigation plan dto.
     *
     * @param irrigationPlan the irrigation plan
     * @return the irrigation plan dto
     */
    public static IrrigationPlanDTO toDTO(IrrigationPlan irrigationPlan) {
        return new IrrigationPlanDTO(irrigationPlan.getCreationDate(), irrigationPlan.getNumberOfDays(),
                IrrigationPlanOfDayMapper.toDTOMap(irrigationPlan.getIrrigationsPerDay()));
    }

    /**
     * To entity irrigation plan.
     *
     * @param irrigationPlanDTO the irrigation plan dto
     * @return the irrigation plan
     */
    public static IrrigationPlan toEntity(IrrigationPlanDTO irrigationPlanDTO) {
        return new IrrigationPlan(IrrigationPlanOfDayMapper.toEntityMap(irrigationPlanDTO.getIrrigationsPerDay()));
    }
}
