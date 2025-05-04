package com.jv.backend.controller;

import com.jv.backend.dto.RegisterDTO;
import com.jv.backend.model.Client;
import com.jv.backend.model.Role;
import com.jv.backend.model.User;
import com.jv.backend.repository.ClientRepository;
import com.jv.backend.repository.RoleRepository;
import com.jv.backend.security.JWtFilter;
import com.jv.backend.security.JWtUtil;
import com.jv.backend.service.IClientService;
import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;


@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private IClientService clientService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWtFilter jwtFilter;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
        if (clientService.existsByUsername(registerDTO.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        Client client = new Client();
        client.setUsername(registerDTO.getUsername());
        client.setMotDePasse(passwordEncoder.encode(registerDTO.getpassword()));
        client.setEmail(registerDTO.getEmail());
        client.setNom(registerDTO.getNom());
        client.setPrenom(registerDTO.getPrenom());
        client.setTelephone(registerDTO.getTelephone());
        client.setSexe(registerDTO.getSexe());
        client.setDateNaissance(registerDTO.getDateNaissance());
        client.setDateInscription(registerDTO.getDateInscription());
        String roleName = (registerDTO.getRole() != null) ? registerDTO.getRole() : "USER";

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));

        client.getRoles().add(role);

        clientService.save(client);

        return ResponseEntity.ok("User registered successfully");
    }


    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Client> getAll() {
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Client getById(@PathVariable Long id) {
        return clientService.getClientById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Client update(@PathVariable Long id, @RequestBody Client client) {
        return clientService.updateClient(id, client);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        clientService.deleteClient(id);
    }

    @GetMapping("/me")
    public ResponseEntity<Client> getCurrentClient() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Client client = clientService.getClientByUsername(username);
        return ResponseEntity.ok(client);
    }
}

