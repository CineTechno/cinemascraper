//package com.cinemascraper.configuration;
//
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class DataSourceConfig {
//    @Bean
//    @Primary
//    public DataSource dataSource() {
//        // Get values from environment variables
//        String username = System.getenv("PGUSER");
//        String password = System.getenv("PGPASSWORD");
//
//        // Print debug info (omit actual password in logs)
//        System.out.println("Database username: " + username);
//        System.out.println("Database password provided: " + (password != null && !password.isEmpty()));
//
//        String host = System.getenv("PGHOST") != null ? System.getenv("PGHOST") : "postgres.railway.internal";
//        String port = System.getenv("PGPORT") != null ? System.getenv("PGPORT") : "5432";
//        String database = System.getenv("PGDATABASE") != null ? System.getenv("PGDATABASE") : "railway";
//
//        String jdbcUrl = String.format("jdbc:postgresql://%s:%s/%s", host, port, database);
//        System.out.println("JDBC URL: " + jdbcUrl);
//
//        // Make sure we're explicitly setting username and password
//        return DataSourceBuilder.create()
//                .driverClassName("org.postgresql.Driver")
//                .url(jdbcUrl)
//                .username(username)
//                .password(password)
//                .build();
//    }
//}