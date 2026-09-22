package com.Timepass.ecotrack.dto;

import com.Timepass.ecotrack.enums.AppRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    private Integer id;
    private AppRole roleName;
}
