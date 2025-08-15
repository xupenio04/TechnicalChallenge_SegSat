package com.example.sensordata.repository;

import com.example.sensordata.model.SensorData;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;


@Repository
public class SensorDataRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;

    public List<SensorData> findAllByOrderByTimestampDesc() {
        String jpql = "SELECT s FROM SensorData s ORDER BY s.timestamp DESC";
        TypedQuery<SensorData> query = entityManager.createQuery(jpql, SensorData.class);
        return query.getResultList();
    }

    public List<SensorData> findBySensorId(String sensorId) {
        String jpql = "SELECT s FROM SensorData s WHERE s.sensorId = :sensorId ORDER BY s.timestamp DESC";
        TypedQuery<SensorData> query = entityManager.createQuery(jpql, SensorData.class);
        query.setParameter("sensorId", sensorId);
        return query.getResultList();
    }

    public List<SensorData> findByTimestampBetween(LocalDateTime start, LocalDateTime end) {
        String jpql = "SELECT s FROM SensorData s WHERE s.timestamp BETWEEN :start AND :end ORDER BY s.timestamp DESC";
        TypedQuery<SensorData> query = entityManager.createQuery(jpql, SensorData.class);
        query.setParameter("start", start);
        query.setParameter("end", end);
        return query.getResultList();
    }

    public List<SensorData> findBySensorIdAndTimestampBetween(String sensorId, LocalDateTime start, LocalDateTime end) {
        String jpql = "SELECT s FROM SensorData s WHERE s.sensorId = :sensorId AND s.timestamp BETWEEN :start AND :end ORDER BY s.timestamp DESC";
        TypedQuery<SensorData> query = entityManager.createQuery(jpql, SensorData.class);
        query.setParameter("sensorId", sensorId);
        query.setParameter("start", start);
        query.setParameter("end", end);
        return query.getResultList();
    }

    public SensorData findLatestBySensorId(String sensorId) {
        String jpql = "SELECT s FROM SensorData s WHERE s.sensorId = :sensorId ORDER BY s.timestamp DESC";
        TypedQuery<SensorData> query = entityManager.createQuery(jpql, SensorData.class);
        query.setParameter("sensorId", sensorId);
        query.setMaxResults(1);
        List<SensorData> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    public List<SensorData> findByTemperatureGreaterThan(Double temperature) {
        String jpql = "SELECT s FROM SensorData s WHERE s.temperature > :temperature ORDER BY s.timestamp DESC";
        TypedQuery<SensorData> query = entityManager.createQuery(jpql, SensorData.class);
        query.setParameter("temperature", temperature);
        return query.getResultList();
    }


    public List<SensorData> findByHumidityLessThan(Double humidity) {
        String jpql = "SELECT s FROM SensorData s WHERE s.humidity < :humidity ORDER BY s.timestamp DESC";
        TypedQuery<SensorData> query = entityManager.createQuery(jpql, SensorData.class);
        query.setParameter("humidity", humidity);
        return query.getResultList();
    }

    public Long countBySensorId(String sensorId) {
        String jpql = "SELECT COUNT(s) FROM SensorData s WHERE s.sensorId = :sensorId";
        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        query.setParameter("sensorId", sensorId);
        return query.getSingleResult();
    }

    public Double findAverageTemperatureBySensorId(String sensorId) {
        String jpql = "SELECT AVG(s.temperature) FROM SensorData s WHERE s.sensorId = :sensorId";
        TypedQuery<Double> query = entityManager.createQuery(jpql, Double.class);
        query.setParameter("sensorId", sensorId);
        Double result = query.getSingleResult();
        return result != null ? result : 0.0;
    }
    
    @Transactional
    public void save(SensorData sensorData) {
        if (sensorData.getId() == null) {
            entityManager.persist(sensorData);
        } else {
            entityManager.merge(sensorData);
        }
    }
    
}