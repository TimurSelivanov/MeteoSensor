package com.selivanov.MeteoSensor.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class SensorDTO {
    @NotEmpty(message = "Sensor name should not be empty")
    @Size(min = 2, max = 50, message = "Sensors name should be between 2 and 50 characters")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
