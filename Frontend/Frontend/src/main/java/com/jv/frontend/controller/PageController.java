package com.jv.frontend.controller;

import com.jv.frontend.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PageController {

    @Autowired
    private TournamentService tournamentService;

    //  Page d'accueil
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("tournaments", tournamentService.getAllTournaments());
        return "index";
    }

    //  Login
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    //  Liste des tournois
    @GetMapping("/tournaments")
    public String list(Model model) {
        model.addAttribute("tournaments", tournamentService.getAllTournaments());
        model.addAttribute("myTournaments", tournamentService.getUserTournaments());
        model.addAttribute("myTeams", tournamentService.getUserTeams());
        return "list";
    }

    //  Tableau de bord
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("tournaments", tournamentService.getAllTournaments());
        model.addAttribute("myTournaments", tournamentService.getUserTournaments());
        model.addAttribute("myTeams", tournamentService.getUserTeams());
        return "dashboard";
    }

    //  Créer un tournoi
    @GetMapping("/tournaments/create")
    public String createPage(Model model) {
        model.addAttribute("tournaments", tournamentService.getAllTournaments());
        model.addAttribute("myTournaments", tournamentService.getUserTournaments());
        model.addAttribute("myTeams", tournamentService.getUserTeams());
        return "create";
    }

    //  Détail d’un tournoi
    @GetMapping("/tournaments/{id}")
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("tournaments", tournamentService.getAllTournaments());
        model.addAttribute("myTournaments", tournamentService.getUserTournaments());
        model.addAttribute("myTeams", tournamentService.getUserTeams());
        model.addAttribute("selectedTournament", tournamentService.getTournamentById(id));
        return "details";
    }

    //  S’inscrire à un tournoi
    @GetMapping("/tournaments/{id}/register")
    public String register(@PathVariable Long id, Model model) {
        model.addAttribute("tournaments", tournamentService.getAllTournaments());
        model.addAttribute("myTournaments", tournamentService.getUserTournaments());
        model.addAttribute("myTeams", tournamentService.getUserTeams());
        model.addAttribute("selectedTournament", tournamentService.getTournamentById(id));
        return "register";
    }

    // (POST) pour créer un tournoi
    @PostMapping("/tournaments/create")
    public String createTournament(@RequestParam String nom, @RequestParam String description) {
        tournamentService.createTournament(nom, description);
        return "redirect:/dashboard";
    }

    // (POST) pour s’inscrire
    @PostMapping("/tournaments/{id}/register")
    public String registerTeam(@PathVariable Long id, @RequestParam Long teamId) {
        tournamentService.registerTeamToTournament(id, teamId);
        return "redirect:/dashboard";
    }
}
