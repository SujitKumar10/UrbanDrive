package com.Timepass.ecotrack.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactMessageDto {
    private Long id;
    private String name;
    private String email;
    private String subject;
    private String message;
    private LocalDateTime createdAt;
    private boolean resolved;
}

