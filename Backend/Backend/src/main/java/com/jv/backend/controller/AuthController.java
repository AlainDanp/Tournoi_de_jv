package com.jv.backend.controller;

import com.jv.backend.model.Client;
import com.jv.backend.model.LoginRequest;
import com.jv.backend.model.User;
import com.jv.backend.security.CustomUserDetailsService;
import com.jv.backend.security.JWtUtil;
import com.jv.backend.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWtUtil jwtUtil;

    @Autowired
    private IClientService service;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
            String token = JWtUtil.generateToken(user.getUsername());
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Login ou mot de passe incorrect");
        }
    }
}

