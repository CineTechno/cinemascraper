package com.cinemascraper.filmRepository;

import com.cinemascraper.model.FilmImage;
import com.cinemascraper.model.FilmModel;
import com.cinemascraper.model.TMDBMovie;
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

    public void create(FilmModel film) {
        var filmID = jdbcClient.sql("INSERT INTO films (title, description, director, release_year, img_path) VALUES (?,?,?,?,?) " +
                        "ON CONFLICT (title) DO UPDATE SET description = EXCLUDED.description, " +
                        "director = EXCLUDED.director, " +
                        "release_year = EXCLUDED.release_year, " +
                        "img_path = EXCLUDED.img_path " +
                        "RETURNING id")
                .params(film.getTitle(), film.getDescription(),film.getDirector(),film.getYear(),film.getImgPath())
                .query(Integer.class)
                .single();

        var cinemaID = jdbcClient.sql("INSERT INTO cinemas (name) VALUES (?) " +
                        "ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name " +
                        "RETURNING id")
                .params(film.getCinema())
                .query(Integer.class)
                .single();

        jdbcClient.sql("INSERT INTO title_cinema (film_id, cinema_id) VALUES (?, ?) " +
                        "ON CONFLICT (film_id, cinema_id) DO NOTHING")
                .params(filmID, cinemaID)
                .update();

        for (LocalDateTime dateTime:film.getDateShowTime()) {
            jdbcClient.sql("INSERT INTO showtimes (film_id, cinema_id, show_datetime) VALUES (?, ?, ?)")
                    .params(filmID, cinemaID, Timestamp.valueOf(dateTime))
                    .update();
        }
    }

    public void addRating(List<TMDBMovie> tmdbMovieList) {
        for (TMDBMovie tmdbMovie : tmdbMovieList) {
            jdbcClient.sql("UPDATE films SET rating=? WHERE title = ?")
                    .params(tmdbMovie.getVoteAverage(), tmdbMovie.getTitle())
                    .update();
        }
    }

    public List<FilmImage> getImgPaths(){
        return jdbcClient.sql("SELECT title, img_path FROM films")
                .query((rs,row) -> new FilmImage(
                        rs.getString("title"),
                        rs.getString("img_path")))
                .list();
    }



    public List<String> getTitlesFromDB(){
        List<String> titles = jdbcClient.sql("SELECT title FROM films")
                .query(String.class)
                .list();
        return titles;
    }

    public List<FilmModel> getFilmsFromCinema(String cinema){

        return jdbcClient.sql("SELECT f.id,f.title,f.description,f.director,f.release_year AS releaseYear " +
                "FROM films f " +
                "JOIN title_cinema t ON f.id = t.film_id " +
                "JOIN cinemas c ON t.cinema_id = c.id " +
                "WHERE c.name = ?")
                .params(cinema)
                .query(FilmModel.class)
                .list();
    }
}



