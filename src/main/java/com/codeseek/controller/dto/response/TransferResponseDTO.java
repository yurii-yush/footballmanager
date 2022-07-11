package com.codeseek.controller.dto.response;

import com.codeseek.common.Messages;
import com.codeseek.entity.Player;
import com.codeseek.entity.Team;
import com.codeseek.entity.Transfer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferResponseDTO {

    private Long id;
    private String player;
    private String fromTeam;
    private String toTeam;
    private BigDecimal price;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime datetime;

}
