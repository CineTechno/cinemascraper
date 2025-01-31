package com.cinemascraper.filmRepository;

import com.cinemascraper.model.FilmModel;
import org.springframework.data.repository.ListCrudRepository;

public interface JDBCData extends ListCrudRepository<FilmModel, Integer> {

}
