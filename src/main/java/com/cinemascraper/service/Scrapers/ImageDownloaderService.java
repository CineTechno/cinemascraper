package com.cinemascraper.service.Scrapers;

import com.cinemascraper.model.FilmImage;
import com.cinemascraper.model.FilmModel;
import com.cinemascraper.model.TMDBMovie;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ImageDownloaderService {
    private final OkHttpClient okHttpClient;
    public ImageDownloaderService(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public void downloadImage(List<FilmImage> films, String outputPath ) {

        for(FilmImage film : films) {
            String url = film.getImgPath();
            if(url==null || url.isEmpty()) {
                continue;
            }
            Request request = new Request.Builder().url(url).build();
            try(Response response = okHttpClient.newCall(request).execute()){
                if(!response.isSuccessful() || response.body() == null){
                    System.err.println(response);
                    continue;
                }
                String sanitizedTitle = film.getTitle().replaceAll("[\\\\/:*?\"<>|]", "_");
                String fileName = sanitizedTitle+".jpg";
                Path filePath = Paths.get(outputPath, fileName);
                byte[] imageBytes = response.body().bytes();
                Files.createDirectories(filePath.getParent());

                try(FileOutputStream fos = new FileOutputStream(filePath.toFile())){
                    fos.write(imageBytes);
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }
}
