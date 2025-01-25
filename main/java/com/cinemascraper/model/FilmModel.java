package com.cinemascraper.model;

import java.util.*;

public class FilmModel {

    String cinema;
    String title;
    List<String> date;
    List<String> showTime;
    Map<String, List<String>> dateShowTime;

    public FilmModel(String cinema, String title, String date, String showTime) {
        this(cinema, title, date, new ArrayList<>(List.of(showTime)));
    }

    public FilmModel(String cinema, String title, String date, List<String> showTime) {
        this.cinema = cinema;
        this.title = title;
        this.date = new ArrayList<>(List.of(date));
        this.showTime = new ArrayList<>(showTime);  // Ensure internal consistency
        this.dateShowTime = new LinkedHashMap<>();
        this.dateShowTime.put(date, new ArrayList<>(showTime));
    }

    public String getCinema() {
        return cinema;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getShowTime() {
        return showTime;
    }

    public List<String> getDate() {
        return date;
    }

    public void setCinema(String cinema) {
        this.cinema = cinema;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Map<String, List<String>> getDateShowTime() {
        return dateShowTime;
    }

    private static FilmModel findFilmByTitle(String title, List<FilmModel> films) {
        for (FilmModel film : films) {
            if (Objects.equals(film.getTitle(), title)) {
                return film;
            }
        }
        return null;
    }

//    public void addFilmToDB() {
//        FilmModel existingFilm = findFilmByTitle(this.title, filmList);
//        String flattenedDate = this.date.getFirst();
//
//        if (existingFilm != null) {
//            if(!existingFilm.getDate().contains(flattenedDate)) {
//                existingFilm.getDate().add(flattenedDate);
//            }
//            existingFilm.dateShowTime.merge(flattenedDate, new ArrayList<>(showTime), (existingList, newList) -> {
//                if (!existingList.contains(this.showTime)) {
//                    existingList.addAll((this.showTime));
//                }
//                return existingList;
//            });
//        } else {
//            filmList.add(this);
//        }
//    }
}