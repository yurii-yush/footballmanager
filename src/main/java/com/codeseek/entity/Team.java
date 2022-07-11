package com.codeseek.entity;

import com.codeseek.controller.dto.response.PlayerResponseDTO;
import com.codeseek.controller.dto.response.TeamResponseDTO;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String countryCode;
    private BigDecimal balance;
    private BigDecimal commission;
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER)
    private List<Player> players;
    private Boolean isActive;

    public TeamResponseDTO toDTO() {
        List<PlayerResponseDTO> playerResponseDTOS = players.stream()
                .filter(Player::getIsActive).map(Player::toDTO)
                .collect(Collectors.toList());
        return TeamResponseDTO.builder()
                .id(id)
                .name(name)
                .countryCode(countryCode)
                .balance(balance)
                .isActive(isActive)
                .commission(commission)
                .players(playerResponseDTOS).build();
    }
}
