package com.codeseek.service;

import com.codeseek.controller.dto.request.PlayerRequestDTO;
import com.codeseek.controller.dto.response.PlayerResponseDTO;
import com.codeseek.controller.request.PlayerSearchRequest;
import org.springframework.data.domain.Page;

public interface PlayerService {
    Page<PlayerResponseDTO> searchPlayers(PlayerSearchRequest searchRequest);

    PlayerResponseDTO savePlayer(PlayerRequestDTO playerRequestDTO);

    PlayerResponseDTO updatePlayer(PlayerRequestDTO playerRequestDTO, Long id);

    void deletePlayerById(Long id);
}
