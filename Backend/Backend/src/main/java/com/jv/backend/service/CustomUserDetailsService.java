package com.jv.backend.service;

import com.jv.backend.model.Role;
import com.jv.backend.model.User;
import com.jv.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Tentative de chargement d'un utilisateur avec email : " + email);

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> {
                    System.out.println("Utilisateur non trouvé avec cet email !");
                    return new UsernameNotFoundException("Utilisateur non trouvé : " + email);
                });

        System.out.println("Utilisateur trouvé : " + user.getEmail());
        System.out.println("Mot de passe stocké en base : " + user.getPassword());
        System.out.println("Rôles de l'utilisateur :");
        if (user.getRoles() != null) {
            for (Role role : user.getRoles()) {
                if (role != null) {
                    System.out.println("- " + role.getName());
                } else {
                    System.out.println("- null");
                }
            }
        } else {
            System.out.println("- aucun rôle");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole()))
                        .collect(Collectors.toList())
        );
    }


}

