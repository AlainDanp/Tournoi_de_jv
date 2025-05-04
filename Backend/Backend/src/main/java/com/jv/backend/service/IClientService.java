package com.jv.backend.service;

import com.jv.backend.model.Client;
import com.jv.backend.model.Role;
import com.jv.backend.model.User;
import org.apache.catalina.connector.Request;

import java.util.List;

public interface IClientService {
    Client createClient(Client client);
    List<Client> getAllClients();
    Client getClientById(Long id);
    void deleteClient(Long id);
    void enregistrer(Client client);
    Client updateClient(Long id, Client client);
    User enregistrerUser(User user) ;
    Role enregistrerRole(Role role);
    Client getClientByUsername(String username);
    boolean existsByUsername(String username);
    Client save(Client client);

}
