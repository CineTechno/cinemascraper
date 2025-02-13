package com.cinemascraper.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventModel {
    private String title;
    private LocalDateTime dateAndTime;
    private String organiser;
    private String description;
    private String imagePath;

    public EventModel(String title, LocalDateTime date, String description, String imgPath) {}
}
