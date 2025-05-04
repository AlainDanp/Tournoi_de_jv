package com.jv.backend.service;

import com.jv.backend.model.Adresse;
import com.jv.backend.model.Client;
import com.jv.backend.model.Role;
import com.jv.backend.model.User;
import com.jv.backend.repository.ClientRepository;
import com.jv.backend.repository.RoleRepository;
import com.jv.backend.repository.UserRepository;
import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements IClientService {

    @Autowired
    private ClientRepository clientDAO;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private UserRepository UserRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Client createClient(Client client) {
        // Vérifie si le client existe déjà
        if (clientRepository.findByUsername(client.getUsername()).isPresent()) {
            throw new RuntimeException("Client already exists with username " + client.getUsername());
        }
        // Vérifie si l'adresse est valide
        if (client.getAdresse() == null || client.getAdresse().getRue() == null || client.getAdresse().getVille() == null) {
            throw new RuntimeException("Invalid address");
        }
        // Vérifie si le client a un nom et un prénom
        if (client.getNom() == null || client.getPrenom() == null) {
            throw new RuntimeException("Client must have a name and surname");
        }
        // Vérifie si le client a un email valide
        if (client.getEmail() == null || !client.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new RuntimeException("Invalid email");
        }
        // Vérifie si le client a un numéro de téléphone valide
        if (client.getTelephone() == null || !client.getTelephone().matches("^[0-9]{10}$")) {
            throw new RuntimeException("Invalid phone number");
        }
        // Vérifie si le client a une date de naissance valide
        if (client.getDateNaissance() == null) {
            throw new RuntimeException("Invalid date of birth");
        }
        // Vérifie si le client a une date d'inscription valide
        if (client.getDateInscription() == null) {
            throw new RuntimeException("Invalid registration date");
        }
        // Vérifie si le pays de l'adresse est valide
        if (client.getAdresse().getPays() == null || client.getAdresse().getPays().isEmpty()) {
            throw new RuntimeException("Invalid country");
        }
        // Vérifie si le code postal de l'adresse est valide
        if (client.getAdresse().getCodePostal() == null || client.getAdresse().getCodePostal().isEmpty()) {
            throw new RuntimeException("Invalid postal code");
        }
        return clientRepository.save(client);
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with id " + id));
    }

    @Override
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public void enregistrer(Client client) {
        clientRepository.save(client);
    }

    public Client getClientByUsername(String username) {
        return clientRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Client not found"));
    }

    @Override
    public boolean existsByUsername(String username) {
        return false;
    }

    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }


    @Override
    public Client updateClient(Long id, Client updatedClient) {
        Client existing = getClientById(id);
        existing.setNom(updatedClient.getNom());
        existing.setPrenom(updatedClient.getPrenom());
        existing.setEmail(updatedClient.getEmail());
        existing.setTelephone(updatedClient.getTelephone());
        existing.setAdresse(updatedClient.getAdresse());
        existing.setDateNaissance(updatedClient.getDateNaissance());
        existing.setDateInscription(updatedClient.getDateInscription());
        existing.setSexe(updatedClient.getSexe());
        return clientRepository.save(existing);
    }

    @Override
    public User enregistrerUser(User user) {
        return  UserRepository.save(user);
    }

    @Override
    public Role enregistrerRole(Role role) {
        return roleRepository.save(role);
    }
}
