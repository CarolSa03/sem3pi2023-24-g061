package dto.mapper;

import dto.OperationWeedingDTO;
import domain.DataBase.OperationWeeding;

public class OperationWeedingMapper {
    public static OperationWeedingDTO toDTO(OperationWeeding operationWeeding) {
        return new OperationWeedingDTO(operationWeeding.getPlotId());
    }

    public static OperationWeeding toEntity(OperationWeedingDTO operationWeedingDTO) {
        return new OperationWeeding(operationWeedingDTO.getPlotId());
    }
}
