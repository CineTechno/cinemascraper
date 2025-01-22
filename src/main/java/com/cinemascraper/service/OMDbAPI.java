package com.cinemascraper.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class OMDbAPI {
    private static final String API_KEY = "cc0773dd";
    private static final String BASE_URL = "https://www.omdbapi.com/";

    public String getMovieByTitle(String title) {
        try {
            // URL encode to handle spaces and special characters
            String encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8);
            String url = BASE_URL + "?t=" + encodedTitle + "&apikey=" + API_KEY;

            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error fetching movie data.";
        }
    }
}
