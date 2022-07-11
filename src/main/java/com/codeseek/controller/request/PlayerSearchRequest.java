package com.codeseek.controller.request;

import com.codeseek.annotations.CountryValidationIfNotNull;
import com.codeseek.entity.Team;
import com.codeseek.entity.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PlayerSearchRequest extends SearchRequest {

    private String name;
    private Team team;
    private Position position;

    private LocalDate birthDateFrom;
    private LocalDate birthDateTo;
    private LocalDate startCareerDateFrom;
    private LocalDate startCareerDateTo;

    @CountryValidationIfNotNull
    private String countryCode;

    private Boolean isActive = true;
}
