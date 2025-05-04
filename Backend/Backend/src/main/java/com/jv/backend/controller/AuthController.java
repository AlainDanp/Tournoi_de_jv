package com.jv.backend.controller;

import com.jv.backend.dto.RegisterDTO;
import com.jv.backend.model.AuthResponse;
import com.jv.backend.model.LoginRequest;
import com.jv.backend.model.Role;
import com.jv.backend.model.User;
import com.jv.backend.repository.RoleRepository;
import com.jv.backend.repository.UserRepository;
import com.jv.backend.security.JWtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterDTO registerDTO) {

        User user = new User();
        user.setEmail(registerDTO.getEmail());
        user.setUsername(registerDTO.getUsername());
        //user.setPassword(registerDTO.getpassword());
        user.setPassword(passwordEncoder.encode(registerDTO.getpassword()));

        Set<Role> userRoles = new HashSet<>();

        if (registerDTO.getRoles() == null || registerDTO.getRoles().isEmpty()) {
            Role userRole = roleRepository.findByName("USER")
                    .orElseThrow(() -> new RuntimeException("Erreur: Le rôle USER n'existe pas."));
            userRoles.add(userRole);
            Role roleAdmin = roleRepository.findByName("ADMIN")
                    .orElseThrow(() -> new RuntimeException("Erreur : rôle ADMIN non trouvé"));

            user.setRoles(Collections.singleton(roleAdmin));

        } else {
            for (String roleName : registerDTO.getRoles()) {
                Role role = roleRepository.findByName(roleName.toUpperCase())
                        .orElseThrow(() -> new RuntimeException("Erreur: Le rôle " + roleName + " n'existe pas."));
                userRoles.add(role);
            }
        }
        user.setRoles(userRoles);
        userRepository.save(user);
        return ResponseEntity.ok("Utilisateur enregistré avec succès !");
    }


    @PostMapping("/login")
    private ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            System.out.println("Tentative de connexion avec email : " + loginRequest.getEmail());

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            User user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

            List<String> roles = user.getRoles()
                    .stream()
                    .map(role -> role.getRole())
                    .collect(Collectors.toList());

            String token = jwtUtil.generateToken(user.getEmail(),roles);

            return ResponseEntity.ok(new AuthResponse(token));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou mot de passe incorrect");
        }
    }


}
