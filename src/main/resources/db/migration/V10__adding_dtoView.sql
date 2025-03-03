CREATE OR REPLACE VIEW cinema_films_with_showtimes AS
SELECT
    c.id as cinema_id,
    c.name,
    f.id as film_id,
    f.title,
    f.description,
    f.director,
    f.release_year,
    f.img_path,
    f.rating,
    array_agg(s.show_datetime ORDER BY s.show_datetime) as showtimes
FROM
    cinemas c
        JOIN
    showtimes s ON c.id = s.cinema_id
        JOIN
    films f ON s.film_id = f.id
WHERE
    c.name IS NOT NULL
  AND f.title IS NOT NULL
  AND f.description IS NOT NULL
  AND f.director IS NOT NULL
  AND f.release_year IS NOT NULL
  AND f.img_path IS NOT NULL
  AND f.rating IS NOT NULL
GROUP BY
    c.id, f.id
HAVING
    COUNT(s.show_datetime) > 0
ORDER BY
    f.id;