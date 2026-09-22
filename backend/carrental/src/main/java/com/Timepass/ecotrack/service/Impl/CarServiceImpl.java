package com.Timepass.ecotrack.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Timepass.ecotrack.dto.CarDto;
import com.Timepass.ecotrack.entities.Car;
import com.Timepass.ecotrack.repository.CarRepository;
import com.Timepass.ecotrack.service.CarService;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<CarDto> getAllCars() {
        return carRepository.findByAvailableTrue().stream()
                .map(car -> modelMapper.map(car, CarDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CarDto> getCarsByType(String type) {
        if (type == null || type.isEmpty() || "all".equalsIgnoreCase(type)) {
            return getAllCars();
        }
        return carRepository.findByTypeContainingIgnoreCaseAndAvailableTrue(type).stream()
                .map(car -> modelMapper.map(car, CarDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CarDto getCarById(Integer id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found with id: " + id));
        return modelMapper.map(car, CarDto.class);
    }

    @Override
    public CarDto createCar(CarDto carDto) {
        Car car = modelMapper.map(carDto, Car.class);
        if (car.getAvailable() == null) car.setAvailable(true);
        return modelMapper.map(carRepository.save(car), CarDto.class);
    }

    @Override
    public CarDto updateCar(Integer id, CarDto carDto) {
        Car existing = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found"));
        if (carDto.getName() != null) existing.setName(carDto.getName());
        if (carDto.getType() != null) existing.setType(carDto.getType());
        if (carDto.getPricePerDay() != null) existing.setPricePerDay(carDto.getPricePerDay());
        if (carDto.getImageUrl() != null) existing.setImageUrl(carDto.getImageUrl());
        if (carDto.getAvailable() != null) existing.setAvailable(carDto.getAvailable());
        return modelMapper.map(carRepository.save(existing), CarDto.class);
    }

    @Override
    public void deleteCar(Integer id) {
        if (!carRepository.existsById(id)) throw new RuntimeException("Car not found");
        carRepository.deleteById(id);
    }

    @Override
    public List<CarDto> getAllCarsForAdmin() {
        return carRepository.findAll().stream()
                .map(car -> modelMapper.map(car, CarDto.class))
                .collect(Collectors.toList());
    }
}
