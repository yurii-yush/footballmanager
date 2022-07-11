package com.codeseek.integration.controller;

import com.codeseek.common.Messages;
import com.codeseek.controller.dto.response.PlayerResponseDTO;
import com.codeseek.controller.request.PlayerSearchRequest;
import com.codeseek.entity.Player;
import com.codeseek.entity.enums.Position;
import com.codeseek.integration.AbstractIT;
import com.codeseek.repository.PlayerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PlayerIntegrationTests extends AbstractIT {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenValidPlayer_whenSavePlayer_thenReturnURI_andStatus201() throws Exception {
        //given
        long maxId = getMaxId() + 1;

        Player expectedPlayer = new Player(maxId, "Radamel Falcao", LocalDate.parse("1994-07-08"), LocalDate.parse("2011-07-09"), null, "AR", Position.FORWARD, true);

        URI location = URI.create(String.format(Messages.CREATED_PLAYER_URI, maxId));

        //when
        mockMvc.perform(
                        post(Messages.PLAYER_CONTROLLER_URI)
                                .content(objectMapper.writeValueAsString(expectedPlayer.toDTO()))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(location)));

        //then
        Player actualPlayer = playerRepository.findById(expectedPlayer.getId()).get();
        actualPlayer.setTeam(null);
        assertThat(expectedPlayer, equalTo(actualPlayer));
    }

    @Test
    public void givenPlayerId_whenDeletePlayer_thenDeletePlayer_andStatus200() throws Exception {
        //given
        addPlayers();
        Player expectedPlayer = getSinglePlayer();

        //when
        mockMvc.perform(
                        delete(Messages.PLAYER_CONTROLLER_URI + Messages.ID_MAPPING, expectedPlayer.getId()))
                .andExpect(status().isOk());

        //then
        Player actualPlayer = playerRepository.findById(expectedPlayer.getId()).get();
        assertThat(actualPlayer.getIsActive(), equalTo(false));
    }

    @Test
    public void givenValidPosition_whenSearchPlayer_thenReturnListOfPlayers_andStatus200() throws Exception {
        //given
        Position position = Position.FORWARD;
        PlayerSearchRequest searchRequest = new PlayerSearchRequest();
        searchRequest.setPosition(position);

        //when
        List<Player> allPlayers = addPlayers();
        List<PlayerResponseDTO> expectedPlayers = filterPlayersByPosition(allPlayers, position);

        //then
        Page<PlayerResponseDTO> expectedPage = new PageImpl<>(expectedPlayers);

        //when
        mockMvc.perform(
                        get(Messages.PLAYER_CONTROLLER_URI)
                                .content(objectMapper.writeValueAsString(searchRequest))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedPage)));
    }

    @Test
    public void givenValidPlayer_whenUpdatePlayer_thenReturnURI_andStatus200() throws Exception {
        //given
        addPlayers();
        Player expectedPlayer = getSinglePlayer();
        expectedPlayer.setPosition(Position.DEFENDER);

        //when
        mockMvc.perform(
                        put(Messages.PLAYER_CONTROLLER_URI + Messages.ID_MAPPING, expectedPlayer.getId())
                                .content(objectMapper.writeValueAsString(expectedPlayer.toDTO()))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedPlayer.toDTO())));

        //then
        Player actualPlayer = playerRepository.findById(expectedPlayer.getId()).get();
        actualPlayer.setTeam(null);
        assertThat(expectedPlayer, equalTo(actualPlayer));
    }

    private List<PlayerResponseDTO> filterPlayersByPosition(List<Player> allPlayers, Position position) {
        return allPlayers.stream()
                .filter(player -> player.getPosition().equals(position))
                .map(Player::toDTO)
                .collect(Collectors.toList());
    }

    private List<Player> addPlayers() {
        Player player1 = new Player(1L, "Lionel Messi", LocalDate.parse("1994-07-08"), LocalDate.parse("2011-07-09"), null, "AR", Position.FORWARD, true);
        Player player2 = new Player(2L, "Cristiano Ronaldo", LocalDate.parse("2000-09-30"), LocalDate.parse("2018-05-09"), null, "UA", Position.DEFENDER, true);
        Player player3 = new Player(3L, "Xavi", LocalDate.parse("2019-06-09"), LocalDate.parse("2019-06-09"), null, "GB", Position.MIDFIELDER, true);
        Player player4 = new Player(4L, "Andres Iniesta", LocalDate.parse("2005-01-08"), LocalDate.parse("2010-07-09"), null, "ES", Position.GOALKEEPER, true);
        Player player5 = new Player(5L, "Zlatan Ibrahimovic", LocalDate.parse("1998-07-03"), LocalDate.parse("2015-07-09"), null, "ES", Position.FORWARD, true);
        List<Player> Players = List.of(player1, player2, player3, player4, player5);
        playerRepository.deleteAll();

        return playerRepository.saveAllAndFlush(Players);
    }

    private Player getSinglePlayer() {
        return playerRepository.findAll().get(0);
    }

    private long getMaxId() {
        Player player = new Player(1L, "Lionel Messi", LocalDate.parse("1994-07-08"), LocalDate.parse("2011-07-09"), null, "AR", Position.FORWARD, true);

        return playerRepository.saveAndFlush(player).getId();
    }
}