package com.codeseek.controller.dto.request;

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
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferRequestDTO {
    private Long id;
    @NotNull(message = Messages.REQUIRED_PLAYER)
    private Player player;

    @NotNull(message = Messages.REQUIRED_TO_TEAM)
    private Team toTeam;

    public Transfer toEntity() {
        return Transfer.builder()
                .id(id)
                .player(player)
                .toTeam(toTeam).build();
    }
}
