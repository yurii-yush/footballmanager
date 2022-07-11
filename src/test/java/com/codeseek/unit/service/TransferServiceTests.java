package com.codeseek.unit.service;

import com.codeseek.controller.dto.request.TransferRequestDTO;
import com.codeseek.controller.dto.response.TransferResponseDTO;
import com.codeseek.entity.Player;
import com.codeseek.entity.Team;
import com.codeseek.entity.Transfer;
import com.codeseek.entity.enums.Position;
import com.codeseek.exception.ResourceNotFoundException;
import com.codeseek.exception.TransferValidationException;
import com.codeseek.repository.PlayerRepository;
import com.codeseek.repository.TeamRepository;
import com.codeseek.repository.TransferRepository;
import com.codeseek.service.impl.TransferServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransferServiceTests {

    @Mock
    private TransferRepository transferRepository;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TransferServiceImpl transferService;

    private Transfer transfer;
    private TransferRequestDTO transferRequest;
    private TransferResponseDTO transferResponse;
    private Team fromTeam, toTeam;
    private Player player;

    @BeforeEach
    public void setup() {
        fromTeam = new Team(1L, "Arsenal London", "GB", BigDecimal.valueOf(200000), BigDecimal.valueOf(2.0), null, true);
        toTeam = new Team(2L, "Karpaty Lviv", "UA", BigDecimal.valueOf(500000), BigDecimal.valueOf(4.0), new ArrayList<>(), true);
        player = new Player(1L, "Lionel Messi", LocalDate.parse("1994-07-08"), LocalDate.parse("2011-07-09"), fromTeam, "AR", Position.FORWARD, true);

        transferRequest = new TransferRequestDTO(1L, player, toTeam);
        transferResponse = new TransferResponseDTO(1L, player.getName(), fromTeam.getName(), toTeam.getName(), BigDecimal.valueOf(50000), LocalDateTime.parse("2022-07-10T20:36:30.454687"));
        transfer = new Transfer(1L, player, fromTeam, toTeam, BigDecimal.valueOf(50000), LocalDateTime.parse("2022-07-10T20:36:30.454687"));
    }

    @Test
    public void givenTeamWithTinyBalance_whenSaveTransfer_thenThrowsTransferValidationException() throws Exception {
        //given
        when(playerRepository.findById(Mockito.any()))
                .thenReturn(Optional.ofNullable(player));

        when(teamRepository.findById(Mockito.any()))
                .thenReturn(Optional.ofNullable(fromTeam));

        //then
        assertThrows(TransferValidationException.class, () -> transferService.saveTransfer(transferRequest));
    }

    @Test
    public void givenNotValidPlayer_whenSaveTransfer_thenThrowsResourceNotFoundException() throws Exception {
        //given
        when(playerRepository.findById(Mockito.any()))
                .thenReturn(Optional.empty());

        //then
        assertThrows(ResourceNotFoundException.class, () -> transferService.saveTransfer(transferRequest));
    }

    @Test
    public void givenNotValidToTeam_whenSaveTransfer_thenThrowsResourceNotFoundException() throws Exception {
        //given
        when(playerRepository.findById(Mockito.any()))
                .thenReturn(Optional.ofNullable(player));

        when(teamRepository.findById(Mockito.any()))
                .thenReturn(Optional.empty());

        //then
        assertThrows(ResourceNotFoundException.class, () -> transferService.saveTransfer(transferRequest));
    }
}
