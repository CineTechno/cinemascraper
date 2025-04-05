package com.cinemascraper;

import com.cinemascraper.filmRepository.FilmRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CinemascraperApplication {

	public static void main(String[] args) {


		System.out.println("DATABASE_URL received: " + System.getenv("DATABASE_URL"));

			SpringApplication.run(CinemascraperApplication.class, args);
	}


}

