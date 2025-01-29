import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.text.Normalizer;
import java.util.Objects;

public class Test {




    public String getDescription(String title) {
        String normalized = Normalizer.normalize(title, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", ""); // Removes accents

        // Step 2: Replace unwanted characters (., !, –, etc.) with empty string
        String cleaned = normalized.replaceAll("[.!–]", "");

        // Step 3: Replace spaces with hyphens
        String result = cleaned.replaceAll("\\s+", "-").toLowerCase();
    return result;
    }

    public static void main(String[] args) {
        Test test = new Test();
        System.out.println(test.getDescription("Basia. Radzę sobie! – zestaw"));
    }
}
