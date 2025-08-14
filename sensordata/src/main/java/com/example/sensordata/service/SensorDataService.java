package com.example.sensordata.service;
import com.example.sensordata.model.SensorData;
import com.example.sensordata.repository.SensorDataRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDateTime;

@Service
public class SensorDataService {
    private final SensorDataRepository sensorDataRepository;
    
    public SensorDataService(SensorDataRepository sensorDataRepository) {
        this.sensorDataRepository = sensorDataRepository;
    }

    public List<SensorData> getAllSensorData() {
        return sensorDataRepository.findAll();
    }

    public List<SensorData> getSensorDataBySensorId(String sensorId) {
        return sensorDataRepository.findBySensorId(sensorId);
    }

    public List<SensorData> getSensorDataByTimestampRange(LocalDateTime start, LocalDateTime end) {
        return sensorDataRepository.findByTimestampBetween(start, end);
    }
}
