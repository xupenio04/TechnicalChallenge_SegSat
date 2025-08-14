package com.example.sensordata.service;

import com.example.sensordata.model.SensorData;
import com.example.sensordata.repository.SensorDataRepository;
import org.springframework.kafka.annotation.KafkaListener;

public class KafkaConsumerService {

    private final SensorDataRepository sensorDataRepository;

    public KafkaConsumerService(SensorDataRepository sensorDataRepository) {
        this.sensorDataRepository = sensorDataRepository;
    }

    @KafkaListener(topics = "sensor-data", groupId = "sensor-group")
    public void consume(String message) {
        try{
            String [] parts = message.split(",");
            SensorData sensorData = new SensorData();
            sensorData.setSensorId(parts[0]);
            sensorData.setTemperature(Double.parseDouble(parts[1]));
            sensorData.setHumidity(Double.parseDouble(parts[2]));

            if(IsValidData(sensorData)){
                sensorDataRepository.save(sensorData);
            } 
        } catch (Exception e){
            // Log error
            System.err.println("Failed to process message: " + message);
        }
       
    }

    private boolean IsValidData(SensorData sensorData){
        return  sensorData.getTemperature() != null && sensorData.getHumidity() != null &&
                sensorData.getTemperature() >= -50 && sensorData.getTemperature() <= 100 &&
                sensorData.getHumidity() >= 0 && sensorData.getHumidity() <= 100;
    }
}
