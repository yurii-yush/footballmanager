package com.codeseek.unit.service;


import com.codeseek.controller.dto.request.TeamRequestDTO;
import com.codeseek.controller.dto.response.PlayerResponseDTO;
import com.codeseek.controller.dto.response.TeamResponseDTO;
import com.codeseek.controller.request.TeamSearchRequest;
import com.codeseek.entity.Team;
import com.codeseek.exception.ResourceNotFoundException;
import com.codeseek.repository.TeamRepository;
import com.codeseek.service.impl.TeamServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class TeamServiceTests {

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamServiceImpl teamService;

    private TeamResponseDTO responseTeam1, responseTeam2, responseTeam3, responseTeam4;
    private TeamRequestDTO requestTeam1, requestTeam2, requestTeam3, requestTeam4;
    private Team team1, team2, team3, team4;

    @BeforeEach
    public void setup() {
        requestTeam1 = new TeamRequestDTO(1L, "Arsenal London", "GB", BigDecimal.valueOf(100000), BigDecimal.valueOf(2.0));
        responseTeam1 = new TeamResponseDTO(1L, "Arsenal London", "GB", BigDecimal.valueOf(100000), BigDecimal.valueOf(2.0), new ArrayList<>(), true);

        requestTeam2 = new TeamRequestDTO(2L, "Newcastle United", "GB", BigDecimal.valueOf(2000000), BigDecimal.valueOf(2.5));
        responseTeam2 = new TeamResponseDTO(2L, "Newcastle United", "GB", BigDecimal.valueOf(2000000), BigDecimal.valueOf(2.5), new ArrayList<>(), true);

        requestTeam3 = new TeamRequestDTO(3L, "Aston Villa", "GB", BigDecimal.valueOf(500000), BigDecimal.valueOf(1.5));
        responseTeam3 = new TeamResponseDTO(3L, "Aston Villa", "GB", BigDecimal.valueOf(500000), BigDecimal.valueOf(1.5), new ArrayList<>(), true);

        requestTeam4 = new TeamRequestDTO(4L, "Athletic Bilbao", "ES", BigDecimal.valueOf(4000000), BigDecimal.valueOf(9.0));
        responseTeam4 = new TeamResponseDTO(4L, "Athletic Bilbao", "ES", BigDecimal.valueOf(4000000), BigDecimal.valueOf(9.0), new ArrayList<>(), false);
        team1 = new Team(1L, "Arsenal London", "GB", BigDecimal.valueOf(100000), BigDecimal.valueOf(2.0), new ArrayList<>(), true);
        team2 = new Team(2L, "Newcastle United", "GB", BigDecimal.valueOf(2000000), BigDecimal.valueOf(2.5), new ArrayList<>(), true);
        team3 = new Team(3L, "Aston Villa", "GB", BigDecimal.valueOf(500000), BigDecimal.valueOf(1.5), new ArrayList<>(), true);
        team4 = new Team(4L, "Athletic Bilbao", "ES", BigDecimal.valueOf(4000000), BigDecimal.valueOf(9.0), new ArrayList<>(), false);
    }

    @Test
    public void givenValidCountryCode_whenSearchTeam_thenReturnPageWithTeams() {
        //given
        TeamSearchRequest searchRequest = new TeamSearchRequest();
        searchRequest.setCountryCode("GB");

        Page<Team> pageFromDB = new PageImpl<>(List.of(team1, team2, team3));
        Page<TeamResponseDTO> expectedPage = new PageImpl<>(List.of(responseTeam1, responseTeam2, responseTeam3));

        //when
        when(teamRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(pageFromDB);

        Page<TeamResponseDTO> actualPage = teamService.searchTeams(searchRequest);

        //then
        assertThat(actualPage, equalTo(expectedPage));
    }

    @Test
    public void givenIsActive_whenSearchTeam_thenReturnPageWithDeadTeams() {
        //given
        TeamSearchRequest searchRequest = new TeamSearchRequest();
        searchRequest.setIsActive(false);

        Page<Team> pageFromDB = new PageImpl<>(List.of(team4));
        Page<TeamResponseDTO> expectedPage = new PageImpl<>(List.of(responseTeam4));

        //when
        when(teamRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(pageFromDB);

        Page<TeamResponseDTO> actualPage = teamService.searchTeams(searchRequest);

        //then
        assertThat(actualPage, equalTo(expectedPage));
    }

    @Test
    public void givenValidTeam_whenSaveTeam_thenSaveAndReturnTeam() throws Exception {
        //given
        when(teamRepository.saveAndFlush(Mockito.any()))
                .thenReturn(team1);

        //when
        TeamResponseDTO actualTeam = teamService.saveTeam(requestTeam1);

        //then
        assertThat(responseTeam1, equalTo(actualTeam));
    }

    @Test
    public void givenNotValidTeamId_whenDeleteTeam_thenThrowResourceNotFoundException() throws Exception {
        //given
        long teamId = 6L;

        //then
        assertThrows(ResourceNotFoundException.class, () -> teamService.deleteTeamById(teamId));
    }

    @Test
    public void givenNotValidTeamId_whenUpdateTeam_thenThrowResourceNotFoundException() {
        //when
        when(teamRepository.findById(Mockito.any()))
                .thenReturn(Optional.empty());

        //then
        assertThrows(ResourceNotFoundException.class, () -> teamService.updateTeam(requestTeam1, requestTeam1.getId()));
    }

    @Test
    public void givenValidTeam_whenUpdateTeam_thenUpdateAndReturnTeam() {
        //given
        String newCountryCode = "PL";
        when(teamRepository.findById(Mockito.any()))
                .thenReturn(Optional.of(team1));

        team1.setCountryCode(newCountryCode);
        when(teamRepository.saveAndFlush(Mockito.any()))
                .thenReturn(team1);

        requestTeam1.setCountryCode(newCountryCode);

        //when
        TeamResponseDTO actualTeam = teamService.updateTeam(requestTeam1, requestTeam1.getId());
        responseTeam1.setCountryCode(newCountryCode);
        //then
        assertThat(responseTeam1, equalTo(actualTeam));
    }
}