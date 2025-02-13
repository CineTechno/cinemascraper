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

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ScraperKinoteka extends Scraper {

    private static final Logger logger = LoggerFactory.getLogger(ScraperKinoteka.class);

    public ScraperKinoteka(
            @Value("${scraper.kinoteka.date-selector}") String dateSelector,
            @Value("${scraper.kinoteka.title-selector}") String titleSelector,
            @Value("${scraper.kinoteka.url}") String url,
            @Value("${scraper.kinoteka.showtime-selector}") String showTimeSelector
    ) {
        super(dateSelector, titleSelector, url, showTimeSelector);
    }

    @Override
    public List<FilmModel> getFilmSchedule() {
        List<FilmModel> filmSchedule = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url).get();
            Elements programItems = doc.select(".repertoire-day"); // Select each day's films

            for (Element day : programItems) {
                String date = day.select(".day-header").text(); // Extract the date
                Elements films = day.select(".row.repertoire-position"); // Select each film in the day

                for (Element filmElement : films) {
                    String title = filmElement.select(".movie-title a").text();
                    String filmUrl = "https://kinoteka.pl" + filmElement.select(".movie-title a").attr("href");  //construct full URL
                    Elements showtimes = filmElement.select(".movie-showtime a");

                    for (Element showtimeElement : showtimes) {
                        String showtime = showtimeElement.text();
                        LocalDateTime dateShowTime = dateShowTimeFormatter(date, showtime);

                        Map<String, String> filmDetails = getFilmDetails(filmUrl);
                        String description = filmDetails.getOrDefault("description", "");
                        String director = filmDetails.getOrDefault("director", "");
                        String year = filmDetails.getOrDefault("year", "");
                        String imgPath = filmDetails.getOrDefault("imgPath", "");


                        FilmModel film = new FilmModel("Kinoteka", title, description, director, year, imgPath, dateShowTime);
                        filmSchedule.add(film);
                        logger.info("film {}", film.toString());
                    }
                }
            }

        } catch (IOException e) {
            logger.error("Error fetching Kinoteka schedule: {}", e.getMessage());
        }
        return filmSchedule;
    }


    @Override
    public Map<String, String> getFilmDetails(String filmUrl) {
        Map<String, String> detailsMap = new HashMap<>();
        try {
            Document filmPage = Jsoup.connect(filmUrl).get();

            // Extract Description
            String description = filmPage.select(".movie-info-description").text();
            detailsMap.put("description", description);

            // Extract Image URL
            String imgPath = "https://kinoteka.pl" + filmPage.select(".movie-poster img").attr("src");
            detailsMap.put("imgPath", imgPath);

            // Extract Director and Year
            Elements detailsRows = filmPage.select(".movie-details-row");
            for (Element row : detailsRows) {
                String label = row.select(".movie-details-label").text().trim();
                String value = row.select(".movie-details-value").text().trim();

                if (label.contains("Reżyseria")) {
                    detailsMap.put("director", value);
                } else if (label.contains("Rok produkcji")) {
                    detailsMap.put("year", value);
                }
            }

        } catch (IOException e) {
            logger.error("Error fetching film details for {}: {}", filmUrl, e.getMessage());
        }
        return detailsMap;
    }

    public static LocalDateTime dateShowTimeFormatter(String date, String showtime) {
        // Date from Kinoteka is in format like: "23 maja 2024"
        String formattedDate = DateParser.parseDateKinoteka(date); //Use the DateParser class
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(formattedDate + " " + showtime.replace(":",":"), formatter); // Ensure showtime format is correct
    }
}