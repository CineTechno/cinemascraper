import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Normalizer;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

    public static void main(String[] args) {
        String title = "Dziewczyna z igłą";
        String normalized = Normalizer.normalize(title, Normalizer.Form.NFD).replaceAll("[łŁ]","l").replaceAll("[^\\p{ASCII}]", "");
        String processedTitle = normalized.replaceAll("[^a-zA-Z\\s-]", "").replaceAll(" ", "-").replaceAll("[łŁ]","l");
        System.out.println(processedTitle);
    }
}
