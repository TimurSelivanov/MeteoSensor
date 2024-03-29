package com.selivanov.MeteoSensor.util;


import com.selivanov.MeteoSensor.model.Measurement;
import com.selivanov.MeteoSensor.service.SensorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MeasurementValidator implements Validator {

    private final SensorsService sensorsService;

    @Autowired
    public MeasurementValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Measurement.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Measurement measurement = (Measurement) obj;

        if(measurement.getSensor() == null) {
            return;
        }

        if(sensorsService.findByName(measurement.getSensor().getName()).isEmpty()) {
            errors.rejectValue("sensor", "Sensor with this name does not exist");
        }
    }
}
