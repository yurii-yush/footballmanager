package com.codeseek.service.impl;

import com.codeseek.common.Messages;
import com.codeseek.common.Pagination;
import com.codeseek.controller.dto.request.TeamRequestDTO;
import com.codeseek.controller.dto.response.TeamResponseDTO;
import com.codeseek.controller.request.TeamSearchRequest;
import com.codeseek.entity.Team;
import com.codeseek.exception.ResourceNotFoundException;
import com.codeseek.exception.TeamValidationException;
import com.codeseek.repository.TeamRepository;
import com.codeseek.repository.specification.TeamSpecifications;
import com.codeseek.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService, Pagination {
    @Autowired
    private TeamRepository teamRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<TeamResponseDTO> searchTeams(TeamSearchRequest searchRequest) {
        Specification<Team> query = TeamSpecifications.generateQuery(searchRequest);
        PageRequest page = PageRequest.of(Optional.ofNullable(searchRequest.getPage()).orElse(DEFAULT_PAGE), Optional.ofNullable(searchRequest.getLimit()).orElse(DEFAULT_LIMIT));
        Page<Team> Teams = teamRepository.findAll(query, page);

        return getTeamDTOList(Teams);
    }

    @Override
    @Transactional
    public TeamResponseDTO updateTeam(TeamRequestDTO teamRequestDTO, Long teamId) {
        if (!getTeamById(teamId).getIsActive()){ // if team is not active throw exception
            throw new TeamValidationException(Messages.TEAM_NOT_UPDATABLE);
        }

        teamRequestDTO.setId(teamId);
        return saveTeam(teamRequestDTO);
    }

    @Override
    @Transactional
    public TeamResponseDTO saveTeam(TeamRequestDTO teamRequestDTO) {
        if (isTeamNameValid(teamRequestDTO.getName())) {
            throw new EntityExistsException(String.format(Messages.TEAM_NAME_ALREADY_EXIST, teamRequestDTO.getName()));
        }

        Team savedTeam = teamRepository.saveAndFlush(teamRequestDTO.toEntity());

        return savedTeam.toDTO();
    }
    @Override
    @Transactional
    public void deleteTeamById(Long teamId) {
       Team team = getTeamById(teamId);
       team.setIsActive(false);

       if (team.getPlayers() != null){
           team.getPlayers().forEach(player -> player.setTeam(null));  // make player Free Agents
       }
    }

    private Page<TeamResponseDTO> getTeamDTOList(Page<Team> teams) {
        return new PageImpl<>(teams.stream()
                .map(Team::toDTO)
                .collect(Collectors.toList()));
    }


    private Team getTeamById(Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(Messages.TEAM_ID_NOT_FOUND, teamId)));
    }

    private boolean isTeamNameValid(String name) {
        return teamRepository.existsByName(name);
    }

    private boolean isTeamIdValid(Long id) {
        return teamRepository.existsById(id);
    }
}
