package com.cinemascraper.filmcontroller;
import com.cinemascraper.service.OMDbAPI;
import com.cinemascraper.service.ScraperService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "http://localhost:5174")
@RequestMapping("/api")
public class FilmController {
    private final OMDbAPI oMDbApi;
    private final Logger logger = LoggerFactory.getLogger(FilmController.class);
    private final ScraperService scraperService;
    public FilmController(ScraperService scraperService, OMDbAPI oMDbApi) {
        this.oMDbApi = oMDbApi;
        this.scraperService = scraperService;
    }
    @GetMapping("/scrape")

    public ResponseEntity<String> scrapeTitlesShowtimes() {
        return scraperService.scrapeAll();
    }

    @GetMapping("/movie")
    public String getDetails(@RequestParam String title) {
        return oMDbApi.getMovieDetails(title);
    }




}






