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
        String sql = "INSERT INTO events (title, description, link) VALUES (?, ?, ?) " +
                "ON CONFLICT(title) DO UPDATE SET " +
                "description = EXCLUDED.description, link = EXCLUDED.link " +
                "RETURNING id_event";
        Integer event_id = jdbcTemplate.queryForObject(sql, Integer.class, event.getTitle(), event.getDescription(), event.getLink());


        String sqlDate = "insert into date_time_event (date_and_time, id_event) values (?,?) ON CONFLICT (date_and_time, id_event) DO NOTHING";
        jdbcTemplate.update(sqlDate, event.getDateAndTime(), event_id);

        String sqlOrganisers = "insert into organisers(name) values (?) ON CONFLICT(name) DO UPDATE SET name=EXCLUDED.name RETURNING id;";
        Integer organiser_id= jdbcTemplate.queryForObject(sqlOrganisers, Integer.class, event.getOrganiser());

        String sqlOrgEvents = "insert into organisers_events(id_event, id_organiser) values (?,?) ON CONFLICT(id_event, id_organiser) DO NOTHING";
        jdbcTemplate.update(sqlOrgEvents, event_id, organiser_id);
    }

}
