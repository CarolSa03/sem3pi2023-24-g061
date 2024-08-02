package dto.mapper;

import domain.DataBase.Plot;
import dto.PlotDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The type Plot mapper.
 */
public class PlotMapper {

    /**
     * To dto plot dto.
     *
     * @param plot the plot
     * @return the plot dto
     */
    public static PlotDTO toDTO(Plot plot) {
        return new PlotDTO(plot.getId(), plot.getDesignation(), plot.getArea());
    }

    /**
     * To entity plot.
     *
     * @param plotDTO the plot dto
     * @return the plot
     */
    public static Plot toEntity(PlotDTO plotDTO) {
        return new Plot(plotDTO.getId());
    }

    /**
     * To dto list.
     *
     * @param plotList the plot list
     * @return the list
     */
    public static List<PlotDTO> toDTOList(List<Plot> plotList) {
        List<PlotDTO> returnList = new ArrayList<>();
        for (Plot plot : plotList) {
            returnList.add(toDTO(plot));
        }
        return returnList;
    }

    /**
     * To entity list.
     *
     * @param plotDTOList the plot list
     * @return the list
     */
    public static List<Plot> toEntityList(List<PlotDTO> plotDTOList) {
        List<Plot> returnList = new ArrayList<>();
        for (PlotDTO plot : plotDTOList) {
            returnList.add(toEntity(plot));
        }
        return returnList;
    }

    /**
     * To dto set.
     *
     * @param plotSet the plot set
     * @return the set
     */
    public static Set<PlotDTO> toDTOSet(Set<Plot> plotSet) {
        Set<PlotDTO> returnList = new HashSet<>();
        for (Plot plot : plotSet) {
            returnList.add(toDTO(plot));
        }
        return returnList;
    }

    /**
     * To entity set.
     *
     * @param plotDTOSet the plot dto set
     * @return the set
     */
    public static Set<Plot> toEntitySet(Set<PlotDTO> plotDTOSet) {
        Set<Plot> returnList = new HashSet<>();
        for (PlotDTO dto : plotDTOSet) {
            returnList.add(toEntity(dto));
        }
        return returnList;
    }

}