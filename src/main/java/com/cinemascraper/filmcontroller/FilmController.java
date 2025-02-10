package com.cinemascraper.filmcontroller;
import com.cinemascraper.filmRepository.FilmRepository;
import com.cinemascraper.model.FilmImage;
import com.cinemascraper.model.FilmModel;
import com.cinemascraper.service.Scrapers.*;
import com.cinemascraper.service.tmdb.TMDBMovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5174")
@RequestMapping("/api")
public class FilmController {
    private final TMDBMovieService tmdbMovieService;
    private final Logger logger = LoggerFactory.getLogger(FilmController.class);
    private final ScraperService scraperService;
    private final FilmRepository filmRepository;
    private final ScraperMuranow scraperMuranow;
    private final ScraperAtlantic scraperAtlantic;
    private final ScraperIluzjon scraperIluzjon;
    private final ImageDownloaderService imageDownloaderService;
    private final ScraperKinoteka scraperKinoteka;

    public FilmController(ScraperService scraperService, TMDBMovieService tmdbMovieService,
                          FilmRepository filmRepository, ScraperMuranow scraperMuranow,
                          ScraperAtlantic scraperAtlantic, ScraperIluzjon scraperIluzjon,
                          ImageDownloaderService imageDownloaderService, ScraperKinoteka scraperKinoteka) {
        this.tmdbMovieService = tmdbMovieService;
        this.scraperService = scraperService;
        this.filmRepository = filmRepository;
        this.scraperMuranow = scraperMuranow;
        this.scraperAtlantic = scraperAtlantic;
        this.scraperIluzjon = scraperIluzjon;
        this.scraperKinoteka = scraperKinoteka;
        this.imageDownloaderService = imageDownloaderService;
    }
    @GetMapping("/scrape")

    public ResponseEntity<String> scrapeFilmSchedule() {
        return scraperService.scrapeAll();
    }


    @GetMapping("/iluzjon")
    public void getFilmDetails() {
    List<FilmModel> films = scraperIluzjon.getFilmSchedule();
    films.forEach(filmRepository::create);

    }

    @GetMapping("/tmdb")
    public void addRating() {
        List<String> titles = filmRepository.getTitlesFromDB();
        logger.info("titles from DB: " + titles);
        try {
            tmdbMovieService.addRatingFromTMDB(titles);
        }   catch (Exception e) {}
    }

    @GetMapping("/images")
    public void downloadImages(){
        List<FilmImage> imgPaths = filmRepository.getImgPaths();
        imageDownloaderService.downloadImage(imgPaths, "images");
    }

    @GetMapping("/atlantic")
    public void scrapeAtlantic() {
        scraperAtlantic.getFilmSchedule().forEach(filmRepository::create);
    }

    @GetMapping("/kinoteka")
    public void scrapeKinoteka() {
        scraperKinoteka.getFilmSchedule().forEach(filmRepository::create);
    }



}






