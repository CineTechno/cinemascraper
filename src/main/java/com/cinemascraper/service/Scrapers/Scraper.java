package com.cinemascraper.service.Scrapers;
import com.cinemascraper.model.FilmModel;
import com.cinemascraper.utils.DateParser;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
    @Service
public abstract class Scraper {
    String dateSelector;
    String titleSelector;
    String url;
    String showTimeSelector;
    List<FilmModel> tempListOfFilms = new ArrayList<>();

    public Scraper( String dateSelector, String titleSelector, String url) {
        this(dateSelector, titleSelector, url, ""); // Providing default value
    }
    public Scraper( String dateSelector, String titleSelector, String url, String showTimeSelector  ) {
        this.dateSelector = dateSelector;
        this.titleSelector = titleSelector;
        this.url = url;
        this.showTimeSelector = showTimeSelector;

    }

        abstract public List<FilmModel> getFilmSchedule ();

        abstract public Map<String,String> getFilmDetails (String title);

    }


