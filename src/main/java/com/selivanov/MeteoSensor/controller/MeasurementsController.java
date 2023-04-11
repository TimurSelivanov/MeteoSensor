package com.selivanov.MeteoSensor.controller;

import com.selivanov.MeteoSensor.dto.MeasurementDTO;
import com.selivanov.MeteoSensor.dto.MeasurementsResponse;
import com.selivanov.MeteoSensor.model.Measurement;
import com.selivanov.MeteoSensor.service.MeasurementsService;
import com.selivanov.MeteoSensor.util.ErrorsUtil;
import com.selivanov.MeteoSensor.util.MeasurementErrorResponse;
import com.selivanov.MeteoSensor.util.MeasurementException;
import com.selivanov.MeteoSensor.util.MeasurementValidator;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

    private final MeasurementsService measurementsService;
    private final MeasurementValidator measurementValidator;
    private final ModelMapper modelMapper;

    public MeasurementsController(MeasurementsService measurementsService, MeasurementValidator measurementValidator, ModelMapper modelMapper) {
        this.measurementsService = measurementsService;
        this.measurementValidator = measurementValidator;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementDTO measurementDTO,
                                          BindingResult bindingResult) {
        Measurement newMeasurement = mapToMeasurement(measurementDTO);

        measurementValidator.validate(newMeasurement, bindingResult);
        if(bindingResult.hasErrors()) {
            ErrorsUtil.returnErrorsToClient(bindingResult);
        }

        measurementsService.addMeasurement(newMeasurement);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping()
    public MeasurementsResponse getMeasurements() {
        return new MeasurementsResponse(measurementsService.findAll().stream().map(this::mapToMeasurementDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/countRainyDays")
    public Long countRainyDays () {
        return measurementsService.findAll().stream().filter(Measurement :: getRaining).count();
    }

    private Measurement mapToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO mapToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementException exception) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                exception.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}

