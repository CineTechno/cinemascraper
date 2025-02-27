CREATE TABLE links (
    id SERIAL PRIMARY KEY,
    link VARCHAR(255) UNIQUE,
    cinema_id int,
    film_id int,
    FOREIGN KEY (cinema_id) REFERENCES cinemas(id) ON DELETE CASCADE,
    FOREIGN KEY (film_id) REFERENCES films(id) ON DELETE CASCADE
);