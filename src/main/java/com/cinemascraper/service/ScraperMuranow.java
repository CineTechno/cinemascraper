package com.cinemascraper.service;

import com.cinemascraper.model.FilmModel;
import com.cinemascraper.utils.VerifyUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.cinemascraper.utils.DateParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.text.ParseException;
import java.util.*;

@Service
public class ScraperMuranow extends Scraper {
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

    public void getFilms()  {
        String currentDate="";
        String date = "";
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
                date = DateParser.parseDateMuranow(currentDate);

            }

            else if (element.is(titleSelector)) {
                String title = element.text().replaceAll("\\d", "").trim();
                String showtime = element.text().replaceAll("[^0-9:]", "").trim();
                if (!currentDate.isEmpty()) {

                    FilmModel film = new FilmModel("Muranow", title, date, showtime);

                    film.addFilmToDB();
                }
            }

        }
    }








}


