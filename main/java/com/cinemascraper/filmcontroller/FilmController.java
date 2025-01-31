package com.cinemascraper.filmcontroller;
import com.cinemascraper.filmRepository.FilmRepository;
import com.cinemascraper.model.TMDBMovie;
import com.cinemascraper.service.ScraperService;
import com.cinemascraper.service.TMDBMovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5174")
@RequestMapping("/api")
public class FilmController {
    private final TMDBMovieService TMDB;
    private final Logger logger = LoggerFactory.getLogger(FilmController.class);
    private final ScraperService scraperService;
    private final FilmRepository filmRepository;
    public FilmController(ScraperService scraperService, TMDBMovieService TMDB, FilmRepository filmRepository) {
        this.TMDB = TMDB;
        this.scraperService = scraperService;
        this.filmRepository = filmRepository;
    }
    @GetMapping("/scrape")

    public ResponseEntity<String> scrapeTitlesShowtimes() {
        return scraperService.scrapeAll();
    }


    @GetMapping("/details")
    public void addRatingAndImagesToDB() {
        List<String> titlesFromDB = filmRepository.getFilmTitles();
        List<>

    }

    @GetMapping("/downlaodimages")
    public void downloadImages(@RequestParam String title) {

    }





}






