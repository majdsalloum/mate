package Storage;

import javafx.util.Pair;
import java.io.*;
import java.util.LinkedList;

public class StorageManger {
    static public void savePage(String fileData, String path) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(path);
            fileWriter.write(fileData);
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
            stringBuilder.append("\n"+line);
        }
        inputFile.close();
        bufferReader.close();
        return stringBuilder.toString();
    }

    static public void addToHistory(String url)
    {
        if(url == null)return;//todo : edit this later
        LinkedList<String> history  = new LinkedList<>();
        File file = new File("history.bin");
        try{
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
            history = (LinkedList<String>) objectInputStream.readObject();
            objectInputStream.close();
        } catch (IOException e) {
          //  e.printStackTrace();
        } catch (ClassNotFoundException e) {

        }
        history.addFirst(url);
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(history);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static public LinkedList<String> getHistory()
    {
        File file = new File("history.bin");
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
            return (LinkedList<String>) objectInputStream.readObject();
        }catch (Exception e) {
            return null;
        }
    }
    static public void addToBookmarks(String title  , String path)
    {
        LinkedList<Pair<String , String>> bookmarks  = new LinkedList<>();
        File file = new File("bookmarks.bin");
        try{
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
            bookmarks = (LinkedList<Pair<String, String>>) objectInputStream.readObject();
            objectInputStream.close();
        } catch (IOException e) {
            //  e.printStackTrace();
        } catch (ClassNotFoundException e) {

        }
        bookmarks.addFirst(new Pair<>(title , path));
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(bookmarks);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static public LinkedList<Pair<String,String>> getBookmarks()
    {
        File file = new File("bookmarks.bin");
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
            return (LinkedList<Pair<String, String>>) objectInputStream.readObject();
        }catch (Exception e) {
            return null;
        }
    }
}
