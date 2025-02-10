package com.cinemascraper.model;

public class FilmImage {
    private String title;
    private String imgPath;
    public FilmImage(String title, String imgPath) {
        this.title = title;
        this.imgPath = imgPath;
    }
    public String getTitle() {
        return title;
    }

    public String getImgPath() {
        return imgPath;
    }

    @Override
    public String toString() {
        return "FilmImage{" +
                "title='" + title + '\'' +
                ", imgPath='" + imgPath + '\'' +
                '}';
    }
}
