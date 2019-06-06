package tests;

import Storage.StorageManger;
import core.exceptions.InvalidContentException;
import core.exceptions.InvalidSyntaxException;
import core.parser.HTMLParser;
import core.tags.Tag;
import network.DownloadManger;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;


public class ParsingTest {
    // Passed
    private static void simpleTest() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(".\\src\\tests\\test.html"));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            if (builder.length() != 0)
                builder.append("\n");
            builder.append(line);
        }
        System.out.println("Done reading");
        try {
            Tag a = HTMLParser.compile(builder.toString());
        } catch (InvalidSyntaxException e) {
            e.printStackTrace();
        } catch (InvalidContentException e) {
            e.printStackTrace();
        }
        int b = 1;
    }


    private static void simpleTestTable() {
        String html = "<html>" +
                "<head>" +
                "</head>" +
                "<body>" +
                "<table>" +
                "<tr>" +
                "<td>shadi</td>" +
                "<td>majd</td>" +
                "</tr>" +
                "<tr>" +
                "<td>reem</td>" +
                "<td>rite</td>" +
                "</tr>" +
                "</table>" +
                "</body>" +
                "</html>";
        try {
            Tag a = HTMLParser.compile(html);
        } catch (InvalidSyntaxException e) {
            e.printStackTrace();
        } catch (InvalidContentException e) {
            e.printStackTrace();
        }
    }
    static boolean debug= true;
    public static void log(String string)
    {
        if(debug) System.out.println(string);
    }
    public static void main(String... args) throws Exception {

//       simpleTest();
//      simpleTestTable();
//        Thread thread = new Thread(()->{
//            File file = new File("voice.mp3");
//            while (true) {
//                System.out.println(file.length());
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
        //System.out.println(DownloadManger.getFileSize("https://www.soundjay.com/nature/rain-01.mp3"));
 //       System.out.println(DownloadManger.getFileName("https://www.soundjay.com/nature/rain-01.mp3"));
       // thread.start();

//        StorageManger.addToHistory("www.wrer.com");
//        StorageManger.addToHistory("www.wrer.com");
//        StorageManger.addToHistory("www.wrer.com");
//        LinkedList<String> his = StorageManger.getHistory();
//        for(int i=0;i<his.size();i++)
//            System.out.println(his.get(i));
    }
}
