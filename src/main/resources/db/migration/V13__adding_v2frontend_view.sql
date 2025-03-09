CREATE OR REPLACE VIEW frontend_view AS
SELECT
    c.id AS cinema_id,
    c.name AS cinema_name,
    f.id AS film_id,
    f.title,
    f.description,
    f.director,
    f.release_year AS year,
    f.img_path,
    f.rating,
    l.link,
    s.show_datetime
FROM cinemas c
JOIN showtimes s ON s.cinema_id = c.id
JOIN films f ON f.id = s.film_id
JOIN links l ON l.film_id = f.id AND l.cinema_id = c.id
WHERE f.title IS NOT NULL
  AND f.description IS NOT NULL
  AND f.director IS NOT NULL
  AND f.release_year IS NOT NULL
  AND f.img_path IS NOT NULL
  AND f.rating IS NOT NULL
  AND l.link IS NOT NULL;