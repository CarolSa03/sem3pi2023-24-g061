//package dto.mapper;
//
//import domain.DataBase.Crop;
//import dto.CropDTO;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
///**
// * The type Crop mapper.
// */
//public class CropMapper {
//
//    /**
//     * To dto crop dto.
//     *
//     * @param crop the crop
//     * @return the crop dto
//     */
//    public static CropDTO toDTO(Crop crop) {
//        return new CropDTO(crop.getCropType(), crop.getCropCycle());
//    }
//
//    /**
//     * To entity crop.
//     *
//     * @param cropDTO the crop dto
//     * @return the crop
//     */
//    public static Crop toEntity(CropDTO cropDTO) {
//        return new Crop(cropDTO.getCropType(), cropDTO.getCropCycle());
//    }
//
//    /**
//     * To dto list list.
//     *
//     * @param cropList the crop list
//     * @return the list
//     */
//    public static List<CropDTO> toDTOList(List<Crop> cropList) {
//        List<CropDTO> returnList = new ArrayList<>();
//        for (Crop crop : cropList) {
//            returnList.add(toDTO(crop));
//        }
//        return returnList;
//    }
//
//    /**
//     * To entity list list.
//     *
//     * @param cropList the crop list
//     * @return the list
//     */
//    public static List<Crop> toEntityList(List<CropDTO> cropList) {
//        List<Crop> returnList = new ArrayList<>();
//        for (CropDTO crop : cropList) {
//            returnList.add(toEntity(crop));
//        }
//        return returnList;
//    }
//
//    /**
//     * To dto set set.
//     *
//     * @param cropSet the crop set
//     * @return the set
//     */
//    public static Set<CropDTO> toDTOSet(Set<Crop> cropSet) {
//        Set<CropDTO> returnSet = new HashSet<>();
//        for (Crop crop : cropSet) {
//            returnSet.add(toDTO(crop));
//        }
//        return returnSet;
//    }
//
//    /**
//     * To entity set set.
//     *
//     * @param cropDTOSet the crop dto set
//     * @return the set
//     */
//    public static Set<Crop> toEntitySet(Set<CropDTO> cropDTOSet) {
//        Set<Crop> returnSet = new HashSet<>();
//        for (CropDTO crop : cropDTOSet) {
//            returnSet.add(toEntity(crop));
//        }
//        return returnSet;
//    }
//
//}
