package com.cinemascraper.filmRepository;

import com.cinemascraper.model.FilmModel;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FilmRepository {

    private List<FilmModel> filmRepository = new ArrayList<FilmModel>();

    public List<FilmModel> getFilmRepository() {
        return filmRepository;
    }

    public FilmModel findFilmByTitle(String title) {
       return filmRepository.stream()
                .filter(filmModel -> filmModel
                        .getTitle().
                        equals(title)).
                findFirst().
                orElse(null);
    }

    public void addToFilmRepository(FilmModel film) {
        FilmModel existingFilm = findFilmByTitle(film.getTitle());
        String flattenedDate = film.getDate().getFirst();

        if (existingFilm != null) {
            if(!existingFilm.getDate().contains(flattenedDate)) {
                existingFilm.getDate().add(flattenedDate);
            }
            existingFilm.getDateShowTime().merge(flattenedDate, new ArrayList<>(film.getShowTime()), (existingList, newList) -> {
                if (!existingList.contains(film.getShowTime())) {
                    existingList.addAll((film.getShowTime()));
                }
                return existingList;
            });
        } else {
            filmRepository.add(film);
        }
    }
    }


