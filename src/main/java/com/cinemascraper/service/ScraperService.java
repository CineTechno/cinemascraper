package com.cinemascraper.service;

import com.cinemascraper.filmRepository.FilmRepository;
import com.cinemascraper.filmcontroller.FilmController;
import com.cinemascraper.model.FilmModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class ScraperService {

    private final ScraperMuranow scraperMuranow;
    private final ScraperAtlantic scraperAtlantic;
    private final FilmRepository filmRepository;
    private final OMDbAPI oMDbApi;
    private final Logger logger = LoggerFactory.getLogger(FilmController.class);
    public ScraperService(ScraperMuranow scraperMuranow, ScraperAtlantic scraperAtlantic, FilmRepository filmRepository, OMDbAPI oMDbApi) {
        this.scraperMuranow = scraperMuranow;
        this.scraperAtlantic = scraperAtlantic;
        this.filmRepository = filmRepository;
        this.oMDbApi = oMDbApi;

    }

    public ResponseEntity<String> scrapeAll (){
        List<Scraper> listOfScrapers = new ArrayList<>();
        Collections.addAll(listOfScrapers, scraperMuranow, scraperAtlantic);

        try{
            listOfScrapers.forEach(scraper -> {
                List<FilmModel> listOfFilms = scraper.getFilms();
                listOfFilms.forEach(filmRepository::create);

            });
        } catch (Exception e) {
            logger.error("Error while scraping films: ", e);
            return ResponseEntity.status(500).body("Scraping failed: " + e.getMessage());
        }

        return ResponseEntity.ok("Scraping successful! Films saved to DB.");
        }


    public String filmDescription(String title) throws IOException {
        String processedTitle = title.replaceAll(" ", "-");
        Document website = Jsoup.connect("https://kinomuranow.pl/film/" + title).get();
        return Objects.requireNonNull(website.select("div.node__summary").first()).text();
    }
}
