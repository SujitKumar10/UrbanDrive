package com.Timepass.ecotrack.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CancellationActionRequest {
    private String action; // APPROVE or REJECT
    private String adminComment;
}
