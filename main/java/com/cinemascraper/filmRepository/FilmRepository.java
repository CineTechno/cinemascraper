package com.cinemascraper.filmRepository;

import com.cinemascraper.model.FilmModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FilmRepository {
    private static final Logger logger = LoggerFactory.getLogger(FilmRepository.class);
    private List<FilmModel> filmRepository = new ArrayList<FilmModel>();
    private final JdbcClient jdbcClient;

    public FilmRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;

    }

    public List<FilmModel> findAll(){
        return jdbcClient.sql("select * from films")
                .query(FilmModel.class)
                .list();
    }

    public Optional<FilmModel> findByTitle(String title){
        return jdbcClient.sql("SELECT id, cinema, title, show_datetime FROM films WHERE title= :title")
                .param(title)
                .query(FilmModel.class)
                .optional();
    }

    public void create(FilmModel film){
        var sql = "INSERT INTO films(cinema, title, show_datetime) VALUES (?, ?, ?)";
        List<LocalDateTime> showTimes = film.getDateShowTime();
        for (LocalDateTime dateTime : showTimes) {
                    film.getCinema(), film.getTitle(), Timestamp.valueOf(dateTime));
            jdbcClient.sql(sql)
                    .params(film.getCinema(), film.getTitle(), Timestamp.valueOf(dateTime))
                    .update();
        }
    }

    public List<FilmModel> getFilmRepository() {
        return filmRepository;
    }

    public FilmModel findFilmByTitle(String title) {
       return filmRepository.stream()
                .filter(filmModel -> filmModel
                        .getTitle().
                        equals(title)).
                findFirst().
                orElse(null);
    }

    public void addFilmToDB(List<FilmModel> filmList){
        filmList.forEach(this::create);
    }

    public void addToFilmRepository(FilmModel film) {
        FilmModel existingFilm = findFilmByTitle(film.getTitle());

        if (existingFilm != null) {
            existingFilm.setDateShowTime(film.getDateShowTime());
        } else {
            filmRepository.add(film);
        }
    }
    }


