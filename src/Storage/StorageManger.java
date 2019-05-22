package Storage;

import java.io.*;

public class StorageManger {
    static public void savePage(String fileData, String path) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(path);
            fileWriter.write(fileData);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try
            {
                fileWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static public String loadPage(String path) throws IOException {
        FileReader inputFile = new FileReader(path);
        BufferedReader bufferReader = new BufferedReader(inputFile);
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = bufferReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }
}
