package com.Timepass.ecotrack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Timepass.ecotrack.dto.CarDto;
import com.Timepass.ecotrack.service.CarService;

@RestController
@RequestMapping("/cars")
@CrossOrigin
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping
    public ResponseEntity<List<CarDto>> getAllCars(
            @RequestParam(required = false) String type) {
        List<CarDto> cars = type != null && !type.isEmpty()
                ? carService.getCarsByType(type)
                : carService.getAllCars();
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDto> getCarById(@PathVariable Integer id) {
        return ResponseEntity.ok(carService.getCarById(id));
    }
}
