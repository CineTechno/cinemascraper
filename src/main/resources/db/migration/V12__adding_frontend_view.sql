CREATE OR REPLACE VIEW cinema_schedule_view AS
SELECT
    c.name AS cinema_name,
    jsonb_build_object(
            'id', c.id,
            'cinemaName', c.name,
            'filmsWithShowtimes',
            (SELECT jsonb_agg(
                            jsonb_build_object(
                                    'film', jsonb_build_object(
                                    'id', f.id,
                                    'title', f.title,
                                    'description', f.description,
                                    'director', f.director,
                                    'year', f.release_year,
                                    'imgPath', f.img_path,
                                    'rating', f.rating,
                                    'link', l.link
                                            ),
                                    'showtimes', (SELECT jsonb_agg(s.show_datetime)
                                                  FROM showtimes s
                                                  WHERE s.cinema_id = c.id AND s.film_id = f.id)
                            )
                    ) FROM films f
                               JOIN links l ON l.film_id = f.id AND l.cinema_id = c.id
             WHERE f.id IN (SELECT DISTINCT film_id FROM showtimes WHERE cinema_id = c.id)
               AND f.title IS NOT NULL
               AND f.description IS NOT NULL
               AND f.director IS NOT NULL
               AND f.release_year IS NOT NULL
               AND f.img_path IS NOT NULL
               AND f.rating IS NOT NULL
               AND l.link IS NOT NULL
            )
    ) AS cinema_json
FROM cinemas c;