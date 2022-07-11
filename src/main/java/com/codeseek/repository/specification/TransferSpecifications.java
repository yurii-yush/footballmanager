package com.codeseek.repository.specification;


import com.codeseek.controller.request.TransferSearchRequest;
import com.codeseek.entity.Player;
import com.codeseek.entity.Team;
import com.codeseek.entity.Transfer;
import com.codeseek.entity.Transfer;
import com.codeseek.generated.metamodel.Transfer_;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class TransferSpecifications {
    private static Specification<Transfer> filterById(Long id) {
        return (root, query, cb) -> cb.equal(root.get(Transfer_.ID), id);
    }
    private static Specification<Transfer> filterByPlayer(Player player) {
        return (root, query, cb) -> cb.equal(root.get(Transfer_.PLAYER), player);
    }

    private static Specification<Transfer> filterByFromTeam(Team fromTeam) {
        return (root, query, cb) -> cb.equal(root.get(Transfer_.FROM_TEAM), fromTeam);
    }

    private static Specification<Transfer> filterByToTeam(Team toTeam) {
        return (root, query, cb) -> cb.equal(root.get(Transfer_.TO_TEAM), toTeam);
    }
    private static Specification<Transfer> filterByPriceFrom(Long priceFrom) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(Transfer_.PRICE), priceFrom);
    }

    private static Specification<Transfer> filterByPriceTo(Long priceTo) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(Transfer_.PRICE), priceTo);
    }

    private static Specification<Transfer> filterByDateFrom(LocalDateTime dateFrom) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(Transfer_.DATETIME), dateFrom);
    }

    private static Specification<Transfer> filterByDateTo(LocalDateTime dateTo) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(Transfer_.DATETIME), dateTo);
    }

    public static Specification<Transfer> generateQuery(TransferSearchRequest request) {

        Specification<Transfer> query = GenericSpecifications.alwaysTrue();

        if (request.getId() != null) {
            query = query.and(filterById(request.getId()));
        }
        if (request.getFromTeam() != null) {
            query = query.and(filterByFromTeam(request.getFromTeam()));
        }
        if (request.getToTeam() != null) {
            query = query.and(filterByToTeam(request.getToTeam()));
        }
        if (request.getPriceFrom() != null) {
            query = query.and(filterByPriceFrom(request.getPriceFrom()));
        }
        if (request.getPriceTo() != null) {
            query = query.and(filterByPriceTo(request.getPriceTo()));
        }
        if (request.getDateFrom() != null) {
            query = query.and(filterByDateFrom(request.getDateFrom()));
        }
        if (request.getDateTo() != null) {
            query = query.and(filterByDateTo(request.getDateTo()));
        }
        if (request.getPlayer() != null) {
            query = query.and(filterByPlayer(request.getPlayer()));
        }

        return query;
    }
}
