package com.Timepass.ecotrack.service.Impl;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Timepass.ecotrack.dto.BookingDto;
import com.Timepass.ecotrack.dto.BookingRequest;
import com.Timepass.ecotrack.dto.CarDto;
import com.Timepass.ecotrack.dto.CancellationRequestDto;
import com.Timepass.ecotrack.entities.Booking;
import com.Timepass.ecotrack.entities.Car;
import com.Timepass.ecotrack.entities.User;
import com.Timepass.ecotrack.repository.BookingRepository;
import com.Timepass.ecotrack.repository.CarRepository;
import com.Timepass.ecotrack.repository.UserRespository;
import com.Timepass.ecotrack.service.BookingService;
import com.Timepass.ecotrack.service.EmailService;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRespository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmailService emailService;

    @Override
    public BookingDto createBooking(BookingRequest request, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Car car = carRepository.findById(request.getCarId())
                .orElseThrow(() -> new RuntimeException("Car not found"));

        if (request.getStartDate().isAfter(request.getEndDate())) {
            throw new RuntimeException("End date must be after start date");
        }

        long days = ChronoUnit.DAYS.between(request.getStartDate(), request.getEndDate()) + 1;
        double totalPrice = car.getPricePerDay() * days;

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setCar(car);
        booking.setStartDate(request.getStartDate());
        booking.setEndDate(request.getEndDate());
        booking.setTotalPrice(totalPrice);
        booking.setStatus("CONFIRMED");

        Booking saved = bookingRepository.save(booking);

        // Send booking confirmation email
        emailService.sendBookingConfirmationEmail(
            userEmail, 
            user.getName(), 
            car.getName(),
            request.getStartDate().toString(),
            request.getEndDate().toString(),
            totalPrice
        );

        BookingDto dto = modelMapper.map(saved, BookingDto.class);
        dto.setUserId(user.getId());
        dto.setCarId(car.getId());
        dto.setCar(modelMapper.map(car, CarDto.class));
        return dto;
    }

    @Override
    public List<BookingDto> getBookingsByUser(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return bookingRepository.findByUser(user).stream()
                .map(b -> {
                    BookingDto dto = modelMapper.map(b, BookingDto.class);
                    dto.setUserId(b.getUser().getId());
                    dto.setCarId(b.getCar().getId());
                    dto.setCar(modelMapper.map(b.getCar(), CarDto.class));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDto> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(b -> {
                    BookingDto dto = modelMapper.map(b, BookingDto.class);
                    dto.setUserId(b.getUser().getId());
                    dto.setUserEmail(b.getUser().getEmail());
                    dto.setCarId(b.getCar().getId());
                    dto.setCar(modelMapper.map(b.getCar(), CarDto.class));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void requestCancellation(Integer bookingId, String reason, String userEmail) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        
        if (!booking.getUser().getEmail().equals(userEmail)) {
            throw new RuntimeException("You can only cancel your own bookings");
        }
        
        if (booking.getCancellationRequestStatus() != null) {
            throw new RuntimeException("Cancellation request already submitted");
        }
        
        booking.setCancellationRequestStatus("PENDING");
        booking.setCancellationReason(reason);
        bookingRepository.save(booking);
        
        // Send email to admin about cancellation request
        emailService.sendCancellationRequestEmail(
            userEmail,
            bookingId.toString(),
            booking.getCar().getName(),
            reason
        );
    }

    @Override
    public List<CancellationRequestDto> getCancellationRequests() {
        return bookingRepository.findAll().stream()
                .filter(b -> b.getCancellationRequestStatus() != null && b.getCancellationRequestStatus().equals("PENDING"))
                .map(b -> {
                    CancellationRequestDto dto = new CancellationRequestDto();
                    dto.setBookingId(b.getId());
                    dto.setReason(b.getCancellationReason());
                    dto.setStatus(b.getCancellationRequestStatus());
                    dto.setUserName(b.getUser().getName());
                    dto.setUserEmail(b.getUser().getEmail());
                    dto.setCarName(b.getCar().getName());
                    dto.setStartDate(b.getStartDate().toString());
                    dto.setEndDate(b.getEndDate().toString());
                    dto.setTotalPrice(b.getTotalPrice());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void handleCancellationRequest(Integer bookingId, String action, String adminComment) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        
        if (!"PENDING".equals(booking.getCancellationRequestStatus())) {
            throw new RuntimeException("No pending cancellation request found");
        }
        
        booking.setCancellationRequestStatus(action.toUpperCase());
        
        if ("APPROVE".equalsIgnoreCase(action)) {
            booking.setStatus("CANCELLED");
        }
        
        bookingRepository.save(booking);
        
        // Send email to user about cancellation decision
        emailService.sendCancellationDecisionEmail(
            booking.getUser().getEmail(),
            booking.getUser().getName(),
            bookingId.toString(),
            booking.getCar().getName(),
            action.toUpperCase(),
            adminComment
        );
    }
}
