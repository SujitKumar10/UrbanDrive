package com.Timepass.ecotrack.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {

    private Integer id;
    private String name;
    private String type;
    private Double pricePerDay;  // Frontend maps as "price"
    private String imageUrl;
    private Boolean available;
}
