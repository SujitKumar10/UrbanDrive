package com.Timepass.ecotrack.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {

    private Integer id;
    private Integer userId;
    private String userEmail;
    private Integer carId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double totalPrice;
    private String status;
    private CarDto car;
}
