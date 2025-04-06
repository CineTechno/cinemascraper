package com.cinemascraper.filmcontroller;
import com.cinemascraper.filmRepository.EventRepository;
import com.cinemascraper.filmRepository.FilmRepository;
import com.cinemascraper.model.EventModel;
import com.cinemascraper.model.FilmImage;
import com.cinemascraper.model.FilmModel;
import com.cinemascraper.service.Scrapers.*;
import com.cinemascraper.service.Scrapers.ScraperWajda;
import com.cinemascraper.service.tmdb.TMDBMovieService;
import com.cinemascraper.utils.JsonParser;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatResponse;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    private final ScraperWajda scraperWajda;
    private final EventRepository eventRepository;
    public FilmController(ScraperService scraperService, TMDBMovieService tmdbMovieService,
                          FilmRepository filmRepository, ScraperMuranow scraperMuranow,
                          ScraperAtlantic scraperAtlantic, ScraperIluzjon scraperIluzjon,
                          ImageDownloaderService imageDownloaderService,
                          ScraperKinoteka scraperKinoteka, ScraperWajda scraperWajda,
                            EventRepository eventRepository) {
        this.eventRepository = eventRepository;
        this.tmdbMovieService = tmdbMovieService;
        this.scraperService = scraperService;
        this.filmRepository = filmRepository;
        this.scraperMuranow = scraperMuranow;
        this.scraperAtlantic = scraperAtlantic;
        this.scraperIluzjon = scraperIluzjon;
        this.scraperKinoteka = scraperKinoteka;
        this.imageDownloaderService = imageDownloaderService;
        this.scraperWajda = scraperWajda;
    }
    @GetMapping("/scrape")

    public ResponseEntity<String> scrapeFilmSchedule() {
        return scraperService.scrapeAll();
    }


    @GetMapping("/muranow")
    public void getFilmDetails() {
    scraperMuranow.getFilmSchedule().forEach(filmRepository::create);
    }

    @GetMapping("/tmdb")
    public void addRating() {
        List<String> titles = filmRepository.getTitlesFromDB();
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
    public List<FilmModel> scrapeKinoteka() {
        return scraperKinoteka.getFilmSchedule();
    }

    @GetMapping("/wajda")
    public ResponseEntity scrapeWajda() {
       var eventList = scraperWajda.getEventSchedule();
       eventList.forEach(eventRepository::save);
       return ResponseEntity.ok(eventList);
    }

    @GetMapping("/getcinemadate")
    public String getFilmsFromCinema(@RequestParam String cinema,
                                     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") String date) {
       return  filmRepository.getCinemaDateAsJson(cinema, date);
    }

    @GetMapping("/cinemaschedule")
    public String getCinemaSchedule(@RequestParam String cinema) {
        return filmRepository.getCinemaAsJson(cinema);
    }

    @GetMapping("/test")
        public String test() {
        return filmRepository.testing();
        }


}






