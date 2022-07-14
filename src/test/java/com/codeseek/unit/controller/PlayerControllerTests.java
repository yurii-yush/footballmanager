package com.codeseek.unit.controller;

import com.codeseek.common.Messages;
import com.codeseek.common.Pagination;
import com.codeseek.controller.PlayerController;
import com.codeseek.controller.dto.request.PlayerRequestDTO;
import com.codeseek.controller.dto.response.PlayerResponseDTO;
import com.codeseek.controller.request.PlayerSearchRequest;
import com.codeseek.entity.enums.Position;
import com.codeseek.service.PlayerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlayerController.class)
public class PlayerControllerTests {

    @MockBean
    private PlayerService playerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private PlayerResponseDTO playerResponse;
    private PlayerRequestDTO playerRequest;

    @BeforeEach
    public void setup() {
        playerRequest = new PlayerRequestDTO(1L, "Lionel Messi", "AR", LocalDate.parse("1994-07-08"), LocalDate.parse("2011-07-09"), Position.FORWARD);
        playerResponse = new PlayerResponseDTO(1L, "Lionel Messi", "AR", LocalDate.parse("1994-07-08"), LocalDate.parse("2011-07-09"), Position.FORWARD, true);
    }

    @Test
    public void givenValidPlayer_whenSavePlayer_thenReturnURI_andStatus201() throws Exception {
        //given
        Mockito.when(playerService.savePlayer(Mockito.any())).thenReturn(playerResponse);

        //when
        mockMvc.perform(
                        post(Messages.PLAYER_CONTROLLER_URI)
                                .content(objectMapper.writeValueAsString(playerRequest))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(playerResponse)));
    }

    @Test
    public void givenValidSearchRequest_whenSearchPlayer_thenReturnPage_andStatus200() throws Exception {
        //given
        PageRequest pagination = PageRequest.of(Pagination.DEFAULT_PAGE, Pagination.DEFAULT_LIMIT);
        PlayerSearchRequest searchRequest = new PlayerSearchRequest();
        searchRequest.setPosition(Position.FORWARD);
        Page<PlayerResponseDTO> expectedPage = new PageImpl<>(List.of(playerResponse), pagination, 1);

        Mockito.when(playerService.searchPlayers(Mockito.any())).thenReturn(expectedPage);

        //when
        mockMvc.perform(
                        get(Messages.PLAYER_CONTROLLER_URI)
                                .content(objectMapper.writeValueAsString(searchRequest))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedPage)));
    }

    @Test
    public void givenPlayerID_whenDeletePlayer_thenReturnStatus200() throws Exception {
        //given
        Long id = playerRequest.getId();

        //when
        mockMvc.perform(
                        delete(Messages.PLAYER_CONTROLLER_URI + Messages.ID_MAPPING, id))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNotValidBirthDate_whenSavePlayer_thenThrowConstraintViolationException_andStatus400() throws Exception {
        //given
        playerRequest.setBirthDate(null);

        //when
        mockMvc.perform(
                        post(Messages.PLAYER_CONTROLLER_URI)
                                .content(objectMapper.writeValueAsString(playerRequest))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(ex -> ex.getResolvedException().getClass().equals(ConstraintViolationException.class));
    }

    @Test
    public void givenValidPlayer_whenUpdatePlayer_thenUpdatedPlayer_andStatus200() throws Exception {
        //given
        Mockito.when(playerService.updatePlayer(Mockito.any(), Mockito.any())).thenReturn(playerResponse);

        //when
        mockMvc.perform(
                        put(Messages.PLAYER_CONTROLLER_URI + Messages.ID_MAPPING, playerRequest.getId())
                                .content(objectMapper.writeValueAsString(playerRequest))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNotValidStartCareer_whenUpdatePlayer_thenThrowConstraintViolationException_andStatus400() throws Exception {
        //given
        playerRequest.setStartCareerDate(null);

        //when
        mockMvc.perform(
                        put(Messages.PLAYER_CONTROLLER_URI + Messages.ID_MAPPING, playerRequest.getId())
                                .content(objectMapper.writeValueAsString(playerRequest))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(ex -> ex.getResolvedException().getClass().equals(ConstraintViolationException.class));
    }
}
