package com.codeseek.controller;

import com.codeseek.common.Messages;
import com.codeseek.controller.dto.request.PlayerRequestDTO;
import com.codeseek.controller.dto.response.PlayerResponseDTO;
import com.codeseek.controller.request.PlayerSearchRequest;
import com.codeseek.service.PlayerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@Validated
@RestController
@RequestMapping(value = Messages.PLAYER_CONTROLLER_URI)
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @ApiOperation(value = "This method is used to search Players")
    @GetMapping
    public ResponseEntity<Page<PlayerResponseDTO>> searchPlayer(@Valid @RequestBody PlayerSearchRequest searchRequest) {
        Page<PlayerResponseDTO> Players = playerService.searchPlayers(searchRequest);

        return ResponseEntity.status(HttpStatus.OK).body(Players);
    }

    @ApiOperation(value = "This method is used to save new Player")
    @PostMapping
    public ResponseEntity<PlayerResponseDTO> savePlayer(@Valid @RequestBody PlayerRequestDTO playerRequestDTO) {
        PlayerResponseDTO createdPlayer = playerService.savePlayer(playerRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdPlayer);
    }

    @ApiOperation(value = "This method is used to update Player by ID")
    @PutMapping(value = Messages.ID_MAPPING)
    public ResponseEntity<PlayerResponseDTO> updatePlayer(@RequestBody
                                              @Valid PlayerRequestDTO playerRequestDTO,
                                                          @PathVariable(Messages.ID_PATH) Long playerId) {

        PlayerResponseDTO updatedPlayer = playerService.updatePlayer(playerRequestDTO, playerId);

        return ResponseEntity.status(HttpStatus.OK).body(updatedPlayer);
    }

    @ApiOperation(value = "This method is used to logical delete Player by ID")
    @DeleteMapping(value = Messages.ID_MAPPING)
    public ResponseEntity<?> deletePlayerByID(@PathVariable(Messages.ID_PATH) Long playerId) {
        playerService.deletePlayerById(playerId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
