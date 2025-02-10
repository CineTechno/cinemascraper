package com.cinemascraper.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.*;

public class FilmModel {
    private Integer id;
    String cinema;
    String title;
    List<LocalDateTime> dateShowTime;
    String description;
    String director;
    String year;
    String imgPath;

    public FilmModel(String cinema, String title, String description, String director, String year, String imgPath, LocalDateTime dateShowTime) {
        this(cinema, title, description, director, year, imgPath, List.of(dateShowTime));
    }

    public FilmModel(String cinema, String title,String description, String director, String year, String imgPath, List<LocalDateTime> dateShowTime) {
        this.cinema = cinema;
        this.title = title;
        this.description = description;
        this.director = director;
        this.year = year;
        this.imgPath = imgPath;
        this.dateShowTime = new ArrayList<>(dateShowTime);
    }

    public String getCinema() {
        return cinema;
    }

    public String getTitle() {
        return title;
    }


    public String getDescription() {
        return description;
    }

    public String getDirector() {
        return director;
    }

    public String getYear() {
        return year;
    }

    public String getImgPath() {
        return imgPath;
    }

    public List<LocalDateTime> getDateShowTime() {
        return dateShowTime;
    }

    public void setDateShowTime(List<LocalDateTime> DateShowTimes) {
        dateShowTime.addAll(DateShowTimes);
    }

    @Override
    public String toString() {
        return "FilmModel{" +
                "cinema='" + cinema + '\'' +
                ", title='" + title + '\'' +
                ", dateShowTime=" + dateShowTime +
                '}';
    }
}