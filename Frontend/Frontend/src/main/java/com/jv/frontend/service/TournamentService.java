package com.jv.frontend.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class TournamentService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String BASE_URL = "http://localhost:9090/api"; // Backend URL

    //  Tous les tournois
    public List<Map<String, Object>> getAllTournaments() {
        return fetchList(BASE_URL + "/tournaments");
    }

    //  Tournois de l'utilisateur connecté
    public List<Map<String, Object>> getUserTournaments() {
        return fetchList(BASE_URL + "/tournaments/my");
    }

    //  Équipes de l'utilisateur connecté
    public List<Map<String, Object>> getUserTeams() {
        return fetchList(BASE_URL + "/teams/my");
    }

    //  Un tournoi par ID
    public Map<String, Object> getTournamentById(Long id) {
        return restTemplate.getForObject(BASE_URL + "/tournaments/" + id, Map.class);
    }

    //  Créer un tournoi
    public void createTournament(String name, String description) {
        Map<String, String> body = new HashMap<>();
        body.put("name", name);
        body.put("description", description);

        restTemplate.postForEntity(BASE_URL + "/tournaments/create", body, Void.class);
    }

    //  Inscrire une équipe à un tournoi
    public void registerTeamToTournament(Long tournamentId, Long teamId) {
        Map<String, Long> body = new HashMap<>();
        body.put("teamId", teamId);

        restTemplate.postForEntity(BASE_URL + "/tournaments/" + tournamentId + "/register", body, Void.class);
    }

    //  Utilitaire pour fetch une liste JSON
    private List<Map<String, Object>> fetchList(String url) {
        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody() != null ? response.getBody() : new ArrayList<>();
    }
}
