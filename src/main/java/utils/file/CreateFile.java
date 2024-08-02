package utils.file;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

public class CreateFile {

    public static boolean createFile(String filePath, String fileName, List<String> content) {
        File file = new File(filePath + fileName);
        try {
            PrintWriter printWriter = new PrintWriter(file);
            for (String line : content) {
                printWriter.println(line);
            }
            printWriter.close();
            file.createNewFile();
            return file.exists();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}