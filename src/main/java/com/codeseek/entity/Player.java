package com.codeseek.entity;

import com.codeseek.controller.dto.response.PlayerResponseDTO;
import com.codeseek.entity.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private LocalDate birthDate;
    private LocalDate startCareerDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Team team;

    private String countryCode;

    @Enumerated(EnumType.STRING)
    private Position position;

    private Boolean isActive;

    public PlayerResponseDTO toDTO() {
        return PlayerResponseDTO.builder()
                .id(id)
                .name(name)
                .countryCode(countryCode)
                .birthDate(birthDate)
                .startCareerDate(startCareerDate)
                .isActive(isActive)
                .position(position).build();
    }
}
