package com.codeseek.repository.specification;


import com.codeseek.controller.request.PlayerSearchRequest;
import com.codeseek.entity.Player;
import com.codeseek.entity.Team;
import com.codeseek.entity.enums.Position;
import com.codeseek.generated.metamodel.Player_;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class PlayerSpecifications {

    private static Specification<Player> filterById(Long id) {
        return (root, query, cb) -> cb.equal(root.get(Player_.ID), id);
    }
    private static Specification<Player> filterByName(String name) {
        return (root, query, cb) -> cb.equal(root.get(Player_.NAME), name);
    }

    private static Specification<Player> filterByIsActive(Boolean isActive) {
        return (root, query, cb) -> cb.equal(root.get(Player_.IS_ACTIVE), isActive);
    }
    private static Specification<Player> filterByTeam(Team team) {
        return (root, query, cb) -> cb.equal(root.get(Player_.TEAM), team);
    }

    private static Specification<Player> filterByPosition(Position position) {
        return (root, query, cb) -> cb.equal(root.get(Player_.POSITION), position);
    }

    private static Specification<Player> filterByDateOfBirthFrom(LocalDate birthDateFrom) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(Player_.BIRTH_DATE), birthDateFrom);
    }

    private static Specification<Player> filterByDateOfBirthTo(LocalDate birthDateTo) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(Player_.BIRTH_DATE), birthDateTo);
    }
    private static Specification<Player> filterByDateOfStartCareerFrom(LocalDate startCareerDateFrom) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(Player_.START_CAREER_DATE), startCareerDateFrom);
    }

    private static Specification<Player> filterByDateOfStartCareerTo(LocalDate startCareerDateTo) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(Player_.START_CAREER_DATE), startCareerDateTo);
    }

    private static Specification<Player> filterByCountry(String countryCode) {
        return (root, query, cb) -> cb.equal(root.get(Player_.COUNTRY_CODE), countryCode);
    }

    public static Specification<Player> generateQuery(PlayerSearchRequest request) {
        Specification<Player> query = GenericSpecifications.alwaysTrue();
        query = query.and(filterByIsActive(request.getIsActive()));

        if (request.getId() != null) {
            query = query.and(filterById(request.getId()));
        }
        if (request.getName() != null) {
            query = query.and(filterByName(request.getName()));
        }
        if (request.getTeam() != null) {
            query = query.and(filterByTeam(request.getTeam()));
        }
        if (request.getPosition() != null) {
            query = query.and(filterByPosition(request.getPosition()));
        }
        if (request.getBirthDateFrom() != null) {
            query = query.and(filterByDateOfBirthFrom(request.getBirthDateFrom()));
        }
        if (request.getBirthDateTo() != null) {
            query = query.and(filterByDateOfBirthTo(request.getBirthDateTo()));
        }
        if (request.getStartCareerDateFrom() != null) {
            query = query.and(filterByDateOfStartCareerFrom(request.getStartCareerDateFrom()));
        }
        if (request.getStartCareerDateTo() != null) {
            query = query.and(filterByDateOfStartCareerTo(request.getStartCareerDateTo()));
        }
        if (request.getCountryCode() != null) {
            query = query.and(filterByCountry(request.getCountryCode()));
        }

        return query;
    }
}
