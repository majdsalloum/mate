package tests;

import core.parser.HTMLParser;
import core.tags.Tag;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

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
        List<HTMLParser.TagLocation> a = HTMLParser.compile(builder.toString());
        int b = 1;
    }


    public static void main(String... args) throws Exception {

        simpleTest();


    }
}
