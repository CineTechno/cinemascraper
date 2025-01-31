package com.cinemascraper;

import com.cinemascraper.filmRepository.FilmRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CinemascraperApplication {

	public static void main(String[] args) {
		SpringApplication.run(CinemascraperApplication.class, args);
	}


}

