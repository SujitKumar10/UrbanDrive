package com.Timepass.ecotrack.service;

import com.Timepass.ecotrack.dto.BookingDto;
import com.Timepass.ecotrack.dto.CreateOrderResponse;
import com.Timepass.ecotrack.dto.VerifyPaymentRequest;

public interface PaymentService {

    CreateOrderResponse createOrder(Double amountInr);

    BookingDto verifyPaymentAndCreateBooking(VerifyPaymentRequest request, String userEmail);
}
