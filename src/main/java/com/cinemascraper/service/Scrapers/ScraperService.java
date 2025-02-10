package com.cinemascraper.service.Scrapers;

import com.cinemascraper.filmRepository.FilmRepository;
import com.cinemascraper.filmcontroller.FilmController;
import com.cinemascraper.service.tmdb.TMDBMovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ScraperService {

    private final ScraperMuranow scraperMuranow;
    private final ScraperAtlantic scraperAtlantic;
    private final ScraperIluzjon scraperIluzjon;
    private final FilmRepository filmRepository;
    private final TMDBMovieService oMDbApi;
    private final Logger logger = LoggerFactory.getLogger(FilmController.class);
    public ScraperService(ScraperMuranow scraperMuranow, ScraperAtlantic scraperAtlantic, ScraperIluzjon scraperIluzjon, FilmRepository filmRepository, TMDBMovieService oMDbApi) {
        this.scraperMuranow = scraperMuranow;
        this.scraperAtlantic = scraperAtlantic;
        this.scraperIluzjon = scraperIluzjon;
        this.filmRepository = filmRepository;
        this.oMDbApi = oMDbApi;

    }

    public ResponseEntity<String> scrapeAll (){
        List<Scraper> listOfScrapers = new ArrayList<>();
        Collections.addAll(listOfScrapers, scraperMuranow, scraperAtlantic, scraperIluzjon);

        try{
            listOfScrapers.forEach(scraper ->  scraper.getFilmSchedule().forEach(filmRepository::create));
        } catch (Exception e) {
            logger.error("Error while scraping films: ", e);
            return ResponseEntity.status(500).body("Scraping failed: " + e.getMessage());
        }

        return ResponseEntity.ok("Scraping successful! Films saved to DB.");
        }

}
