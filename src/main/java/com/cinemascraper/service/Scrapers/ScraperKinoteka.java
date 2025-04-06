package com.cinemascraper.service.Scrapers;

import com.cinemascraper.model.FilmModel;
import com.cinemascraper.utils.DateParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.swing.text.DateFormatter;
import java.io.IOException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScraperKinoteka extends Scraper {
    public static void main(String[] args) {

    }
    private static final Logger logger = LoggerFactory.getLogger(ScraperKinoteka.class);

    public ScraperKinoteka(
            @Value("${scraper.kinoteka.date-selector}") String dateSelector,
            @Value("${scraper.kinoteka.title-selector}") String titleSelector,
            @Value("${scraper.kinoteka.url}") String url,
            @Value("${scraper.kinoteka.showtime-selector}") String showTimeSelector
    ) {
        super(dateSelector, titleSelector, url, showTimeSelector);
    }
    private Document connectWithCustomCert(String url) throws Exception {
        // Load cert from resources
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        Certificate cert = cf.generateCertificate(getClass().getResourceAsStream("/_.kinoteka.pl"));
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(null, null); // create empty keystore
        ks.setCertificateEntry("kinoteka", cert);

        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(ks);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), new SecureRandom());

        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

        return Jsoup.connect(url)
                .userAgent("Mozilla/5.0") // optional but good practice
                .timeout(10_000)
                .get();
    }

    @Override
    public List<FilmModel> getFilmSchedule() {
        List<FilmModel> filmSchedule = new ArrayList<>();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            for(int i =0;i<7;i++){
                String urlDate = LocalDate.now().plusDays(i).format(formatter);
                Document doc = connectWithCustomCert("https://kinoteka.pl/repertuar/?date=" + urlDate);
                Elements programItems = doc.select(".e-movie");
                for(Element programItem : programItems){

                    //formatting LocalDateTime
                    List<String> showtimes = programItem.select(".e-movie__footer-screenings.e-movie__screenings li").stream()
                    .map(Element::text).collect(Collectors.toList());
                    List<LocalDateTime> dateTimes = convertToDateTimesList(showtimes, urlDate);

                    //Accessing individual film websites
                    String filmUrl= programItem.selectFirst("a").attr("href").replaceAll("\\?.*","");
                    Document filmWebsite = connectWithCustomCert(filmUrl);

                    //Fetching film details
                    String title = filmWebsite.select(".p-movie-details__hero-title.text-h5").text()
                            .replaceAll("LET’S DOC \\| ", "")
                            .replaceAll(" \\|.*", "");



                    String description = filmWebsite.select("div.mce-content-body.text-body-small").text();
                    Elements filmInfoLabels = filmWebsite.select(".p-movie-details__general-info dt");
                    Elements filmInfoValue = filmWebsite.select(".p-movie-details__general-info dd");
                    Map<String, String> filmInfo = new HashMap<>();
                    for(int j = 0; j<filmInfoLabels.size(); j++){
                        filmInfo.put(filmInfoLabels.get(j).text(), filmInfoValue.get(j).text());
                    }
                    String director = filmInfo.get("Reżyseria:");
                    String year = filmInfo.get("Data premiery:").replaceAll(".*?(\\d{4}).*","$1");
                    String imgPath = filmWebsite.select(".p-movie-details__hero-poster img").attr("data-src");

                    //creating film object
                    FilmModel film = new FilmModel("Kinoteka", title, description, director, year, imgPath, filmUrl,  dateTimes);
                    filmSchedule.add(film);
                }

            }

        } catch (IOException e) {
            logger.error("Error fetching Kinoteka schedule: {}", e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return filmSchedule;
    }


    @Override
    public Map<String, String> getFilmDetails(String filmUrl) {

        return null;
    }



    public List<LocalDateTime> convertToDateTimesList(List<String> showTimeList, String date) {
        List<LocalDateTime> dateTimeList = new ArrayList<>();
        for (String showtime : showTimeList) {
            LocalDateTime dateTime = LocalDateTime.parse(date + " " + showtime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            dateTimeList.add(dateTime);
        }
        return dateTimeList;
    }


}