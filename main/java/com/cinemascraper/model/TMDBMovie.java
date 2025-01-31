package com.cinemascraper.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class TMDBMovie {
    private String title;
    private String posterPath;
    private String releaseDate;
    private double voteAverage;
    private int voteCount;
    private String backdropPath;
    private double popularity;
    private List<Integer> genreIds;
    private String localRating;

    @JsonCreator
    public TMDBMovie(
            @JsonProperty("title") String title,
            @JsonProperty("poster_path") String posterPath,
            @JsonProperty("release_date") String releaseDate,
            @JsonProperty("vote_average") double voteAverage,
            @JsonProperty("vote_count") int voteCount,
            @JsonProperty("popularity") double popularity,
            @JsonProperty("genre_ids") List<Integer> genreIds,
            @JsonProperty("backdrop_path") String backdropPath

    ) {
        this.title = title;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.popularity = popularity;
        this.genreIds = genreIds;
        this.backdropPath = backdropPath;
    };




    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public double getPopularity() {
        return popularity;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    @Override
    public String toString() {
        return "TMDBMovie{" +
                "posterPath='" + posterPath + '\'' +
                ", backdropPath='" + backdropPath + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", voteAverage=" + voteAverage +
                ", voteCount=" + voteCount +
                ", popularity=" + popularity +
                ", genreIds=" + genreIds +
                '}';
    }
}
