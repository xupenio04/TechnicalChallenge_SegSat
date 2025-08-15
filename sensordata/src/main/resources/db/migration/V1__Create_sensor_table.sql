CREATE TABLE sensor_data (
    id SERIAL PRIMARY KEY,
    sensor_id VARCHAR(50) NOT NULL,
    temperature FLOAT8,
    humidity FLOAT8,
    timestamp TIMESTAMP
);

CREATE INDEX idx_sensor_id ON sensor_data(sensor_id);
CREATE INDEX idx_timestamp ON sensor_data(timestamp);