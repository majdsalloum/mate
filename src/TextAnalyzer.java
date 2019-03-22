import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextAnalyzer {
    List<String> tags = new ArrayList<>();
    public TextAnalyzer() {
        String line2 = "<html> <body> <fuck>";
        String regex = "<[a-z]*>";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(line2);

    }
}
