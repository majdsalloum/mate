package tests;

import core.exceptions.InvalidContentException;
import core.exceptions.InvalidSyntaxException;
import core.parser.HTMLParser;
import core.render.Symbols.OrderedSymbol;
import core.tags.Tag;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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



        URL url = new URL("https://scontent-frt3-2.xx.fbcdn.net/v/t1.0-9/62056188_1243861369109989_5683586078846484480_n.jpg?_nc_cat=105&_nc_ht=scontent-frt3-2.xx&oh=e23d531b3a0fbe0fb16ef0e2cac948dd&oe=5D86418C");
        URLConnection urlConnection = url.openConnection();
        urlConnection.connect();
//        for (Map.Entry<String,List<String>> entry:urlConnection.getHeaderFields().entrySet()) {
//            System.out.print(entry.getKey()+ " : ");
//            for(String s : entry.getValue())
//                System.out.print(s + " ");
//            System.out.println();
//        }
//        Pattern pattern = Pattern.compile("\\w+/(\\w+)");
//        Matcher matcher = pattern.matcher(urlConnection.getHeaderField("Content-type"));
//        matcher.find();
//        System.out.println(urlConnection.getHeaderField("Content-type"));
//        System.out.println("Ext : "+matcher.group(1));
//
    }
}
