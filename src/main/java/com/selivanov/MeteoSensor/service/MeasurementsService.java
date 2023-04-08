package com.selivanov.MeteoSensor.service;

import com.selivanov.MeteoSensor.model.Measurement;
import com.selivanov.MeteoSensor.repository.MeasurementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {

    private final MeasurementsRepository measurementRepository;
    private final SensorsService sensorService;

    @Autowired
    public MeasurementsService(MeasurementsRepository measurementRepository, SensorsService sensorService) {
        this.measurementRepository = measurementRepository;
        this.sensorService = sensorService;
    }

    public List<Measurement> findAll() {
        return measurementRepository.findAll();
    }

    @Transactional
    public void addMeasurement(Measurement measurement) {
        measurement.setSensor(sensorService.findByName(measurement.getSensor().getName()).get());
        measurement.setMeasurementDateTime(LocalDateTime.now());

        measurementRepository.save(measurement);
    }


}
