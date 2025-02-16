package com.cinemascraper.service.Scrapers;

import com.cinemascraper.model.FilmModel;
import com.cinemascraper.utils.DateParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ScraperMuranow extends Scraper {
    Logger logger = LoggerFactory.getLogger(ScraperMuranow.class);
    public ScraperMuranow(
            @Value("${scraper.muranow.date-selector}") String dateSelector,
            @Value("${scraper.muranow.title-selector}") String titleSelector,
            @Value("${scraper.muranow.url}") String url
            )
    {
        super(dateSelector, titleSelector, url);
    }


    public Elements fetchAndParse () throws IOException {
        Document website = Jsoup.connect(url).get();

        return website.select(dateSelector + "," + titleSelector);
    }


    @Override
    public List<FilmModel> getFilmSchedule()  {
        String currentDate="";
        LocalDateTime dateShowTime;
        Elements movieElements = null;
        try {
            movieElements = fetchAndParse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (Element element : movieElements) {
            if (element.is(dateSelector)) {
                Elements spans = element.select("span:nth-child(1), span:nth-child(2), span:nth-child(3)");
                if(spans.size() >= 3) {
                    currentDate = spans.get(0).text() + spans.get(1).text() + spans.get(2).text();
                }


            }

            else if (element.is(titleSelector)) {
                String title = element.text().replaceAll("\\d|:", "").trim();
                String showtime = element.select("span.movie-calendar-info__date").text();
                dateShowTime = dateShowTimeFormatter(currentDate, showtime);
                Map<String,String> filmDetails = Objects.requireNonNullElse(getFilmDetails(title), Collections.emptyMap());
                String description = filmDetails.getOrDefault("description", "");
                String director = filmDetails.getOrDefault("director","");
                String year = filmDetails.getOrDefault("year","");
                String imgPath = filmDetails.getOrDefault("imgPath","");
                if (!currentDate.isEmpty()) {

                    FilmModel film = new FilmModel("Muranow", title, description, director, year, imgPath, dateShowTime);

                    tempListOfFilms.add(film);
                }
            }

        }
        return tempListOfFilms;
    }

    @Override
    public Map<String,String> getFilmDetails(String title) {
        String processedTitle = normalizeText(title);
        Map<String,String> detailsMap = new HashMap<>();
        try {
            Document website = Jsoup.connect("https://kinomuranow.pl/film/" + processedTitle).get();
            String description = Objects.requireNonNull(website.select("div.node__summary").first()).text();
            String imgPath = "https://kinomuranow.pl/" + Objects.requireNonNull(website.selectFirst("div.field__item img").attr("src"));
            String director = Objects.requireNonNull(website.select("div.field.field--name-field-movie-director div.field__items div.field__item")).text();
            String year = Objects.requireNonNull(website.select("div.field.field--name-field-movie-production-year div.field__item")).text();
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



    public static LocalDateTime dateShowTimeFormatter(String date, String showtime){
        String formattedDate = DateParser.parseDateMuranow(date);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(formattedDate + " " + showtime, formatter);
    }

    public static String normalizeText(String text){
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("[łŁ]","l").replaceAll("[^\\p{ASCII}]", "");
        String processedTitle = normalized.replaceAll("[^a-zA-Z\\s-]", "").replaceAll(" - "," ").replaceAll(" ", "-").replaceAll("[łŁ]","l");
        return processedTitle;
    }


}


