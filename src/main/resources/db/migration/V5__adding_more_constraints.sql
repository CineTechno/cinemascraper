TRUNCATE TABLE showtimes RESTART IDENTITY;
ALTER TABLE showtimes DROP CONSTRAINT showtimes_cinema_id_fkey;
ALTER TABLE showtimes ADD CONSTRAINT showtimes_cinema_id_fkey  FOREIGN KEY (cinema_id) REFERENCES cinemas(id) ON DELETE CASCADE;
ALTER TABLE showtimes ADD CONSTRAINT unique_triple UNIQUE(film_id, cinema_id, show_datetime);
