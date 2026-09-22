package com.Timepass.ecotrack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Timepass.ecotrack.dto.BookingDto;
import com.Timepass.ecotrack.dto.BookingRequest;
import com.Timepass.ecotrack.dto.CancellationRequest;
import com.Timepass.ecotrack.dto.CancellationRequestDto;
import com.Timepass.ecotrack.service.BookingService;

@RestController
@RequestMapping("/bookings")
@CrossOrigin
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingDto> createBooking(
            @RequestBody BookingRequest request,
            Authentication auth) {
        String email = auth.getName();
        BookingDto booking = bookingService.createBooking(request, email);
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @GetMapping("/my")
    public ResponseEntity<List<BookingDto>> getMyBookings(Authentication auth) {
        String email = auth.getName();
        return ResponseEntity.ok(bookingService.getBookingsByUser(email));
    }

    @PostMapping("/{bookingId}/cancel")
    public ResponseEntity<String> requestCancellation(
            @PathVariable Integer bookingId,
            @RequestBody CancellationRequest request,
            Authentication auth) {
        String email = auth.getName();
        bookingService.requestCancellation(bookingId, request.getReason(), email);
        return ResponseEntity.ok("Cancellation request submitted successfully");
    }
}
