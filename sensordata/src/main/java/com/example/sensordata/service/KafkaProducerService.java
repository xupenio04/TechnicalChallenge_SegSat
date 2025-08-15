package com.example.sensordata.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.example.sensordata.model.SensorData;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "sensor-data";

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendSensorData(String sensorId, Double temperature, Double humidity) {
        // Gera uma mensagem no formato esperado pelo consumer
        String message = String.format("0,%s,%.2f,%.2f", sensorId, temperature, humidity);
        kafkaTemplate.send(TOPIC, message);
    }

    public void sendSensorData(SensorData sensorData) {
        String message = String.format("%d,%s,%.2f,%.2f", 
            sensorData.getId(),
            sensorData.getSensorId(),
            sensorData.getTemperature(),
            sensorData.getHumidity());
        kafkaTemplate.send(TOPIC, message);
    }
}