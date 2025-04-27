package com.jv.backend;

import com.jv.backend.model.Role;
import com.jv.backend.model.User;
import com.jv.backend.repository.RoleRepository;
import com.jv.backend.repository.UserRepository;
import com.jv.backend.security.JWtUtil;
import com.jv.backend.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    private IClientService service;

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Override
    public void run(String... args) {
       System.out.println("le token" + JWtUtil.generateToken("sqsq"));
        if (roleRepository.findAll().isEmpty()) {
            Role r1= service.enregistrerRole(new Role("ADMIN"));
            Role r2= service.enregistrerRole(new Role("USER"));
            Role r3= service.enregistrerRole(new Role("INVITER"));
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("1234"));
            admin.addRole(r1);
            admin.addRole(r2);
            admin.addRole(r3);
            service.enregistrerUser(admin);
        }
    }
}
