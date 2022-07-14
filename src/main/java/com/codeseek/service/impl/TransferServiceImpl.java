package com.codeseek.service.impl;

import com.codeseek.common.Messages;
import com.codeseek.common.Pagination;
import com.codeseek.controller.dto.request.TransferRequestDTO;
import com.codeseek.controller.dto.response.TransferResponseDTO;
import com.codeseek.controller.request.TransferSearchRequest;
import com.codeseek.entity.Player;
import com.codeseek.entity.Team;
import com.codeseek.entity.Transfer;
import com.codeseek.exception.ResourceNotFoundException;
import com.codeseek.exception.TransferValidationException;
import com.codeseek.repository.PlayerRepository;
import com.codeseek.repository.TeamRepository;
import com.codeseek.repository.TransferRepository;
import com.codeseek.repository.specification.TransferSpecifications;
import com.codeseek.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransferServiceImpl implements TransferService, Pagination {

    public static final BigDecimal TRANSFER_COEFFICIENT = BigDecimal.valueOf(100_000);
    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<TransferResponseDTO> searchTransfers(TransferSearchRequest searchRequest) {
        Specification<Transfer> query = TransferSpecifications.generateQuery(searchRequest);
        PageRequest page = PageRequest.of(Optional.ofNullable(searchRequest.getPage()).orElse(DEFAULT_PAGE), Optional.ofNullable(searchRequest.getLimit()).orElse(DEFAULT_LIMIT));
        Page<Transfer> transfers = transferRepository.findAll(query, page);

        return getTransferDTOList(transfers);
    }

    @Override
    @Transactional
    public TransferResponseDTO saveTransfer(TransferRequestDTO transferRequestDTO) {
        Transfer transfer = transferRequestToEntity(transferRequestDTO);

        validateTransfer(transfer);

        Transfer savedTransfer = transferRepository.save(transfer);
        makeTransfer(transfer);

        return savedTransfer.toDTO();
    }

    private void validateTransfer(Transfer transfer) {
        StringBuilder stringBuilder = new StringBuilder();
        if (!isBuyerBalanceValid(transfer)) {
            stringBuilder.append(String.format(Messages.NOT_ENOUGH_MONEY_ON_BALANCE, transfer.getToTeam().getBalance(), getPlayerTransferPriceWithCommission(transfer.getPlayer())));
        }
        if (transfer.getToTeam().equals(transfer.getFromTeam())){
            stringBuilder.append(Messages.CHANGE_ANOTHER_TO_TEAM);
        }
        if (!transfer.getPlayer().getIsActive()){
            stringBuilder.append(Messages.PLAYER_FINISHED_CAREER);
        }
        if (!transfer.getToTeam().getIsActive()){
            stringBuilder.append(Messages.TEAM_FINISHED_CAREER);
        }

        if (stringBuilder.length() > 0){
            throw new TransferValidationException(stringBuilder.toString());
        }
    }

    private Transfer transferRequestToEntity(TransferRequestDTO transferRequestDTO) {
        Player player = getPlayerById(transferRequestDTO.getPlayer().getId());
        Team toTeam = getTeamById(transferRequestDTO.getToTeam().getId());

        return Transfer.builder()
                .player(player)
                .fromTeam(player.getTeam())
                .toTeam(toTeam)
                .price(getPlayerTransferPriceWithCommission(player))
                .datetime(LocalDateTime.now()).build();
    }

    private Player getPlayerById(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(Messages.PLAYER_ID_NOT_FOUND, id)));
    }

    private Team getTeamById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(Messages.TEAM_ID_NOT_FOUND, id)));
    }

    private void makeTransfer(Transfer transfer) {
        Player player = transfer.getPlayer();
        player.setTeam(transfer.getToTeam());

        if (transfer.getFromTeam()!= null) {
            depositMoneyOnTeamBalance(transfer.getFromTeam(), transfer.getPrice());
            withdrawMoneyFromTeamBalance(transfer.getToTeam(), transfer.getPrice());
        }
    }

    private void withdrawMoneyFromTeamBalance(Team team, BigDecimal price) {
        BigDecimal withdrawBalance = team.getBalance().subtract(price);
        team.setBalance(withdrawBalance);
    }

    private void depositMoneyOnTeamBalance(Team team, BigDecimal price) {
        BigDecimal depositBalance = team.getBalance().add(price);
        team.setBalance(depositBalance);
    }

    private boolean isBuyerBalanceValid(Transfer transfer) {
        return transfer.getToTeam().getBalance().compareTo(transfer.getPrice()) >= 0;
    }

    private BigDecimal getPlayerTransferPriceWithCommission(Player player) {
         return  getPlayerTransferPrice(player).multiply(getTotalTeamCommission(player.getTeam()));
    }

    private BigDecimal getTotalTeamCommission(Team team) {
        if (team == null){
            return BigDecimal.ZERO;
        }

        return BigDecimal.ONE.add(team.getCommission().divide(new BigDecimal(100), 3, RoundingMode.DOWN));// 1 + commission/100 = coff like 1.05
    }

    private BigDecimal getPlayerTransferPrice(Player player) {
        if (player.getTeam() == null) {
            return BigDecimal.ZERO;
        }

        LocalDate currentDate = LocalDate.now();
        int playerAge = Period.between(player.getBirthDate(), currentDate).getYears();
        Period period = Period.between(player.getStartCareerDate(), currentDate);
        int experienceMonths = (period.getYears()*12) + period.getMonths();

        return TRANSFER_COEFFICIENT.multiply(new BigDecimal(experienceMonths)).divide(new BigDecimal(playerAge), 2, RoundingMode.DOWN);
    }

    private Page<TransferResponseDTO> getTransferDTOList(Page<Transfer> transfers) {

        return new PageImpl<>(transfers.stream()
                .map(Transfer::toDTO)
                .collect(Collectors.toList()));
    }
}
