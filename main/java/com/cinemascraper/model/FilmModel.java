package com.cinemascraper.model;

import java.time.LocalDateTime;
import java.util.*;

public class FilmModel {

    String cinema;
    String title;
    List<LocalDateTime> dateShowTime;

    public FilmModel(String cinema, String title, LocalDateTime dateShowTime) {
        this(cinema, title, List.of (dateShowTime));
    }

    public FilmModel(String cinema, String title, List<LocalDateTime> dateShowTime) {
        this.cinema = cinema;
        this.title = title;
        this.dateShowTime = dateShowTime;
    }

    public String getCinema() {
        return cinema;
    }

    public String getTitle() {
        return title;
    }

    public List<LocalDateTime> getDateShowTime() {
        return dateShowTime;
    }
    public void setDateShowTime(List<LocalDateTime> DateShowTimes) {
        dateShowTime.addAll(DateShowTimes);
    }


    private static FilmModel findFilmByTitle(String title, List<FilmModel> films) {
        for (FilmModel film : films) {
            if (Objects.equals(film.getTitle(), title)) {
                return film;
            }
        }
        return null;
    }
}