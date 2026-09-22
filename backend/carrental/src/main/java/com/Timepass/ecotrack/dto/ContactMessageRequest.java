package com.Timepass.ecotrack.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactMessageRequest {
    private String name;
    private String email;
    private String subject;
    private String message;
}

