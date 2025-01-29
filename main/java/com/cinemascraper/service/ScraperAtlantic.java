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
    Logger logger = LoggerFactory.getLogger(ScraperAtlantic.class);
    public ScraperAtlantic(
            @Value("${scraper.atlantic.date-selector}") String dateSelector,
            @Value("${scraper.atlantic.title-selector}") String titleSelector,
            @Value("${scraper.atlantic.url}") String url,
            @Value("${scraper.atlantic.show-Time-Selector}") String showTimeSelector) {
        super(dateSelector, titleSelector, url, showTimeSelector);
    }

    @Override
    public List<FilmModel> getFilms() {

        LocalDate today;


        for (int i = 0; i < 7; i++) {
            today = LocalDate.now().plusDays(i);
            String dailyURL = url + today;
            Elements movies;
            Document doc=null;

            try {
                doc = Jsoup.connect(dailyURL).timeout(2000).get();
            }catch(IOException e){
                e.printStackTrace();}

            assert doc != null;
            movies = doc.select("tr.repertoire-movie-tr");

            for(Element movie : movies) {
                Element descriptionDiv =movie.selectFirst(".repertoire-movie-title a");
                 String descriptionLink =descriptionDiv.attr("href");
                String description="";
                try {
                    Document descriptionSite = Jsoup.connect("https://www.novekino.pl/kina/atlantic/"+descriptionLink).get();
                    logger.info("https://www.novekino.pl/kina/atlantic/"+descriptionLink);
                    description = descriptionSite.selectFirst("p")
                            .text().trim();
                }catch(IOException e){
                    e.printStackTrace();
                }
                String title = titleProcessing(movie.select(titleSelector).text());
                List<String> showTime = movie.select(showTimeSelector).eachText();
                List<LocalDateTime> dateShowTime = new ArrayList<>();
                for(String time : showTime) {
                    LocalDateTime dateTime = LocalDateTime.parse(today + " " + time, formatter);
                    dateShowTime.add(dateTime);
                    FilmModel filmModel = new FilmModel("Atlantic",title,description, dateShowTime);
                    tempListOfFilms.add(filmModel);
                }
            }
        }
        return tempListOfFilms;
    }

    public String titleProcessing(String rawTitle) {
        var processedText = rawTitle.replaceAll("- napisy|- przedpremiera|Kino dla Ciebie – Kobiece wieczory w Atlanticu:|– napisy ENG/PL| Pora dla Seniora:|- zestaw|\\(wersja z napisami\\)", "");
        return processedText.trim();
    }

    @Override
    public String getDescription(String title) {
       return "";
    }
}









