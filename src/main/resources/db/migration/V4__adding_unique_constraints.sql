ALTER TABLE events ADD CONSTRAINT unique_title UNIQUE(title);
ALTER TABLE date_time_event ADD CONSTRAINT unique_id_date UNIQUE(id_event, date_and_time);
ALTER TABLE organisers ADD CONSTRAINT unique_name UNIQUE(name);
ALTER TABLE organisers_events ADD  CONSTRAINT  unique_id UNIQUE(id_event,id_organiser)