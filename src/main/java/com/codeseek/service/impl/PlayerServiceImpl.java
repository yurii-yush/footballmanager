package com.codeseek.service.impl;

import com.codeseek.common.Messages;
import com.codeseek.common.Pagination;
import com.codeseek.common.PlayerValidator;
import com.codeseek.controller.dto.request.PlayerRequestDTO;
import com.codeseek.controller.dto.response.PlayerResponseDTO;
import com.codeseek.controller.request.PlayerSearchRequest;
import com.codeseek.entity.Player;
import com.codeseek.exception.PlayerValidationException;
import com.codeseek.exception.ResourceNotFoundException;
import com.codeseek.repository.PlayerRepository;
import com.codeseek.repository.specification.PlayerSpecifications;
import com.codeseek.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlayerServiceImpl implements PlayerService, Pagination {

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<PlayerResponseDTO> searchPlayers(PlayerSearchRequest searchRequest) {
        Specification<Player> query = PlayerSpecifications.generateQuery(searchRequest);
        PageRequest page = PageRequest.of(Optional.ofNullable(searchRequest.getPage()).orElse(DEFAULT_PAGE), Optional.ofNullable(searchRequest.getLimit()).orElse(DEFAULT_LIMIT));
        Page<Player> players = playerRepository.findAll(query, page);

        return getPlayerDTOList(players);
    }

    @Override
    public PlayerResponseDTO savePlayer(PlayerRequestDTO playerRequestDTO) {
        PlayerValidator.validate(playerRequestDTO);

        Player savedPlayer = playerRepository.saveAndFlush(playerRequestDTO.toEntity());

        return savedPlayer.toDTO();
    }

    @Override
    public PlayerResponseDTO updatePlayer(PlayerRequestDTO playerRequestDTO, Long id) {
        if (!getPlayerById(id).getIsActive()) {
            throw new PlayerValidationException(Messages.PLAYER_NOT_UPDATABLE);
        }

        playerRequestDTO.setId(id);
        return savePlayer(playerRequestDTO);
    }

    @Override
    public void deletePlayerById(Long id) {
       getPlayerById(id).setIsActive(false);
    }

    private Page<PlayerResponseDTO> getPlayerDTOList(Page<Player> players) {
        return new PageImpl<>(players.stream()
                .map(Player::toDTO)
                .collect(Collectors.toList()));
    }

    @Transactional(readOnly = true)
    private Player getPlayerById(Long playerId) {
        return playerRepository.findById(playerId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(Messages.PLAYER_ID_NOT_FOUND, playerId)));
    }
}
