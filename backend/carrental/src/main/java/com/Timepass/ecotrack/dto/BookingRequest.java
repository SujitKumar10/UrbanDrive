package com.Timepass.ecotrack.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {

    private Integer carId;
    private LocalDate startDate;
    private LocalDate endDate;
}
