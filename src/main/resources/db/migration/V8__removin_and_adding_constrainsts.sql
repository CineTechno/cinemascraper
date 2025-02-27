ALTER TABLE links DROP CONSTRAINT unique_id_date_links;
ALTER TABLE links ADD CONSTRAINT unique_film_cinema_link UNIQUE(film_id, cinema_id, link);
