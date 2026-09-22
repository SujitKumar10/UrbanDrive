package com.Timepass.ecotrack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Timepass.ecotrack.dto.BookingDto;
import com.Timepass.ecotrack.dto.CreateOrderRequest;
import com.Timepass.ecotrack.dto.CreateOrderResponse;
import com.Timepass.ecotrack.dto.VerifyPaymentRequest;
import com.Timepass.ecotrack.service.PaymentService;

@RestController
@RequestMapping("/payments")
@CrossOrigin
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create-order")
    public ResponseEntity<CreateOrderResponse> createOrder(
            @RequestBody CreateOrderRequest request,
            Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }
        CreateOrderResponse response = paymentService.createOrder(request.getAmount());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify")
    public ResponseEntity<BookingDto> verifyPayment(
            @RequestBody VerifyPaymentRequest request,
            Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }
        String email = auth.getName();
        BookingDto booking = paymentService.verifyPaymentAndCreateBooking(request, email);
        return ResponseEntity.ok(booking);
    }
}
