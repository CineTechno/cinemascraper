package com.cinemascraper.configuration;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    @Bean
    @Primary
    public DataSource dataSource() {
        // Manually extract components from the URL
        String username = "postgres";
        String password = "UiZHJmwwnLCeaXaGzxCDrRxxpOlIkJxz";
        String host = "postgres.railway.internal";
        String port = "5432";
        String database = "railway";

        String jdbcUrl = String.format("jdbc:postgresql://%s:%s/%s", host, port, database);

        return DataSourceBuilder.create()
                .url(jdbcUrl)
                .username(username)
                .password(password)
                .build();
    }
}
