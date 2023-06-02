CREATE TABLE IF NOT EXISTS station
(
    station_id            VARCHAR(6)  NOT NULL PRIMARY KEY,
    station_name          VARCHAR(10) NOT NULL UNIQUE,
    latitude              VARCHAR(20) NOT NULL,
    longitude             VARCHAR(20) NOT NULL,
    line_id               VARCHAR(3)  NOT NULL,
    line_name             VARCHAR(10) NOT NULL,
    station_status        VARCHAR(20),
    operating_institution VARCHAR(10) NOT NULL,
    station_address       VARCHAR(50) NOT NULL,
    station_contact       VARCHAR(15),
    UNIQUE (latitude, longitude)
);

CREATE TABLE IF NOT EXISTS station_exit
(
    exit_id     INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    exit_number VARCHAR(5)  NOT NULL,
    latitude    VARCHAR(20) NOT NULL,
    longitude   VARCHAR(20) NOT NULL,
    station_id  VARCHAR(6)  NOT NULL,
    FOREIGN KEY (station_id) REFERENCES station (station_id),
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
    station_id      VARCHAR(6)  NOT NULL,
    FOREIGN KEY (station_id) REFERENCES station (station_id),
    UNIQUE (latitude, longitude)
);
