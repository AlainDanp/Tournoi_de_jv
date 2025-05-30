package com.jv.backend.repository;

import com.jv.backend.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional <List <Team>> findByName(String name);
}
