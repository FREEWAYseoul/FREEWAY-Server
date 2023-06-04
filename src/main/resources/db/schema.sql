CREATE TABLE IF NOT EXISTS subway_line
(
    line_id   VARCHAR(3)  NOT NULL PRIMARY KEY,
    line_name VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS station
(
    station_id            VARCHAR(6)  NOT NULL PRIMARY KEY,
    station_name          VARCHAR(10) NOT NULL UNIQUE,
    latitude              VARCHAR(20) NOT NULL,
    longitude             VARCHAR(20) NOT NULL,
    station_status        VARCHAR(20),
    operating_institution VARCHAR(10) NOT NULL,
    station_address       VARCHAR(50) NOT NULL,
    station_contact       VARCHAR(15),
    line_id               VARCHAR(3)  NOT NULL,
    UNIQUE (latitude, longitude),
    FOREIGN KEY (line_id) REFERENCES subway_line (line_id)
);

CREATE TABLE IF NOT EXISTS exits
(
    exit_id     INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    exit_number VARCHAR(5)  NOT NULL,
    latitude    VARCHAR(20) NOT NULL,
    longitude   VARCHAR(20) NOT NULL,
    UNIQUE (latitude, longitude)
);

CREATE TABLE IF NOT EXISTS elevator
(
    elevator_id     INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    latitude        VARCHAR(20) NOT NULL,
    longitude       VARCHAR(20) NOT NULL,
    elevator_status VARCHAR(10),
    description     VARCHAR(50),
    nearest_exit    VARCHAR(5),
    UNIQUE (latitude, longitude)
);

CREATE TABLE IF NOT EXISTS station_exits
(
    station_id VARCHAR(6) NOT NULL,
    exit_id    INT        NOT NULL,
    PRIMARY KEY (station_id, exit_id),
    FOREIGN KEY (station_id) REFERENCES station (station_id),
    FOREIGN KEY (exit_id) REFERENCES exits (exit_id)
);

CREATE TABLE IF NOT EXISTS station_elevator
(
    station_id  VARCHAR(6) NOT NULL,
    elevator_id INT        NOT NULL,
    PRIMARY KEY (station_id, elevator_id),
    FOREIGN KEY (station_id) REFERENCES station (station_id),
    FOREIGN KEY (elevator_id) REFERENCES elevator (elevator_id)
);
