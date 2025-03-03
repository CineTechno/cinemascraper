import com.microsoft.playwright.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.lang.model.util.Elements;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Normalizer;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    static String normalizeText(String text){
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("[łŁ]","l").replaceAll("[^\\p{ASCII}]", "");
        String processedTitle = normalized.replaceAll("[^a-zA-Z0-9\\s-]", "").replaceAll(" of ", "-").replaceAll("The ", "").replaceAll(" - "," ").replaceAll(" ", "-").replaceAll("[łŁ]","l");
        return processedTitle;
    }

    public static void main(String[] args) {
        System.out.println(normalizeText("Paryz, 13. dzielnica"));

    }
}
