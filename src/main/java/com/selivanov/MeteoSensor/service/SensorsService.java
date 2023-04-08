package com.selivanov.MeteoSensor.service;

import com.selivanov.MeteoSensor.model.Sensor;
import com.selivanov.MeteoSensor.repository.SensorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorsService {
    private final SensorsRepository sensorRepository;

    @Autowired
    public SensorsService(SensorsRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public Optional<Sensor> findByName(String name) {
        return sensorRepository.findByName(name);
    }

    @Transactional
    public void registerNewSensor(Sensor sensor) {
        sensorRepository.save(sensor);
    }
}
