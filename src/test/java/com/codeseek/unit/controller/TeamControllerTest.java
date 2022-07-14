package com.codeseek.unit.controller;

import com.codeseek.common.Messages;
import com.codeseek.controller.TeamController;
import com.codeseek.controller.dto.request.TeamRequestDTO;
import com.codeseek.controller.dto.response.TeamResponseDTO;
import com.codeseek.controller.request.TeamSearchRequest;
import com.codeseek.service.TeamService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TeamController.class)
public class TeamControllerTest {

    @MockBean
    private TeamService teamService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private TeamRequestDTO requestTeam;
    private TeamResponseDTO responseTeam;

    @BeforeEach
    public void setup() {
        requestTeam = new TeamRequestDTO(1L, "Arsenal London", "GB", BigDecimal.valueOf(100000), BigDecimal.valueOf(2.0));
        responseTeam = new TeamResponseDTO(1L, "Arsenal London", "GB", BigDecimal.valueOf(100000), BigDecimal.valueOf(2.0), new ArrayList<>(), true);

    }

    @Test
    public void givenValidSearchRequest_whenSearchTeam_thenReturnPage_andStatus200() throws Exception {
        //given
        TeamSearchRequest searchRequest = new TeamSearchRequest();
        searchRequest.setCountryCode("UA");
        Page<TeamResponseDTO> expectedPage = new PageImpl<>(List.of(responseTeam));

        Mockito.when(teamService.searchTeams(Mockito.any())).thenReturn(expectedPage);

        //when
        mockMvc.perform(
                        get(Messages.TEAM_CONTROLLER_URI)
                                .content(objectMapper.writeValueAsString(searchRequest))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedPage)));
    }

    @Test
    public void givenNotValidCountryInSearchRequest_whenSearchTeam_thenThrowConstraintViolationException_andStatus400() throws Exception {
        //given
        TeamSearchRequest searchRequest = new TeamSearchRequest();
        searchRequest.setCountryCode("UK");

        //when
        mockMvc.perform(
                        get(Messages.TEAM_CONTROLLER_URI)
                                .content(objectMapper.writeValueAsString(searchRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(ex -> ex.getResolvedException().getClass().equals(ConstraintViolationException.class));
    }

    @Test
    public void givenTeamId_whenDeleteTeam_thenReturnStatus200() throws Exception {
        //given
        Long id = requestTeam.getId();

        //when
        mockMvc.perform(
                        delete(Messages.TEAM_CONTROLLER_URI + Messages.ID_MAPPING, id))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNotValidBalance_whenSaveTeam_thenThrowConstraintViolationException_andStatus400() throws Exception {
        //given
        requestTeam.setBalance(BigDecimal.valueOf(1000));

        //when
        mockMvc.perform(
                        post(Messages.TEAM_CONTROLLER_URI)
                                .content(objectMapper.writeValueAsString(requestTeam))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(ex -> ex.getResolvedException().getClass().equals(ConstraintViolationException.class));

    }

    @Test
    public void givenValidTeam_whenSaveTeam_thenReturnURI_andStatus201() throws Exception {
        //given
        Mockito.when(teamService.saveTeam(Mockito.any())).thenReturn(responseTeam);

        //when
        mockMvc.perform(
                        post(Messages.TEAM_CONTROLLER_URI)
                                .content(objectMapper.writeValueAsString(requestTeam))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(responseTeam)));
    }

    @Test
    public void givenNotValidCountry_whenUpdateTeam_thenThrowConstraintViolationException_andStatus400() throws Exception {
        //given
        requestTeam.setCountryCode("UAE");

        //when
        mockMvc.perform(
                        put(Messages.TEAM_CONTROLLER_URI + Messages.ID_MAPPING, requestTeam.getId())
                                .content(objectMapper.writeValueAsString(requestTeam))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(ex -> ex.getResolvedException().getClass().equals(ConstraintViolationException.class));

    }

    @Test
    public void givenValidTeam_whenUpdateTeam_thenUpdatedTeam_andStatus200() throws Exception {
        //given
        Mockito.when(teamService.updateTeam(Mockito.any(), Mockito.any())).thenReturn(responseTeam);

        //when
        mockMvc.perform(
                        put(Messages.TEAM_CONTROLLER_URI + Messages.ID_MAPPING, requestTeam.getId())
                                .content(objectMapper.writeValueAsString(requestTeam))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(requestTeam)));
    }
}