package com.cinemascraper.filmcontroller;


import com.cinemascraper.filmRepository.FilmRepository;
import com.cinemascraper.model.FilmModel;
import com.cinemascraper.service.ScraperAtlantic;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cinemascraper.service.ScraperMuranow;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5174")
@RequestMapping("/")
public class FilmController {

    private final ScraperMuranow scraperMuranow;
    private final ScraperAtlantic scraperAtlantic;
    private final FilmRepository filmRepository;

    public FilmController(ScraperMuranow scraperMuranow, ScraperAtlantic scraperAtlantic, FilmRepository filmRepository) {
        this.scraperMuranow = scraperMuranow;
        this.scraperAtlantic = scraperAtlantic;
        this.filmRepository = filmRepository;

    }
    @GetMapping("/")

    public void scrapeAll (){


        try {
            scraperMuranow.fetchAndParse();
            List<FilmModel> muranowFilms = scraperMuranow.getFilms();
            muranowFilms.forEach(filmRepository::addToFilmRepository);
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            List<FilmModel> atlanticFilms = scraperAtlantic.fetchAndParse();
            atlanticFilms.forEach(filmRepository::addToFilmRepository);
        }catch (Exception e) {
           e.printStackTrace();
        }



        filmRepository.addFilmToDB(filmRepository.getFilmRepository());

    }






}

