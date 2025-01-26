CREATE TABLE IF NOT EXISTS Films(
    id INT AUTO_INCREMENT PRIMARY KEY,
    cinema VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    show_datetime DATETIME NOT NULL,
    PRIMARY KEY (id),
    );
CREATE INDEX idx_title ON Films (title);
CREATE INDEX idx_show_datetime ON Films (show_datetime);