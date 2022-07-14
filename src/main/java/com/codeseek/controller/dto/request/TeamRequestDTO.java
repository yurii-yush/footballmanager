package com.codeseek.controller.dto.request;

import com.codeseek.annotations.CountryValidation;
import com.codeseek.common.Messages;
import com.codeseek.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamRequestDTO {
    private Long id;
    @NotEmpty(message = Messages.NOT_EMPTY_NAME)
    @Size(min = 5, max = 40, message = Messages.REQUIRED_NAME_LENGTH)
    private String name;
    @CountryValidation
    private String countryCode;
    @NotNull(message = Messages.NOT_NULL_BALANCE)
    @Min(value = 100_000, message = Messages.REQUIRED_BALANCE_FOR_CREATING_TEAM)
    private BigDecimal balance;
    @NotNull(message = Messages.NOT_NULL_COMMISSION)
    @DecimalMin(value = "0.10", message = Messages.REQUIRED_MIN_TEAM_COMMISSION)
    @DecimalMax(value = "10.0", message = Messages.REQUIRED_MAX_TEAM_COMMISSION)
    private BigDecimal commission;

    public Team toEntity() {
        return Team.builder()
                .id(id)
                .name(name)
                .countryCode(countryCode)
                .isActive(true)
                .players(new ArrayList<>())
                .balance(balance)
                .commission(commission).build();
    }
}
