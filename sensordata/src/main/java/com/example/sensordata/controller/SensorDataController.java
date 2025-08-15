package com.example.sensordata.controller;

import com.example.sensordata.repository.SensorDataRepositoryImpl;
import com.example.sensordata.model.SensorData;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/sensor-data")
public class SensorDataController {
     
    private final SensorDataRepositoryImpl sensorDataRepository;

    public SensorDataController(SensorDataRepositoryImpl sensorDataRepository) {
        this.sensorDataRepository = sensorDataRepository;
    }

    @GetMapping
    public ResponseEntity<List<SensorData>> getAllSensorData() {
        try {
            List<SensorData> data = sensorDataRepository.findAllByOrderByTimestampDesc();
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 2. Obter dados por ID do sensor
     * GET /api/sensor-data/by-sensor/{sensorId}
     */
    @GetMapping("/by-sensor/{sensorId}")
    public ResponseEntity<List<SensorData>> getSensorDataBySensorId(
            @PathVariable String sensorId) {
        try {
            if (sensorId == null || sensorId.isBlank()) {
                return ResponseEntity.badRequest().build();
            }
            
            List<SensorData> data = sensorDataRepository.findBySensorId(sensorId);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 3. Obter dados por intervalo de tempo
     * GET /api/sensor-data/by-time-range?start={start}&end={end}
     */
    @GetMapping("/by-time-range")
    public ResponseEntity<List<SensorData>> getSensorDataByTimestampRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        try {
            if (start == null || end == null || start.isAfter(end)) {
                return ResponseEntity.badRequest().build();
            }
            
            List<SensorData> data = sensorDataRepository.findByTimestampBetween(start, end);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 4. Obter dados por sensor e intervalo de tempo
     * GET /api/sensor-data/by-sensor/{sensorId}/time-range?start={start}&end={end}
     */
    @GetMapping("/by-sensor/{sensorId}/time-range")
    public ResponseEntity<List<SensorData>> getSensorDataBySensorAndTimeRange(
            @PathVariable String sensorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        try {
            if (sensorId == null || sensorId.isBlank() || start == null || end == null || start.isAfter(end)) {
                return ResponseEntity.badRequest().build();
            }
            
            List<SensorData> data = sensorDataRepository.findBySensorIdAndTimestampBetween(sensorId, start, end);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 5. Obter a última leitura de um sensor
     * GET /api/sensor-data/by-sensor/{sensorId}/latest
     */
    @GetMapping("/by-sensor/{sensorId}/latest")
    public ResponseEntity<SensorData> getLatestSensorData(
            @PathVariable String sensorId) {
        try {
            if (sensorId == null || sensorId.isBlank()) {
                return ResponseEntity.badRequest().build();
            }
            
            SensorData data = sensorDataRepository.findLatestBySensorId(sensorId);
            return data != null ? ResponseEntity.ok(data) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 6. Obter dados com temperatura acima de um valor
     * GET /api/sensor-data/temperature-above/{temperature}
     */
    @GetMapping("/temperature-above/{temperature}")
    public ResponseEntity<List<SensorData>> getSensorDataWithTemperatureAbove(
            @PathVariable Double temperature) {
        try {
            List<SensorData> data = sensorDataRepository.findByTemperatureGreaterThan(temperature);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 7. Obter dados com umidade abaixo de um valor
     * GET /api/sensor-data/humidity-below/{humidity}
     */
    @GetMapping("/humidity-below/{humidity}")
    public ResponseEntity<List<SensorData>> getSensorDataWithHumidityBelow(
            @PathVariable Double humidity) {
        try {
            List<SensorData> data = sensorDataRepository.findByHumidityLessThan(humidity);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 8. Obter estatísticas de um sensor
     * GET /api/sensor-data/stats/{sensorId}
     */
    @GetMapping("/stats/{sensorId}")
    public ResponseEntity<SensorStats> getSensorStats(
            @PathVariable String sensorId) {
        try {
            if (sensorId == null || sensorId.isBlank()) {
                return ResponseEntity.badRequest().build();
            }
            
            Long count = sensorDataRepository.countBySensorId(sensorId);
            Double avgTemp = sensorDataRepository.findAverageTemperatureBySensorId(sensorId);
            
            SensorStats stats = new SensorStats(count, avgTemp);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    public static class SensorStats {
        private final Long count;
        private final Double averageTemperature;

        public SensorStats(Long count, Double averageTemperature) {
            this.count = count;
            this.averageTemperature = averageTemperature;
        }

        // Getters
        public Long getCount() { return count; }
        public Double getAverageTemperature() { return averageTemperature; }
    }
}