package com.cinemascraper.filmRepository;

import com.cinemascraper.model.EventModel;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;

@Repository
public class EventRepository {

    JdbcTemplate jdbcTemplate;

    public EventRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    public void save(EventModel event) {
        String sql = "insert into events (title, description, link) values (?, ?, ?) " +
                "on conflict(title) DO UPDATE SET " +
                "description = EXCLUDED.description, image_path = EXCLUDED.link " +
                "returning id_event";
        jdbcTemplate.queryForObject(sql, Integer.class, event.getTitle(), event.getDescription(), event.getLink());

        String sqlDate = "insert into date_time_event(date_and_time) values (?,?) ";
        jdbcTemplate.queryForObject(sqlDate, Integer.class, event.getDateAndTime());
    }

}
