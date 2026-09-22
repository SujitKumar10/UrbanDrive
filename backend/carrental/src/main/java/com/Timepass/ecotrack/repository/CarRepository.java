package com.Timepass.ecotrack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Timepass.ecotrack.entities.Car;

public interface CarRepository extends JpaRepository<Car, Integer> {

    List<Car> findByAvailableTrue();

    List<Car> findByTypeContainingIgnoreCaseAndAvailableTrue(String type);
}
