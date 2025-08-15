package com.example.sensordata.service;

import com.example.sensordata.model.SensorData;
import com.example.sensordata.repository.SensorDataRepositoryImpl;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KafkaConsumerService {

    private final SensorDataRepositoryImpl repository;

    public KafkaConsumerService(SensorDataRepositoryImpl repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "sensor-topic", groupId = "sensor-group")
    @Transactional
    public void consume(String message) {
        try {
            System.out.println("Mensagem recebida: " + message);
            
            // Remove aspas se existirem
            message = message.replace("\"", "").trim();
            
            String[] parts = message.split(",");
            if (parts.length != 4) {
                throw new IllegalArgumentException("Formato inválido. Esperado: id,sensorId,temperature,humidity");
            }

            SensorData data = new SensorData();
            // Ignora o ID da mensagem pois é auto-incremento
            data.setSensorId(parts[1].trim());
            data.setTemperature(Double.parseDouble(parts[2].trim()));
            data.setHumidity(Double.parseDouble(parts[3].trim()));

            if (isValid(data)) {
                repository.save(data);
                System.out.println("Dados salvos: " + data);
            } else {
                System.err.println("Dados inválidos: " + message);
            }
        } catch (Exception e) {
            System.err.println("Erro ao processar mensagem: " + message);
            System.err.println("Detalhes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean isValid(SensorData data) {
        return data.getSensorId() != null &&
               !data.getSensorId().isBlank() &&
               data.getTemperature() != null &&
               data.getHumidity() != null;
    }
}