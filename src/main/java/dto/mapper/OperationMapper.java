package dto.mapper;

import domain.DataBase.Operation;
import dto.OperationDTO;

import java.util.ArrayList;
import java.util.List;

public class OperationMapper {

    public static OperationDTO toDTO(Operation operation) {
        return new OperationDTO(operation.getOperationId(), operation.getPlotId(), operation.getOperationDate());
    }

    public static Operation toEntity(OperationDTO operationDTO) {
        return new Operation(operationDTO.getId(), operationDTO.getPlotId(), operationDTO.getOperationDate());
    }

    public static List<OperationDTO> toDTOList(List<Operation> operationList) {
        List<OperationDTO> returnList = new ArrayList<>();
        for (Operation operation : operationList) {
            returnList.add(toDTO(operation));
        }
        return returnList;
    }

    public static List<Operation> toEntityList(List<OperationDTO> operationDTOList) {
        List<Operation> returnList = new ArrayList<>();
        for (OperationDTO operation : operationDTOList) {
            returnList.add(toEntity(operation));
        }
        return returnList;
    }

}