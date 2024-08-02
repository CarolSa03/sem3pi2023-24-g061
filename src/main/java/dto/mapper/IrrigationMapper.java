package dto.mapper;

import domain.Irrigation.Fertigation;
import domain.Irrigation.Irrigation;
import dto.IrrigationDTO;

import java.util.ArrayList;
import java.util.List;

public class IrrigationMapper {

    public static IrrigationDTO toDTO(Irrigation irrigation) {
        IrrigationDTO irrigationDTO = new IrrigationDTO();
        irrigationDTO.setSector(SectorMapper.toDTO(irrigation.getSector()));
        irrigationDTO.setOperationDate(irrigation.getOperationDate());
        irrigationDTO.setDurationInMinutes(irrigation.getDurationInMinutes());
        irrigationDTO.setStartTime(irrigation.getStartTime());
        irrigationDTO.setEndTime(irrigation.getEndTime());
        if (irrigation instanceof Fertigation) irrigationDTO.setMixId(((Fertigation) irrigation).getMix().getId());
        return irrigationDTO;
    }

    public static Irrigation toEntity(IrrigationDTO irrigationDTO) {
        Irrigation irrigation = new Irrigation();
        irrigation.setSector(SectorMapper.toEntity(irrigationDTO.getSector()));
        irrigation.setOperationDate(irrigationDTO.getOperationDate());
        irrigation.setDurationInMinutes(irrigationDTO.getDurationInMinutes());
        irrigation.setStartTime(irrigationDTO.getStartTime());
        irrigation.setEndTime(irrigationDTO.getEndTime());
        if (irrigationDTO.getMixId() != null) irrigation = new Fertigation(irrigation, irrigationDTO.getMixId());
        return irrigation;
    }

    public static List<IrrigationDTO> toDTOList(List<Irrigation> irrigationList) {
        List<IrrigationDTO> returnList = new ArrayList<>();
        for (Irrigation irrigation : irrigationList) {
            returnList.add(toDTO(irrigation));
        }
        return returnList;
    }

    public static List<Irrigation> toEntityList(List<IrrigationDTO> irrigationDTOList) {
        List<Irrigation> returnList = new ArrayList<>();
        for (IrrigationDTO irrigation : irrigationDTOList) {
            returnList.add(toEntity(irrigation));
        }
        return returnList;
    }

}