package com.codeseek.entity;


import com.codeseek.common.Messages;
import com.codeseek.controller.dto.response.TransferResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "transfers")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id", referencedColumnName = "id")
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_team_id", referencedColumnName = "id")
    private Team fromTeam;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_team_id", referencedColumnName = "id")
    private Team toTeam;

    private BigDecimal price;
    private LocalDateTime datetime;

    public TransferResponseDTO toDTO() {
        String fromTeamName = fromTeam == null ? Messages.FREE_AGENT : fromTeam.getName();
        return TransferResponseDTO.builder()
                .id(id)
                .player(player.getName())
                .fromTeam(fromTeamName)
                .toTeam(toTeam.getName())
                .price(price)
                .datetime(datetime).build();
    }
}
