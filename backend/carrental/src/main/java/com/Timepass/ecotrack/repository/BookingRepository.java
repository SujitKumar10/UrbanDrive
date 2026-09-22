package com.Timepass.ecotrack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Timepass.ecotrack.entities.Booking;
import com.Timepass.ecotrack.entities.User;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    List<Booking> findByUser(User user);
}
