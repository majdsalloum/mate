package tests;

import Storage.StorageManger;
import core.parser.HTMLParser;
import core.tags.Tag;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


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
        Tag a = HTMLParser.compile(builder.toString());
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
        Tag a = HTMLParser.compile(html);
    }

    public static void main(String... args) throws Exception {
       simpleTest();



//        simpleTestTable();

    }
}
