package com.example.sensordata.repository;

import com.example.sensordata.model.SensorData;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface SensorDataRepository extends JpaRepository<SensorData, Long> {
    List<SensorData> findBySensorId(String sensorId);
    List<SensorData> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
} 