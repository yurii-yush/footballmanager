package com.codeseek.repository.specification;

import com.codeseek.controller.request.TeamSearchRequest;
import com.codeseek.entity.Player;
import com.codeseek.entity.Team;
import com.codeseek.generated.metamodel.Player_;
import com.codeseek.generated.metamodel.Team_;
import org.springframework.data.jpa.domain.Specification;

public class TeamSpecifications {

    private static Specification<Team> filterById(Long id) {
        return (root, query, cb) -> cb.equal(root.get(Team_.ID), id);
    }
    private static Specification<Team> filterByName(String name) {
        return (root, query, cb) -> cb.like(root.get(Team_.NAME), "%" + name +"%");
    }

    private static Specification<Team> filterByIsActive(Boolean isActive) {
        return (root, query, cb) -> cb.equal(root.get(Team_.IS_ACTIVE), isActive);
    }
    private static Specification<Team> filterByCountry(String countryCode) {
        return (root, query, cb) -> cb.equal(root.get(Team_.COUNTRY_CODE), countryCode);
    }

    private static Specification<Team> filterByBalanceFrom(Long balanceFrom) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(Team_.BALANCE), balanceFrom);
    }

    private static Specification<Team> filterByBalanceTo(Long balanceTo) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(Team_.BALANCE), balanceTo);
    }

    public static Specification<Team> generateQuery(TeamSearchRequest request) {
        Specification<Team> query = GenericSpecifications.alwaysTrue();
        query = query.and(filterByIsActive(request.getIsActive()));

        if (request.getId() != null) {
            query = query.and(filterById(request.getId()));
        }
        if (request.getName() != null) {
            query = query.and(filterByName(request.getName()));
        }
        if (request.getBalanceFrom() != null) {
            query = query.and(filterByBalanceFrom(request.getBalanceFrom()));
        }
        if (request.getBalanceTo() != null) {
            query = query.and(filterByBalanceTo(request.getBalanceTo()));
        }
        if (request.getCountryCode() != null) {
            query = query.and(filterByCountry(request.getCountryCode()));
        }

        return query;
    }
}
