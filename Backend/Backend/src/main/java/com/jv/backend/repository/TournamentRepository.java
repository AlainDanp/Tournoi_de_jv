package com.jv.backend.repository;

import com.jv.backend.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    // Custom query methods can be defined here if needed
    // For example, find by name or other attributes
    // List<Tournament> findByName(String name);
}
