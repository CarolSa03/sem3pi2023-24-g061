package dto.mapper;

import domain.DataBase.Sector;
import dto.SectorDTO;
import java.util.ArrayList;
import java.util.List;

public class SectorMapper {

    public static SectorDTO toDTO(Sector sector) {
        return new SectorDTO(sector.getId());
    }

    public static Sector toEntity(SectorDTO sectorDTO) {
        return new Sector(sectorDTO.getId());
    }

    public static List<SectorDTO> toDTOList(List<Sector> sectorList) {
        List<SectorDTO> returnList = new ArrayList<>();
        for (Sector sector : sectorList) {
            returnList.add(toDTO(sector));
        }
        return returnList;
    }

    public static List<Sector> toEntityList(List<SectorDTO> sectorDTOList) {
        List<Sector> returnList = new ArrayList<>();
        for (SectorDTO sector : sectorDTOList) {
            returnList.add(toEntity(sector));
        }
        return returnList;
    }

}