CREATE TABLE IF NOT EXISTS films (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) UNIQUE,
    description VARCHAR(2000),
    director VARCHAR(255),
    release_year VARCHAR(255),
    img_path VARCHAR(255)
    );



CREATE TABLE IF NOT EXISTS cinemas(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS title_cinema(
    cinema_id int,
    film_id int,
    PRIMARY KEY(cinema_id, film_id),
    FOREIGN KEY (film_id) REFERENCES films(id) ON DELETE CASCADE ,
    FOREIGN KEY(cinema_id) REFERENCES cinemas(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS showtimes (
    id SERIAL PRIMARY KEY,
    film_id INT NOT NULL,
    cinema_id INT NOT NULL,
    show_datetime TIMESTAMP NOT NULL,
    FOREIGN KEY (film_id) REFERENCES films(id) ON DELETE CASCADE,
    FOREIGN KEY(cinema_id) REFERENCES cinemas(id)
    );
