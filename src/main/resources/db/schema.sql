CREATE TABLE IF NOT EXISTS subway_line
(
    line_id   VARCHAR(3)  NOT NULL PRIMARY KEY,
    line_name VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS facilities
(
    facilities_id                              INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    elevator                                   BOOLEAN,
    wheelchair_lift                            BOOLEAN,
    disabled_toilet                            BOOLEAN,
    transit_parking_lot                        BOOLEAN,
    unmanned_civil_application_issuing_machine BOOLEAN,
    currency_exchange_kiosk                    BOOLEAN,
    train_ticket_office                        BOOLEAN,
    feeding_room                               BOOLEAN
);

CREATE TABLE IF NOT EXISTS station
(
    station_id            INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    station_code          VARCHAR(6)  NOT NULL,
    line_id               VARCHAR(3)  NOT NULL,
    station_name          VARCHAR(10) NOT NULL,
    latitude              VARCHAR(20) NOT NULL,
    longitude             VARCHAR(20) NOT NULL,
    station_status        VARCHAR(20),
    operating_institution VARCHAR(10) NOT NULL,
    station_address       VARCHAR(50) NOT NULL,
    station_contact       VARCHAR(15),
    station_image_url     VARCHAR(100),
    facilities_id         INT,
    foreign_id            VARCHAR(6),
    next_station          INT,
    previous_station      INT,
    branch_station        INT,
    UNIQUE (latitude, longitude),
    FOREIGN KEY (line_id) REFERENCES subway_line (line_id),
    FOREIGN KEY (next_station) REFERENCES station (station_id) ON UPDATE CASCADE,
    FOREIGN KEY (previous_station) REFERENCES station (station_id) ON UPDATE CASCADE,
    FOREIGN KEY (branch_station) REFERENCES station (station_id) ON UPDATE CASCADE,
    FOREIGN KEY (facilities_id) REFERENCES facilities (facilities_id)
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
    elevator_status VARCHAR(20),
    description     VARCHAR(50),
    nearest_exit    VARCHAR(5),
    UNIQUE (latitude, longitude)
);

CREATE TABLE IF NOT EXISTS station_exits
(
    station_id INT NOT NULL,
    exit_id    INT NOT NULL,
    PRIMARY KEY (station_id, exit_id),
    FOREIGN KEY (station_id) REFERENCES station (station_id) ON UPDATE CASCADE,
    FOREIGN KEY (exit_id) REFERENCES exits (exit_id)
);

CREATE TABLE IF NOT EXISTS station_elevator
(
    station_id  INT NOT NULL,
    elevator_id INT NOT NULL,
    PRIMARY KEY (station_id, elevator_id),
    FOREIGN KEY (station_id) REFERENCES station (station_id) ON UPDATE CASCADE,
    FOREIGN KEY (elevator_id) REFERENCES elevator (elevator_id)
);

CREATE TABLE IF NOT EXISTS notification
(
    notification_id      INT           NOT NULL AUTO_INCREMENT PRIMARY KEY,
    notification_summary VARCHAR(100)  NOT NULL,
    notification_content VARCHAR(1024) NOT NULL,
    notification_date    DATETIME      NOT NULL
);