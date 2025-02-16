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

import javax.swing.text.DateFormatter;
import java.io.IOException;
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

    @Override
    public List<FilmModel> getFilmSchedule() {
        List<FilmModel> filmSchedule = new ArrayList<>();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            for(int i =0;i<7;i++){
                String urlDate = LocalDate.now().plusDays(i).format(formatter);
                Document doc = Jsoup.connect("https://kinoteka.pl/repertuar/?date="+urlDate).get();
                Elements programItems = doc.select(".e-movie");
                for(Element programItem : programItems){

                    //formatting LocalDateTime
                    List<String> showtimes = programItem.select(".e-movie__footer-screenings.e-movie__screenings li").stream()
                    .map(Element::text).collect(Collectors.toList());
                    List<LocalDateTime> dateTimes = convertToDateTimesList(showtimes, urlDate);

                    //Accessing individual film websites
                    String filmUrl= programItem.selectFirst("a").attr("href");
                    Document filmWebsite = Jsoup.connect(filmUrl).get();

                    //Fetching film details
                    String title = filmWebsite.select(".p-movie-details__hero-title.text-h5").text();


                    String description = filmWebsite.select("div.mce-content-body.text-body-small").text();
                    String director = filmWebsite.select("dl.p-movie-details__general-info dd").text();
                    String year = filmWebsite.select("dl.p-movie-details__general-info dd").text();
                    String imgPath = filmWebsite.select("dl.p-movie-details__hero-poster a").attr("href");

                    //creating film object
                    FilmModel film = new FilmModel("Kinoteka", title, description, director, year, imgPath, dateTimes);
                    filmSchedule.add(film);
                }

            }

        } catch (IOException e) {
            logger.error("Error fetching Kinoteka schedule: {}", e.getMessage());
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