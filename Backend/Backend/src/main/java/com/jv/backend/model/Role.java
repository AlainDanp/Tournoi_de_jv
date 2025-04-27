package com.jv.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Collections;

@Entity
@Data
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role;

    public Role(String role) {
        this.role = role;
    }
    public Role() {
    }
    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setrole(String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }
    public void setUsers(Collection<User> users) {
        this.users = users;
    }
    public Collection<User> getUsers() {
        return users;
    }
}
