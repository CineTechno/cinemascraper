CREATE TABLE events(
    id_event SERIAL PRIMARY KEY,
    title VARCHAR,
    description VARCHAR,
    image_path VARCHAR
);

CREATE TABLE date_time_event(
    id SERIAL PRIMARY KEY,
    id_event INT,
    date_and_time TIMESTAMP,
    FOREIGN KEY(id_event) REFERENCES events(id_event) ON DELETE CASCADE
);

CREATE TABLE organisers(
    id SERIAL PRIMARY KEY,
    name VARCHAR
);

CREATE TABLE organisers_events(
    id SERIAL PRIMARY KEY,
    id_event INT,
    id_organiser INT,
    FOREIGN KEY(id_event) REFERENCES events(id_event) ON DELETE CASCADE ,
    FOREIGN KEY (id_organiser) REFERENCES  organisers(id) ON DELETE CASCADE
);