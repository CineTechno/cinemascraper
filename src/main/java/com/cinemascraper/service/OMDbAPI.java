package com.cinemascraper.service;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Service
public class OMDbAPI {
    private static final String API_KEY = "cc0773dd";
    private static final String BASE_URL = "https://www.omdbapi.com/";
    private static final Logger logger = LoggerFactory.getLogger(OMDbAPI.class);

    private final OkHttpClient client;

    public OMDbAPI(){
        this.client = new OkHttpClient.Builder()
                .connectionPool(new ConnectionPool(5,1,
                TimeUnit.MINUTES)).build();
    }

    public String getMovieDetails(String title) {
        String encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8);
        Request request = new Request.Builder()
                .url(BASE_URL + "?t=" + encodedTitle + "&apikey=" + API_KEY)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                logger.error("Error fetching movie details. HTTP Code: {}", response.code());
                return "Error: HTTP " + response.code();
            }
            return response.body().string();
        } catch (IOException e) {
            logger.error("Empty response body from OMDb API.");
            return "Error: " + e.getMessage();
        }
    }
}

