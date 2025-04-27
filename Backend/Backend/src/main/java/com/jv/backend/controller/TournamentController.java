package com.jv.backend.controller;

import com.jv.backend.model.Tournament;
import com.jv.backend.service.ITournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tournaments")
public class TournamentController {

    @Autowired
    private ITournamentService tournamentService;

    @PostMapping()
    public Tournament create(@RequestBody Tournament tournament) {
        return tournamentService.createTournament(tournament);
    }

    @GetMapping()
    public List<Tournament> getAll() {
        return tournamentService.getAllTournaments();
    }

    @GetMapping("/{id}")
    public Tournament getById(@PathVariable Long id) {
        return tournamentService.getTournamentById(id);
    }

    @PutMapping("/{id}")
    public Tournament update(@PathVariable Long id, @RequestBody Tournament tournament) {
        return tournamentService.updateTournament(id, tournament);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        tournamentService.deleteTournament(id);
    }
}
