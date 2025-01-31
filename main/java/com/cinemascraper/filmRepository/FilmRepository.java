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
    private final JdbcClient jdbcClient;
    public FilmRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;

    }

    public void create(FilmModel film){
        Integer filmId = jdbcClient.sql("SELECT id FROM films WHERE cinema = ? AND title = ?")
                .params(film.getCinema(), film.getTitle())
                .query(Integer.class)
                .optional()
                .orElse(null);

        if (filmId == null) {
            // Step 2: Insert the film if it doesn't exist
            jdbcClient.sql("INSERT INTO films (cinema, title, description) VALUES (?, ?, ?)")
                    .params(film.getCinema(), film.getTitle(), film.getDescription())
                    .update();

            // Retrieve the newly inserted film's ID
            filmId = jdbcClient.sql("SELECT id FROM films WHERE cinema = ? AND title = ?")
                    .params(film.getCinema(), film.getTitle())
                    .query(Integer.class)
                    .single();
        }


        var sql = "INSERT INTO showtimes (film_id, show_datetime) VALUES (?, ?)";
        for (LocalDateTime dateTime : film.getDateShowTime()) {
            jdbcClient.sql(sql)
                    .params(filmId, Timestamp.valueOf(dateTime))
                    .update();
        }
    }

    public List<String> getFilmTitles(){
        return jdbcClient.sql("SELECT title FROM films")
                .query(String.class)
                .list();
    }

    public void addRatingAndImages(String title, String imgPath, String rating){
    JdbcClient.sql("INSERT INTO films id FROM films WHERE title = title").
            query(String.class)
            .
    }

    public void
    }


