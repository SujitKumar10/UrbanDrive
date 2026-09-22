package com.Timepass.ecotrack.service;

import java.util.List;

import com.Timepass.ecotrack.dto.CarDto;

public interface CarService {

    List<CarDto> getAllCars();

    List<CarDto> getCarsByType(String type);

    CarDto getCarById(Integer id);

    CarDto createCar(CarDto carDto);

    CarDto updateCar(Integer id, CarDto carDto);

    void deleteCar(Integer id);

    List<CarDto> getAllCarsForAdmin();
}
