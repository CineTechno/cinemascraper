package com.cinemascraper.filmcontroller;


import com.cinemascraper.model.FilmModel;
import com.cinemascraper.service.ScraperAtlantic;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cinemascraper.service.ScraperMuranow;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5174")
@RequestMapping("/")
public class FilmController {

    private final ScraperMuranow scraperMuranow;
    private final ScraperAtlantic scraperAtlantic;

    public FilmController(ScraperMuranow scraperMuranow, ScraperAtlantic scraperAtlantic) {
        this.scraperMuranow = scraperMuranow;
        this.scraperAtlantic = scraperAtlantic;

    }
    @GetMapping("/")

    public List<FilmModel> scrapeAll (){
        try {
            scraperMuranow.fetchAndParse();
            scraperMuranow.getFilms();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            scraperAtlantic.fetchAndParse();
        }catch (Exception e) {
           e.printStackTrace();
        }
        return  scraperMuranow.getFilmList();

    }






}

