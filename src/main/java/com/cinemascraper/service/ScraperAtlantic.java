package com.cinemascraper.service;

import com.cinemascraper.model.FilmModel;
import com.cinemascraper.utils.VerifyUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.io.IOException;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class ScraperAtlantic extends Scraper {

    private static final Logger logger = LoggerFactory.getLogger(ScraperAtlantic.class);

    public ScraperAtlantic(
            @Value("${scraper.atlantic.date-selector}") String dateSelector,
            @Value("${scraper.atlantic.title-selector}") String titleSelector,
            @Value("${scraper.atlantic.url}") String url,
            @Value("${scraper.atlantic.show-Time-Selector}") String showTimeSelector) {
        super(dateSelector, titleSelector, url, showTimeSelector);
    }


    public void fetchAndParse() throws IOException {

        LocalDate today;
        Map<String, List<String>> mapOfFilms = new LinkedHashMap<>();


        for (int i = 0; i < 7; i++) {
            today = LocalDate.now().plusDays(i);

            String dailyURL = url + today;
            System.out.println(dailyURL);
            Document doc = Jsoup.connect(dailyURL).timeout(2000).get();
            Elements movies = doc.select("tr.repertoire-movie-tr");
            for(Element movie : movies) {
                Map<String, List<String>> titleShowtimes = new LinkedHashMap<>();
                String film = doc.select(titleSelector).text();
                List<String> showTime = doc.select(showTimeSelector).eachText();
                titleShowtimes.put(film, showTime);
                logger.info("Films: {}", showTime);
                for(Map.Entry<String, List<String>> entry : titleShowtimes.entrySet()) {
                    String title = entry.getKey();
                    List<String> showTimes = entry.getValue();
                    FilmModel filmModel = new FilmModel("Atlantic",title, String.valueOf(today), showTimes);
                    filmModel.addFilmToDB();
                }

            }



        }

    }

}








