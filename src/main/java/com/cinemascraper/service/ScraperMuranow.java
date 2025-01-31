package com.cinemascraper.service;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.cinemascraper.model.FilmModel;
import com.cinemascraper.utils.VerifyUtils;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.cinemascraper.utils.DateParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ScraperMuranow extends Scraper {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    Logger logger = LoggerFactory.getLogger(ScraperMuranow.class);
    public ScraperMuranow(
            @Value("${scraper.muranow.date-selector}") String dateSelector,
            @Value("${scraper.muranow.title-selector}") String titleSelector,

            @Value("${scraper.muranow.url}") String url){
        super(dateSelector, titleSelector, url);
    }


    public Elements fetchAndParse () throws IOException {
        Document website = Jsoup.connect(url).get();

        return website.select(dateSelector + "," + titleSelector);
    }


    @Override
    public List<FilmModel> getFilms()  {
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
                String title = element.text().replaceAll("\\d|:|– zestaw", "").trim();
                String showtime = element.text().replaceAll("[^0-9:]", "").trim();
                dateShowTime = dateShowTimeFormatter(currentDate, showtime);
                String description = getDescription(title);
                if (!currentDate.isEmpty()) {

                    FilmModel film = new FilmModel("Muranow", title, description, dateShowTime);

                    tempListOfFilms.add(film);
                }
            }

        }
        return tempListOfFilms;
    }

    @Override
    public String getDescription(String title) {
        String processedTitle = title.replaceAll("[.!-]", "").replaceAll(" ", "-");
        try {
            Document website = Jsoup.connect("https://kinomuranow.pl/film/" + processedTitle).get();
            return Objects.requireNonNull(website.select("div.node__summary").first()).text();
        }catch(IOException e){

        }
        return "" ;
    }


    public LocalDateTime dateShowTimeFormatter(String date, String showtime){
        String formattedDate = DateParser.parseDateMuranow(date);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(formattedDate + " " + showtime, formatter);
    }





}


