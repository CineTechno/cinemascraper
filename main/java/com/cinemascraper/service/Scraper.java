package com.cinemascraper.service;
import com.cinemascraper.model.FilmModel;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.*;

public abstract class Scraper {
    String dateSelector;
    String titleSelector;
    String url;
    String showTimeSelector;
    public static List<FilmModel> filmList = new ArrayList<>();

    public Scraper( String dateSelector, String titleSelector, String url) {
        this(dateSelector, titleSelector, url, ""); // Providing default value
    }

    public Scraper( String dateSelector, String titleSelector, String url, String showTimeSelector  ) {
        this.dateSelector = dateSelector;
        this.titleSelector = titleSelector;
        this.url = url;
        this.showTimeSelector = showTimeSelector;
    }

    public List<FilmModel> getFilmList() {
        return filmList;
    }


}


