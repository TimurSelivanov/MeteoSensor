package com.selivanov.MeteoSensor.repository;

import com.selivanov.MeteoSensor.model.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementsRepository extends JpaRepository<Measurement, Integer> {
}
