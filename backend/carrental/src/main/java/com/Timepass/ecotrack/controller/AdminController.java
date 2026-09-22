package com.Timepass.ecotrack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Timepass.ecotrack.dto.BookingDto;
import com.Timepass.ecotrack.dto.CarDto;
import com.Timepass.ecotrack.dto.ContactMessageDto;
import com.Timepass.ecotrack.dto.CancellationRequestDto;
import com.Timepass.ecotrack.dto.CancellationActionRequest;
import com.Timepass.ecotrack.dto.UserDto;
import com.Timepass.ecotrack.entities.ContactMessage;
import com.Timepass.ecotrack.repository.ContactMessageRepository;
import com.Timepass.ecotrack.service.BookingService;
import com.Timepass.ecotrack.service.CarService;
import com.Timepass.ecotrack.service.UserService;

@RestController
@RequestMapping("/admin")
@CrossOrigin
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private CarService carService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private ContactMessageRepository contactMessageRepository;

    // ========== CARS ==========
    @GetMapping("/cars")
    public ResponseEntity<List<CarDto>> getAllCars() {
        return ResponseEntity.ok(carService.getAllCarsForAdmin());
    }

    @PostMapping("/cars")
    public ResponseEntity<CarDto> createCar(@RequestBody CarDto carDto) {
        return new ResponseEntity<>(carService.createCar(carDto), HttpStatus.CREATED);
    }

    @PutMapping("/cars/{id}")
    public ResponseEntity<CarDto> updateCar(@PathVariable Integer id, @RequestBody CarDto carDto) {
        return ResponseEntity.ok(carService.updateCar(id, carDto));
    }

    @DeleteMapping("/cars/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Integer id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }

    // ========== BOOKINGS ==========
    @GetMapping("/bookings")
    public ResponseEntity<List<BookingDto>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    // ========== CANCELLATION REQUESTS ==========
    @GetMapping("/cancellations")
    public ResponseEntity<List<CancellationRequestDto>> getCancellationRequests() {
        return ResponseEntity.ok(bookingService.getCancellationRequests());
    }

    @PutMapping("/cancellations/{bookingId}")
    public ResponseEntity<String> handleCancellationRequest(
            @PathVariable Integer bookingId,
            @RequestBody CancellationActionRequest request) {
        bookingService.handleCancellationRequest(bookingId, request.getAction(), request.getAdminComment());
        return ResponseEntity.ok("Cancellation request " + request.getAction().toLowerCase() + "d successfully");
    }

    // ========== USERS ==========
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    
    // ========== CONTACT QUERIES ==========
    @GetMapping("/contacts")
    public ResponseEntity<List<ContactMessageDto>> getAllContactQueries() {
        List<ContactMessageDto> list = contactMessageRepository.findAll().stream()
                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .map(c -> new ContactMessageDto(
                        c.getId(),
                        c.getName(),
                        c.getEmail(),
                        c.getSubject(),
                        c.getMessage(),
                        c.getCreatedAt(),
                        c.isResolved()))
                .toList();
        return ResponseEntity.ok(list);
    }
    
    @PatchMapping("/contacts/{id}/resolve")
    public ResponseEntity<Void> resolveContactQuery(@PathVariable Long id) {
        ContactMessage msg = contactMessageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact query not found"));
        msg.setResolved(true);
        contactMessageRepository.save(msg);
        return ResponseEntity.noContent().build();
    }
}
