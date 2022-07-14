package com.codeseek.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamResponseDTO {

    private Long id;
    private String name;
    private String countryCode;
    private BigDecimal balance;
    private BigDecimal commission;

    private List<PlayerResponseDTO> players;
    private Boolean isActive;

}
