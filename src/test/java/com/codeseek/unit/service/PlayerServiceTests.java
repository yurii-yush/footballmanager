package com.codeseek.unit.service;


import com.codeseek.controller.dto.request.PlayerRequestDTO;
import com.codeseek.controller.dto.response.PlayerResponseDTO;
import com.codeseek.controller.request.PlayerSearchRequest;
import com.codeseek.entity.Player;
import com.codeseek.entity.enums.Position;
import com.codeseek.exception.ResourceNotFoundException;
import com.codeseek.repository.PlayerRepository;
import com.codeseek.service.impl.PlayerServiceImpl;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class PlayerServiceTests {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerServiceImpl playerService;

    private Player player1, player2, player3;
    private PlayerRequestDTO playerRequest1, playerRequest2, playerRequest3;
    private PlayerResponseDTO playerResponse1, playerResponse2, playerResponse3;

    @BeforeEach
    public void setup() {
        playerRequest1 = new PlayerRequestDTO(1L, "Lionel Messi", "AR", LocalDate.parse("1994-07-08"), LocalDate.parse("2011-07-09"), Position.FORWARD);
        playerResponse1 = new PlayerResponseDTO(1L, "Lionel Messi", "AR", LocalDate.parse("1994-07-08"), LocalDate.parse("2011-07-09"), Position.FORWARD, true);
        player1 = new Player(1L, "Lionel Messi", LocalDate.parse("1994-07-08"), LocalDate.parse("2011-07-09"), null, "AR", Position.FORWARD, true);

        playerRequest2 = new PlayerRequestDTO(2L, "Cristiano Ronaldo", "UA", LocalDate.parse("2000-09-30"), LocalDate.parse("2018-05-09"), Position.DEFENDER);
        playerResponse2 = new PlayerResponseDTO(2L, "Cristiano Ronaldo", "UA", LocalDate.parse("2000-09-30"), LocalDate.parse("2018-05-09"), Position.DEFENDER, true);
        player2 = new Player(2L, "Cristiano Ronaldo", LocalDate.parse("2000-09-30"), LocalDate.parse("2018-05-09"), null, "UA", Position.DEFENDER, true);

        playerRequest3 = new PlayerRequestDTO(3L, "Xavi", "GB", LocalDate.parse("2001-07-08"), LocalDate.parse("2019-06-09"), Position.GOALKEEPER);
        playerResponse3 = new PlayerResponseDTO(3L, "Xavi", "GB", LocalDate.parse("2001-07-08"), LocalDate.parse("2019-06-09"), Position.GOALKEEPER, false);
        player3 = new Player(3L, "Xavi", LocalDate.parse("2001-07-08"), LocalDate.parse("2019-06-09"), null, "GB", Position.GOALKEEPER, false);
    }

    @Test
    public void givenValidPosition_whenSearchPlayer_thenReturnPageWithPlayers() {
        //given
        PlayerSearchRequest searchRequest = new PlayerSearchRequest();
        searchRequest.setPosition(Position.DEFENDER);

        Page<Player> pageFromDB = new PageImpl<>(List.of(player2));
        Page<PlayerResponseDTO> expectedPage = new PageImpl<>(List.of(playerResponse2));

        //when
        when(playerRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(pageFromDB);

        Page<PlayerResponseDTO> actualPage = playerService.searchPlayers(searchRequest);

        //then
        assertThat(actualPage, equalTo(expectedPage));
    }

    @Test
    public void givenIsActive_whenSearchPlayer_thenReturnPageWithNotActivePlayers() {
        //given
        PlayerSearchRequest searchRequest = new PlayerSearchRequest();
        searchRequest.setIsActive(false);

        Page<Player> pageFromDB = new PageImpl<>(List.of(player3));
        Page<PlayerResponseDTO> expectedPage = new PageImpl<>(List.of(playerResponse3));

        //when
        when(playerRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(pageFromDB);

        Page<PlayerResponseDTO> actualPage = playerService.searchPlayers(searchRequest);

        //then
        assertThat(actualPage, equalTo(expectedPage));
    }

    @Test
    public void givenValidPlayer_whenSavePlayer_thenSaveAndReturnPlayer() throws Exception {
        //given
        when(playerRepository.saveAndFlush(Mockito.any()))
                .thenReturn(player1);

        //when
        PlayerResponseDTO actualPlayer = playerService.savePlayer(playerRequest1);

        //then
        assertThat(playerResponse1, equalTo(actualPlayer));
    }

    @Test
    public void givenNotValidPlayerId_whenDeletePlayer_thenThrowsResourceNotFoundException() throws Exception {
        //given
        long teamId = 6L;

        //then
        assertThrows(ResourceNotFoundException.class, () -> playerService.deletePlayerById(teamId));
    }

    @Test
    public void givenValidPlayer_whenUpdatePlayer_thenUpdateAndReturnPlayer() {
        //given
        String newName = "Yura Yushchyshyn";
        when(playerRepository.findById(Mockito.any()))
                .thenReturn(Optional.of(player1));
        player1.setName(newName);

        when(playerRepository.saveAndFlush(Mockito.any()))
                .thenReturn(player1);
        playerRequest1.setName(newName);

        //when
        PlayerResponseDTO actualPlayer = playerService.updatePlayer(playerRequest1, playerRequest1.getId());
        playerResponse1.setName(newName);
        //then
        assertThat(playerResponse1, equalTo(actualPlayer));
    }
}
