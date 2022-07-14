package com.codeseek.controller.dto.request;

import com.codeseek.annotations.CountryValidation;
import com.codeseek.common.Messages;
import com.codeseek.entity.Player;
import com.codeseek.entity.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PlayerRequestDTO {
    private Long id;
    @NotEmpty(message = Messages.NOT_EMPTY_NAME)
    @Size(min = 5, max = 40, message = Messages.REQUIRED_NAME_LENGTH)
    private String name;
    @CountryValidation
    private String countryCode;
    @NotNull(message = Messages.NOT_NULL_BIRTH_DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;
    @NotNull(message = Messages.NOT_NULL_START_CAREER_DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startCareerDate;
    @NotNull(message = Messages.NOT_NULL_POSITION)
    private Position position;


    public Player toEntity() {
        return Player.builder()
                .id(id)
                .name(name)
                .countryCode(countryCode)
                .birthDate(birthDate)
                .isActive(true)
                .startCareerDate(startCareerDate)
                .position(position).build();
    }
}
