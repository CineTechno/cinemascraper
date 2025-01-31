package com.cinemascraper.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;
import java.util.List;

@Table("showtimes")
public class ShowTimeModel {
    @Id
    private Integer id;
    private Integer filmId;
    private List<LocalDateTime> showDatetime;

    public ShowTimeModel(Integer filmId, List<LocalDateTime> showDatetime) {
        this.filmId = filmId;
        this.showDatetime = showDatetime;
    }

    public Integer getId() { return id; }
    public Integer getFilmId() { return filmId; }
    public List<LocalDateTime> getShowDatetime() { return showDatetime; }
}