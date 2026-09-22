package com.Timepass.ecotrack.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderResponse {
    private String orderId;
    private Double amount;
    private String currency;
    private String razorpayKey; // Public key for frontend checkout
}
