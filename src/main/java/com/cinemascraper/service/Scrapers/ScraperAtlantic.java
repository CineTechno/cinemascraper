package com.cinemascraper.service.Scrapers;

import com.cinemascraper.model.FilmModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
    public List<FilmModel> getFilmSchedule() {

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
            movies = doc.select("tr.repertoire-movie-tr");

            for(Element movie : movies) {
                Element titleId =movie.selectFirst(".repertoire-movie-title a");
                 String titleIdStr =titleId.attr("href");
                Map<String, String> filmDetails = Objects.requireNonNullElse(getFilmDetails(titleIdStr), Collections.emptyMap());
                String title = titleProcessing(movie.select(titleSelector).text());
                String description = filmDetails.getOrDefault("description", "");
                String director = filmDetails.getOrDefault("director","");
                String year = filmDetails.getOrDefault("year", "");
                String imgPath = filmDetails.getOrDefault("imgPath","");
                List<LocalDateTime> dateShowTime = new ArrayList<>();
                List<String> showTime = movie.select(showTimeSelector).eachText();
                for(String time : showTime) {
                    LocalDateTime dateTime = LocalDateTime.parse(today + " " + time, formatter);
                    dateShowTime.add(dateTime);
                    FilmModel filmModel = new FilmModel("Atlantic",title,description,director,year,imgPath, dateShowTime);
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
    public Map<String,String> getFilmDetails(String titleId) {
        String processedTitle = titleId.replaceAll("[.!-]", "").replaceAll(" ", "-");
        Map<String,String> detailsMap = new HashMap<>();
        try {
            Document website = Jsoup.connect("https://www.novekino.pl/kina/atlantic/"+titleId).get();
            String description = Objects.requireNonNull(website.selectFirst("p"))
                    .text().trim();
            String imgPath = Objects.requireNonNull(website.selectFirst("img.sp-image").attr("src"));
            String director = Objects.requireNonNull(website.select("div.movie_details_panel-info dl dd:nth-of-type(1)")).text();
            String year = Objects.requireNonNull(website.select("div.movie_details_panel-info dl dd:nth-of-type(5)")).text();
            detailsMap.put("description", description);
            detailsMap.put("director", director);
            detailsMap.put("year", year);
            detailsMap.put("imgPath", imgPath);
            return detailsMap;
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }

    }
}









