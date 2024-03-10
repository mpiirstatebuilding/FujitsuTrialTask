CREATE TABLE WEATHER_DATA (
    station_name VARCHAR(50) NOT NULL,
    wmo_code INT,
    air_temp DOUBLE,
    wind_speed DOUBLE,
    phenomenon VARCHAR(50),
    timestamp TIMESTAMP NOT NULL,
    PRIMARY KEY (station_name, timestamp)
);
