package com.cinemascraper.dto;

import lombok.Data;

import java.util.List;

@Data
public class CinemaDto {
    private int cinemaId;
    private String cinemaName;
    private List<FilmWithShowtimesDto> filmWithShowtimes;

}

 class FilmWithShowtimesDto {
    private FilmDto film;
    private List<String> showtimes;
}

 class FilmDto {
    private int id;
    private String title;
    private String description;
    private String director;
    private String year;
    private String imgPath;
    private Double rating;
}
