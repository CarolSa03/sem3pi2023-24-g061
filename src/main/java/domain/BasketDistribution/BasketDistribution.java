package domain.BasketDistribution;

import utils.file.ReadFile;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BasketDistribution {

    private static final LinkedList<Distance> distancesList = new LinkedList<>();
    private static final LinkedList<Local> localsList = new LinkedList<>();

    List<String> stringList = new ArrayList<>();

    public static LinkedList<Distance> getDistancesList() {
        return distancesList;
    }

    public static LinkedList<Local> getLocalsList() {
        return localsList;
    }

    public void createDistancesList(File file, String path){
        stringList.clear();

        stringList = ReadFile.readFile(path, file.getName()); // O(n)
        convertToDistance(stringList); // O(n)
    }

    private void convertToDistance(List<String> stringList) {
        try {
            for (String line : stringList.subList(1, stringList.size())) { // O(n)
                String[] data = line.split(",");

                Distance distance = new Distance();

                try {
                    distance.setLocal1(data[0]);
                    distance.setLocal2(data[1]);
                    distance.setLength(Double.parseDouble(data[2])/1000);
                } catch (Exception e) {
                    System.out.println("Invalid values.");
                    System.out.println(line);
                }

                distancesList.add(distance);
            }
        } catch (Exception e) {
            System.out.println("Invalid File.");
        }
    }
    public void createLocalsList(File file, String path){
        stringList.clear();

        stringList = ReadFile.readFile(path, file.getName()); // O(n)
        convertToLocal(stringList); // O(n)
    }

    private void convertToLocal(List<String> stringList) {
        for (String line : stringList.subList(1, stringList.size())) { // O(n)
            String[] data = line.split(",");

            Local local = new Local();

            try {
                local.setId(Integer.parseInt(data[0].substring(2)));
                local.setDesignation(data[0]);
                local.setLat(Double.parseDouble(data[1]));
                local.setLng(Double.parseDouble(data[2]));
            } catch (Exception e) {
                System.out.println("Invalid values.");
                System.out.println(line);
            }

            localsList.add(local);
        }
    }
}