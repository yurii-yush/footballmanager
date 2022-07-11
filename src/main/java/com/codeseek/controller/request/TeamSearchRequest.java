package com.codeseek.controller.request;

import com.codeseek.annotations.CountryValidationIfNotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TeamSearchRequest extends SearchRequest {

    private String name;
    private Long balanceFrom;
    private Long balanceTo;
    private Boolean isActive = true;

    @CountryValidationIfNotNull
    private String countryCode;
}
