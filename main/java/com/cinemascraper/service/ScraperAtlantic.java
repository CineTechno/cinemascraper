package com.cinemascraper.service;

import com.cinemascraper.filmRepository.FilmRepository;
import com.cinemascraper.model.FilmModel;
import com.cinemascraper.utils.VerifyUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class ScraperAtlantic extends Scraper {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public ScraperAtlantic(
            @Value("${scraper.atlantic.date-selector}") String dateSelector,
            @Value("${scraper.atlantic.title-selector}") String titleSelector,
            @Value("${scraper.atlantic.url}") String url,
            @Value("${scraper.atlantic.show-Time-Selector}") String showTimeSelector) {
        super(dateSelector, titleSelector, url, showTimeSelector);
    }


    public List<FilmModel> fetchAndParse() throws IOException {

        LocalDate today;



        for (int i = 0; i < 7; i++) {
            today = LocalDate.now().plusDays(i);
            String dailyURL = url + today;
            System.out.println(dailyURL);
            Document doc = Jsoup.connect(dailyURL).timeout(2000).get();
            Elements movies = doc.select("tr.repertoire-movie-tr");

            for(Element movie : movies) {
                String title = movie.select(titleSelector).text();
                List<String> showTime = movie.select(showTimeSelector).eachText();
                List<LocalDateTime> dateShowTime = new ArrayList<>();
                for(String time : showTime) {
                    LocalDateTime dateTime = LocalDateTime.parse(today + " " + time, formatter);
                    dateShowTime.add(dateTime);
                    FilmModel filmModel = new FilmModel("Atlantic",title, dateShowTime);
                    tempListOfFilms.add(filmModel);
                }
            }
        }
        return tempListOfFilms;
    }

}








