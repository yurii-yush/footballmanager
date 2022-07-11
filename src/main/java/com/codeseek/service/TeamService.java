package com.codeseek.service;

import com.codeseek.controller.dto.request.TeamRequestDTO;
import com.codeseek.controller.dto.response.TeamResponseDTO;
import com.codeseek.controller.request.TeamSearchRequest;
import org.springframework.data.domain.Page;

public interface TeamService {
    Page<TeamResponseDTO> searchTeams(TeamSearchRequest searchRequest);

    TeamResponseDTO updateTeam(TeamRequestDTO teamRequestDTO, Long id);

    TeamResponseDTO saveTeam(TeamRequestDTO teamRequestDTO);
    void deleteTeamById(Long id);
}
