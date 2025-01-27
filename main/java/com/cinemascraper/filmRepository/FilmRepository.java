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
    private List<FilmModel> filmRepository = new ArrayList<FilmModel>();
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
            jdbcClient.sql("INSERT INTO films (cinema, title) VALUES (?, ?)")
                    .params(film.getCinema(), film.getTitle())
                    .update();

            // Retrieve the newly inserted film's ID
            filmId = jdbcClient.sql("SELECT id FROM films WHERE cinema = ? AND title = ?")
                    .params(film.getCinema(), film.getTitle())
                    .query(Integer.class)
                    .single();
        }

        // Step 3: Insert all the showtimes for the film
        var sql = "INSERT INTO showtimes (film_id, show_datetime) VALUES (?, ?)";
        for (LocalDateTime dateTime : film.getDateShowTime()) {
            jdbcClient.sql(sql)
                    .params(filmId, Timestamp.valueOf(dateTime))
                    .update();
        }
    }

    public List<FilmModel> getFilmRepository() {
        return filmRepository;
    }

    public FilmModel findFilmByTitle(String title, String cinema) {
       return filmRepository.stream()
                .filter(filmModel -> filmModel.getTitle().equals(title) && filmModel.getCinema().equals(cinema))
               .findFirst().
                orElse(null);
    }

    public void addFilmToDB(List<FilmModel> filmList){
        filmList.forEach(this::create);
    }

    public void addToFilmRepository(FilmModel film) {
        FilmModel existingFilm = findFilmByTitle(film.getTitle(), film.getCinema());

        if (existingFilm != null) {
            existingFilm.setDateShowTime(film.getDateShowTime());
        } else  {
            filmRepository.add(film);
        }
    }
    }


