package com.Timepass.ecotrack.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CancellationRequestDto {
    private Integer bookingId;
    private String reason;
    private String status; // PENDING, APPROVED, REJECTED
    private String userName;
    private String userEmail;
    private String carName;
    private String startDate;
    private String endDate;
    private Double totalPrice;
}
