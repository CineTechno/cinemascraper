package com.cinemascraper.service;

import com.cinemascraper.filmRepository.FilmRepository;
import com.cinemascraper.model.FilmModel;
import com.cinemascraper.model.TMDBMovie;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TMDBMovieService {
    private static final String API_KEY = "b34a16eb8215ff272b37a9f1bda50e25";
    private static final String BASE_URL = "https://api.themoviedb.org/3/search/movie";
    private static final Logger logger = LoggerFactory.getLogger(TMDBMovieService.class);
    private final ObjectMapper objectMapper;
    private final OkHttpClient okHttpClient;
    private final FilmRepository filmRepository;

    public TMDBMovieService(OkHttpClient okHttpClient, ObjectMapper objectMapper, FilmRepository filmRepository) {
        this.okHttpClient = okHttpClient;
        this.objectMapper = objectMapper;
        this.filmRepository = filmRepository;
    }

    public void addRatingAndImages() {
        List<String> titlesFromDB = filmRepository.getFilmTitles();
        try {
            List<TMDBMovie> TMDBFilmList = getMovieDetailsFromTMDB(titlesFromDB);

        }catch (IOException e){
            logger.error(e.getMessage());
        }
    }

    public void getMovieDetailsFromTMDB(List<String> titlesFromDB) throws IOException {
        List<TMDBMovie> movies = new ArrayList<>();
        for (String title : titlesFromDB) {
            String url = BASE_URL + "?api_key=" + API_KEY + "&query=" + title;
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try (Response response = okHttpClient.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    logger.error("Error fetching movie details. HTTP Code: {}", response.code());

                }
                JsonNode jsonNode = objectMapper.readTree(response.body().string());
                JsonNode resultsNode = jsonNode.get("results");
                TMDBMovie movie = objectMapper.treeToValue(resultsNode.get(0), TMDBMovie.class);
                TMDBMovie tmdbMovieWithPolishTitle=  new TMDBMovie(
                        title,
                        movie.getPosterPath(),
                        movie.getReleaseDate(),
                        movie.getVoteAverage(),
                        movie.getVoteCount(),
                        movie.getPopularity(),
                        movie.getGenreIds(),
                        movie.getBackdropPath()
                );

                movies.add(tmdbMovieWithPolishTitle);
            } catch (IOException e) {
                logger.error("Empty response body from OMDb API.");
            }
        }

        for(TMDBMovie tmdbMovie : movies) {
            if(tmdbMovie.getTitle()==)
        }

    }
}

