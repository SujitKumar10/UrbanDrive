package com.Timepass.ecotrack.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Car car;

    private LocalDate startDate;
    private LocalDate endDate;
    private Double totalPrice;
    private String status = "PENDING";
    private String cancellationRequestStatus; // PENDING, APPROVED, REJECTED
    private String cancellationReason;
}
