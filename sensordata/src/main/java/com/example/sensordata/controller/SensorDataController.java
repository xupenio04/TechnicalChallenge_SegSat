package com.example.sensordata.controller;

import com.example.sensordata.model.SensorData;
import com.example.sensordata.repository.SensorDataRepository;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/sensor-data")

public class SensorDataController {
     
    private final SensorDataRepository sensorDataRepository;

    public SensorDataController(SensorDataRepository sensorDataRepository) {
        this.sensorDataRepository = sensorDataRepository;
    }

    @GetMapping("/all")
    public List<SensorData> getAllSensorData() {
        return sensorDataRepository.findAll();
    }

    @GetMapping("/by-sensor-id")
    public List<SensorData> getSensorDataBySensorId(String sensorId) {
        return sensorDataRepository.findBySensorId(sensorId);
    }

    @GetMapping("/by-timestamp-range")
    public List<SensorData> getSensorDataByTimestampRange( @RequestParam LocalDateTime start,@RequestParam LocalDateTime end) {
        return sensorDataRepository.findByTimestampBetween(start, end);
    }
}
