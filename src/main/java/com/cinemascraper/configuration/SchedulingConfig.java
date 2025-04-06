package com.cinemascraper.configuration;

import com.cinemascraper.filmRepository.FilmRepository;
import com.cinemascraper.service.Scrapers.ScraperService;
import com.cinemascraper.service.tmdb.TMDBMovieService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.util.List;

@EnableScheduling
@Configuration
public class SchedulingConfig {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(SchedulingConfig.class);
    @Autowired
    private OkHttpClient client;
    @Autowired
    private ScraperService scraperService;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    TMDBMovieService tmdbMovieService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteOldShowtimes() {

        String sql = "DELETE FROM showtimes WHERE show_datetime < CURRENT_DATE";
        jdbcTemplate.update(sql);
        log.info("Old showtimes deleted successfully");
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void scrapeFilms() {
        log.info("Scraping films");
        scraperService.scrapeAll();

        List<String> titles = filmRepository.getTitlesFromDB();

        try {
            tmdbMovieService.addRatingFromTMDB(titles);
        }   catch (Exception e) {}
    }
}