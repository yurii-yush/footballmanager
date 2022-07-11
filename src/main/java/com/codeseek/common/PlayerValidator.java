package com.codeseek.common;


import com.codeseek.controller.dto.request.PlayerRequestDTO;
import com.codeseek.exception.PlayerValidationException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

public class PlayerValidator {

    public static final int MIN_PLAYER_AGE = 16;

    public static void validate(PlayerRequestDTO playerRequestDTO) throws PlayerValidationException{
        StringBuilder stringBuilder = new StringBuilder();

        if (!isAgeValid(playerRequestDTO.getBirthDate())) {
            stringBuilder.append(String.format(Messages.REQUIRED_PLAYER_AGE, MIN_PLAYER_AGE));
        }
        if (!isStartCareerDateValid(playerRequestDTO.getStartCareerDate())) {
            stringBuilder.append(Messages.NOT_VALID_START_CAREER_DATE);
        }
        if (!isExperienceValid(playerRequestDTO.getBirthDate(), playerRequestDTO.getStartCareerDate())) {
            stringBuilder.append(String.format(Messages.REQUIRED_PLAYER_EXPERIENCE, MIN_PLAYER_AGE));
        }

        if (stringBuilder.length() > 0) {
            throw new PlayerValidationException(stringBuilder.toString());
        }
    }

    private static boolean isStartCareerDateValid(LocalDate startCareerDate) {
        return startCareerDate.isBefore(LocalDate.now());
    }

    private static boolean isExperienceValid(LocalDate birthDate, LocalDate startCareerDate) {
        int experience = Period.between(birthDate, startCareerDate).getYears();

        return experience >= MIN_PLAYER_AGE;
    }

    private static boolean isAgeValid(LocalDate birthDate) {
        LocalDate currentDate = LocalDate.now();
        int age = Period.between(birthDate, currentDate).getYears();

        return age >= MIN_PLAYER_AGE;
    }
}
