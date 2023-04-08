package com.selivanov.MeteoSensor.util;


import com.selivanov.MeteoSensor.model.Sensor;
import com.selivanov.MeteoSensor.service.SensorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SensorValidator implements Validator {

    private final SensorsService sensorsService;

    @Autowired
    public SensorValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Sensor.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Sensor sensor = (Sensor) obj;

        if(sensorsService.findByName(sensor.getName()).isPresent())
            errors.rejectValue("name", "You have already registered sensor with this name");
    }
}
