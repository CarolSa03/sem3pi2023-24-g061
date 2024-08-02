package utils.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The type File reader.
 */
public class ReadFile {
    /**
     * Run list.
     *
     * @param filePath the file path
     * @param fileName the file name
     * @return the list
     */

    public static List<String> readFile(String filePath, String fileName) {
        File file = new File(filePath + fileName);
        List<String> fileContent = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                fileContent.add(line);
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("Error reading file!");
            System.out.println("Error : " + e.getMessage());
        }
        return fileContent;
    }
}