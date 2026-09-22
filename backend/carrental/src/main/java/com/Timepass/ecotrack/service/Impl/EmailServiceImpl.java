package com.Timepass.ecotrack.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.Timepass.ecotrack.entities.ContactMessage;
import com.Timepass.ecotrack.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.admin.email:${spring.mail.username}}")
    private String adminEmail;

    @Override
    public void sendWelcomeEmail(String toEmail, String name) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromEmail);
        msg.setTo(toEmail);
        msg.setSubject("Welcome to UrbanDrive");
        msg.setText(
                "Hi " + (name == null ? "" : name) + ",\n\n"
                        + "Your UrbanDrive account has been created successfully.\n\n"
                        + "Thanks,\n"
                        + "UrbanDrive Team");
        mailSender.send(msg);
    }

    @Override
    public void sendContactQueryEmail(ContactMessage message) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromEmail);
        msg.setTo(adminEmail);
        msg.setSubject("New Contact Query - UrbanDrive");
        msg.setText(
                "Name: " + message.getName() + "\n"
                        + "Email: " + message.getEmail() + "\n"
                        + "Subject: " + (message.getSubject() == null ? "" : message.getSubject()) + "\n"
                        + "Message:\n" + (message.getMessage() == null ? "" : message.getMessage()) + "\n\n"
                        + "CreatedAt: " + message.getCreatedAt());
        mailSender.send(msg);
    }

    @Override
    public void sendBookingConfirmationEmail(String toEmail, String name, String carName, String startDate, String endDate, Double totalPrice) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromEmail);
        msg.setTo(toEmail);
        msg.setSubject("Booking Confirmed - UrbanDrive");
        msg.setText(
                "Hi " + (name == null ? "" : name) + ",\n\n"
                        + "Your booking has been confirmed successfully!\n\n"
                        + "Booking Details:\n"
                        + "Car: " + carName + "\n"
                        + "Start Date: " + startDate + "\n"
                        + "End Date: " + endDate + "\n"
                        + "Total Price: $" + totalPrice + "\n\n"
                        + "Thank you for choosing UrbanDrive!\n"
                        + "UrbanDrive Team");
        mailSender.send(msg);
    }

    @Override
    public void sendCancellationRequestEmail(String toEmail, String bookingId, String carName, String reason) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromEmail);
        msg.setTo(adminEmail);
        msg.setSubject("New Cancellation Request - UrbanDrive");
        msg.setText(
                "A new cancellation request has been submitted:\n\n"
                        + "Booking ID: " + bookingId + "\n"
                        + "Car: " + carName + "\n"
                        + "Customer Email: " + toEmail + "\n"
                        + "Reason: " + reason + "\n\n"
                        + "Please review and approve or reject this request.");
        mailSender.send(msg);
    }

    @Override
    public void sendCancellationDecisionEmail(String toEmail, String name, String bookingId, String carName, String decision, String adminComment) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromEmail);
        msg.setTo(toEmail);
        msg.setSubject("Cancellation Request " + decision + " - UrbanDrive");
        msg.setText(
                "Hi " + (name == null ? "" : name) + ",\n\n"
                        + "Your cancellation request for booking #" + bookingId + " has been " + decision.toLowerCase() + ".\n\n"
                        + "Car: " + carName + "\n"
                        + "Booking ID: " + bookingId + "\n"
                        + (adminComment != null ? "Admin Comment: " + adminComment + "\n\n" : "\n")
                        + "Thank you for your understanding.\n"
                        + "UrbanDrive Team");
        mailSender.send(msg);
    }
}

