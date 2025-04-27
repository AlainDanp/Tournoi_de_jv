package com.jv.backend.service;

import com.jv.backend.model.Team;
import java.util.List;

public interface ITeamService {
    Team createTeam(Team team);
    Team getTeamById(Long id);
    Team updateTeam(Long id, Team updatedTeam);
    void deleteTeam(Long id);
    List<Team> getAllTeams();
}
