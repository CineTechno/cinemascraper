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
        // Get values from environment variables
        String username = System.getenv("PGUSER");
        String password = System.getenv("PGPASSWORD");
        String host = System.getenv("PGHOST");
        String port = System.getenv("PGPORT");
        String database = System.getenv("PGDATABASE");

        // Set defaults for any missing values
        if (host == null) host = "postgres.railway.internal";
        if (port == null) port = "5432";
        if (database == null) database = "railway";

        String jdbcUrl = String.format("jdbc:postgresql://%s:%s/%s", host, port, database);

        return DataSourceBuilder.create()
                .url(jdbcUrl)
                .username(username)
                .password(password)
                .build();
    }
}