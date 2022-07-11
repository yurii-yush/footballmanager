package com.codeseek.controller.request;


import com.codeseek.entity.Player;
import com.codeseek.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TransferSearchRequest extends SearchRequest {

    private Player player;
    private Team fromTeam;
    private Team toTeam;
    private Long priceFrom;
    private Long priceTo;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;

}
