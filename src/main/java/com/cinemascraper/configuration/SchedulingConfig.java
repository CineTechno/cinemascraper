package com.cinemascraper.configuration;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@Configuration
public class SchedulingConfig {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(SchedulingConfig.class);

    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteOldShowtimes() {
        String sql = "DELETE FROM showtimes WHERE show_datetime < CURRENT_DATE";
        jdbcTemplate.update(sql);
        log.info("Old showtimes deleted successfully");
    }
}