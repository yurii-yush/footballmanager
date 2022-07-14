package com.codeseek.unit.controller;

import com.codeseek.common.Messages;
import com.codeseek.controller.TransferController;
import com.codeseek.controller.dto.request.TransferRequestDTO;
import com.codeseek.controller.dto.response.TransferResponseDTO;
import com.codeseek.controller.request.TransferSearchRequest;
import com.codeseek.entity.Player;
import com.codeseek.entity.Team;
import com.codeseek.entity.enums.Position;
import com.codeseek.service.TransferService;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransferController.class)
public class TransferControllerTests {

    @MockBean
    private TransferService transferService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
    }

    @Test
    public void givenValidSearchRequest_whenSearchTransfer_thenReturnPage_andStatus200() throws Exception {
        //given
        TransferSearchRequest searchRequest = new TransferSearchRequest();
        searchRequest.setPlayer(player);

        Page<TransferResponseDTO> expectedPage = new PageImpl<>(List.of(transferResponse));

        Mockito.when(transferService.searchTransfers(Mockito.any())).thenReturn(expectedPage);

        //when
        mockMvc.perform(
                        get(Messages.TRANSFER_CONTROLLER_URI)
                                .content(objectMapper.writeValueAsString(searchRequest))
                               .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
               .andExpect(content().json(objectMapper.writeValueAsString(expectedPage)));
    }

    @Test
    public void givenValidTransfer_whenSaveTransfer_thenReturnURI_andStatus201() throws Exception {
        //given
        Mockito.when(transferService.saveTransfer(Mockito.any())).thenReturn(transferResponse);
        //when
        mockMvc.perform(
                        post(Messages.TRANSFER_CONTROLLER_URI)
                                .content(objectMapper.writeValueAsString(transferRequest))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(transferResponse)));
    }

    @Test
    public void givenNotValidTransfer_whenSaveTransfer_thenThrowConstraintViolationException_andStatus400() throws Exception {
        //given
        transferRequest.setToTeam(null);

        //when
        mockMvc.perform(
                        post(Messages.TRANSFER_CONTROLLER_URI)
                                .content(objectMapper.writeValueAsString(transferRequest))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(ex -> ex.getResolvedException().getClass().equals(ConstraintViolationException.class));
    }
}
