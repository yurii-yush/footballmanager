package com.codeseek.controller;

import com.codeseek.common.Messages;
import com.codeseek.controller.dto.request.TeamRequestDTO;
import com.codeseek.controller.dto.response.TeamResponseDTO;
import com.codeseek.controller.request.TeamSearchRequest;
import com.codeseek.service.TeamService;
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
@RequestMapping(value = Messages.TEAM_CONTROLLER_URI)
public class TeamController {

    @Autowired
    private TeamService teamService;

    @ApiOperation(value = "This method is used to search Teams")
    @GetMapping
    public ResponseEntity<Page<TeamResponseDTO>> searchTeam(@Valid @RequestBody TeamSearchRequest searchRequest) {
        Page<TeamResponseDTO> teams = teamService.searchTeams(searchRequest);

        return ResponseEntity.status(HttpStatus.OK).body(teams);
    }

    @ApiOperation(value = "This method is used to save new Team")
    @PostMapping
    public ResponseEntity<URI> saveTeam(@Valid @RequestBody TeamRequestDTO teamRequestDTO) {
        TeamResponseDTO createdTeamResponseDTO = teamService.saveTeam(teamRequestDTO);
        URI location = URI.create(String.format(Messages.CREATED_TEAM_URI, createdTeamResponseDTO.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(location);
    }

    @ApiOperation(value = "This method is used to update Team by ID")
    @PutMapping(value = Messages.ID_MAPPING)
    public ResponseEntity<TeamResponseDTO> updateTeam(@Valid @RequestBody TeamRequestDTO teamRequestDTO,
                                                      @PathVariable(Messages.ID_PATH) Long teamId) {
        TeamResponseDTO updatedTeamResponseDTO = teamService.updateTeam(teamRequestDTO, teamId);

        return ResponseEntity.status(HttpStatus.OK).body(updatedTeamResponseDTO);
    }

    @ApiOperation(value = "This method is used to logical delete Team by ID")
    @DeleteMapping(value = Messages.ID_MAPPING)
    public ResponseEntity<?> deleteTeamById(@PathVariable(Messages.ID_PATH) Long id) {
        teamService.deleteTeamById(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
