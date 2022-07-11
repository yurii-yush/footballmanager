package com.codeseek.controller.dto.response;

import com.codeseek.entity.enums.Position;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PlayerResponseDTO {

    private Long id;
    private String name;

    private String countryCode;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startCareerDate;
    @Enumerated(EnumType.STRING)
    private Position position;
    private Boolean isActive;
}
