package com.codeseek.controller.dto.response;

import com.codeseek.common.Messages;
import com.codeseek.entity.Player;
import com.codeseek.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamResponseDTO {

    private Long id;
    private String name;
    private String countryCode;
    private BigDecimal balance;

    @DecimalMin(value = "0.10", message = Messages.REQUIRED_MIN_TEAM_COMMISSION)
    @DecimalMax(value = "10.0", message = Messages.REQUIRED_MAX_TEAM_COMMISSION)
    private BigDecimal commission;

    private List<PlayerResponseDTO> players;
    private Boolean isActive;

}
