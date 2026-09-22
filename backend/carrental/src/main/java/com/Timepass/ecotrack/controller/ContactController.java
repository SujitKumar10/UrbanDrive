package com.Timepass.ecotrack.controller;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Timepass.ecotrack.dto.ContactMessageRequest;
import com.Timepass.ecotrack.entities.ContactMessage;
import com.Timepass.ecotrack.repository.ContactMessageRepository;
import com.Timepass.ecotrack.service.EmailService;

@RestController
@RequestMapping("/contact")
@CrossOrigin
public class ContactController {

    @Autowired
    private ContactMessageRepository contactMessageRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<Void> submitContact(@RequestBody ContactMessageRequest req) {
        ContactMessage message = modelMapper.map(req, ContactMessage.class);
        message.setId(null);
        message.setCreatedAt(LocalDateTime.now());
        message.setResolved(false);
        ContactMessage saved = contactMessageRepository.save(message);

        emailService.sendContactQueryEmail(saved);

        return ResponseEntity.ok().build();
    }
}

