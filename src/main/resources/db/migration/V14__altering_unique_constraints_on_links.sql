ALTER TABLE links DROP CONSTRAINT unique_film_cinema_link;
ALTER TABLE links ADD CONSTRAINT unique_film_cinema UNIQUE (cinema_id, film_id);