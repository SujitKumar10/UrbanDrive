package com.Timepass.ecotrack.service;

import java.util.List;

import com.Timepass.ecotrack.dto.BookingDto;
import com.Timepass.ecotrack.dto.BookingRequest;
import com.Timepass.ecotrack.dto.CancellationRequestDto;

public interface BookingService {

    BookingDto createBooking(BookingRequest request, String userEmail);

    List<BookingDto> getBookingsByUser(String userEmail);

    List<BookingDto> getAllBookings();
    
    void requestCancellation(Integer bookingId, String reason, String userEmail);
    
    List<CancellationRequestDto> getCancellationRequests();
    
    void handleCancellationRequest(Integer bookingId, String action, String adminComment);
}
