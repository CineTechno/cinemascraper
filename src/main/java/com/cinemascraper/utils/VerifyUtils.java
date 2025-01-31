package com.cinemascraper.utils;
import com.cinemascraper.model.FilmModel;
import java.util.List;
import java.util.Objects;

public class VerifyUtils {

    private static FilmModel findFilmByTitle(String title, List<FilmModel> films) {
        for (FilmModel film : films) {

            if (Objects.equals(film.getTitle(), title)) {
                return film;
            }
        }
        return null;
    }
}

