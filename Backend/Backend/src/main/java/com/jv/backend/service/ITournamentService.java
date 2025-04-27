package com.jv.backend.service;

import com.jv.backend.model.Tournament;
import java.util.List;

public interface ITournamentService {
    Tournament createTournament(Tournament tournament);
    List<Tournament> getAllTournaments();
    Tournament getTournamentById(Long id);
    void deleteTournament(Long id);
    Tournament updateTournament(Long id, Tournament tournament);
}
