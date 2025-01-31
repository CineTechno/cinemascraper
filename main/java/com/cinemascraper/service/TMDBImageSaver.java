package com.cinemascraper.service;

import com.cinemascraper.model.FilmModel;
import com.cinemascraper.model.TMDBMovie;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class TMDBImageSaver {
    private final OkHttpClient okHttpClient;
    public TMDBImageSaver(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public void downloadImage(List<TMDBMovie> films, String outputPath ) throws IOException {

        for(TMDBMovie film : films) {
            String url = "https://image.tmdb.org/t/p/w500/" + film.getPosterPath();
            Request request = new Request.Builder().url(url).build();
            try(Response response = okHttpClient.newCall(request).execute()){
                if(!response.isSuccessful() || response.body() == null){
                    throw new IOException("Failed to download an image " + response);
                }
                byte[] imageBytes = response.body().bytes();
                Files.createDirectories(Paths.get(outputPath).getParent());

                try(FileOutputStream fos = new FileOutputStream(outputPath)){
                    fos.write(imageBytes);
                }
            }catch (IOException e){
                throw new IOException("Failed to download an image " + e);
            }
        }

    }

}
