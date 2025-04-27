package com.jv.backend.service;

import com.jv.backend.model.Tournament;
import com.jv.backend.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TournamentServiceImpl implements ITournamentService{

    @Autowired
    private TournamentRepository tournamentRepo;

    @Override
    public Tournament createTournament(Tournament tournament) {
        // Vérifier si le nom est valide
        if (tournament.getName() == null || tournament.getName().isEmpty()) {
            throw new IllegalArgumentException("Invalid name");
        }
        return tournamentRepo.save(tournament);
    }

    @Override
    public List<Tournament> getAllTournaments() {
        return tournamentRepo.findAll();
    }

    @Override
    public Tournament getTournamentById(Long id) {
        return tournamentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Tournament not found with id " + id));
    }

    @Override
    public void deleteTournament(Long id) {
        tournamentRepo.deleteById(id);
    }

    @Override
    public Tournament updateTournament(Long id, Tournament tournament) {
        Tournament existingTournament = tournamentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Tournament not found with id " + id));
        existingTournament.setName(tournament.getName());
        existingTournament.setDescription(tournament.getDescription());
        existingTournament.setTeams(tournament.getTeams());
        existingTournament.setStartDate(tournament.getStartDate());
        existingTournament.setEndDate(tournament.getEndDate());
        existingTournament.setLocation(tournament.getLocation());

        return tournamentRepo.save(existingTournament);
    }
}
