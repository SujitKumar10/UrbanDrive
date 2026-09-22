package com.Timepass.ecotrack.service;

import com.Timepass.ecotrack.entities.ContactMessage;

public interface EmailService {

    void sendWelcomeEmail(String toEmail, String name);

    void sendContactQueryEmail(ContactMessage message);
    
    void sendBookingConfirmationEmail(String toEmail, String name, String carName, String startDate, String endDate, Double totalPrice);
    
    void sendCancellationRequestEmail(String toEmail, String bookingId, String carName, String reason);
    
    void sendCancellationDecisionEmail(String toEmail, String name, String bookingId, String carName, String decision, String adminComment);
}

