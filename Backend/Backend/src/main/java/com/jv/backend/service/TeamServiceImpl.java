package com.jv.backend.service;

import com.jv.backend.model.Team;
import com.jv.backend.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamServiceImpl implements ITeamService {

    @Autowired
    private TeamRepository teamRepo;


    @Override
    public Team createTeam(Team team) {
        // Vérifier si le logo est valide
        if (team.getLogo() == null || team.getLogo().isEmpty()) {
            throw new IllegalArgumentException("Invalid logo");
        }
        // Vérifier si le pays est valide
        if (team.getCountry() == null || team.getCountry().isEmpty()) {
            throw new IllegalArgumentException("Invalid country");
        }
        // Vérifier si la couleur est valide
        if (team.getColor() == null || team.getColor().isEmpty()) {
            throw new IllegalArgumentException("Invalid color");
        }
        // Vérifier si la ville est valide
        if (team.getCity() == null || team.getCity().isEmpty()) {
            throw new IllegalArgumentException("Invalid city");
        }
        // Vérifier si l'adresse est valide
        if (team.getAddress() == null || team.getAddress().isEmpty()) {
            throw new IllegalArgumentException("Invalid address");
        }
        // Vérifier si le numéro de téléphone est valide
        if (team.getPhoneNumber() == null || team.getPhoneNumber().isEmpty()) {
            throw new IllegalArgumentException("Invalid phone number");
        }
        // Vérifier si l'email est valide
        if (team.getEmail() == null || !team.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email");
        }
        // Vérifier si la description est valide
        if (team.getDescription() == null || team.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Invalid description");
        }
        // Vérifier si le nom de l'équipe est valide
        if (team.getName() == null || team.getName().isEmpty()) {
            throw new IllegalArgumentException("Invalid team name");
        }
        // Vérifier si le nom de l'équipe respecte la longueur maximale
        if (team.getName().length() > 50) {
            throw new IllegalArgumentException("Team name exceeds maximum length");
        }


        return teamRepo.save(team);
    }

    @Override
    public List<Team> getAllTeams() {
        return teamRepo.findAll();
    }

    @Override
    public Team getTeamById(Long id) {
        return teamRepo.findById(id).orElse(null);
    }

    @Override
    public Team updateTeam(Long id, Team updatedTeam) {
        Team team = teamRepo.findById(id).orElse(null);
        if (team != null) {
            team.setName(updatedTeam.getName());
            team.setDescription(updatedTeam.getDescription());
            team.setLogo(updatedTeam.getLogo());
            team.setCountry(updatedTeam.getCountry());
            team.setColor(updatedTeam.getColor());
            team.setCity(updatedTeam.getCity());
            team.setAddress(updatedTeam.getAddress());
            team.setPhoneNumber(updatedTeam.getPhoneNumber());
            team.setEmail(updatedTeam.getEmail());
            return teamRepo.save(team);
        }
        return null;
    }

    @Override
    public void deleteTeam(Long id) {
        teamRepo.deleteById(id);
    }
}
