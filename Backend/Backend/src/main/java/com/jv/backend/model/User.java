package com.jv.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;


@Data
@Entity
@Getter
@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String password;
    private String username;
    public void addRole(Role role) {
        roles.add(role);
    }
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles=new ArrayList<Role>();
    public User(String password, String username) {
        super();
        this.password = password;
        this.username = username;
    }

    public User() {
        super();
        // TODO Auto-generated constructor stub
    }

    public User(String password, String username, Collection<Role> roles) {
        super();
        this.password = password;
        this.username = username;
        this.roles = roles;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role->{
            return new SimpleGrantedAuthority("ROLE_"+role.getRole());   // Lors du chargement des roles, il est important de prefixer vos roles du mot <<ROLE_>>. Sinon ca ne marchera pas
        }).collect(Collectors.toList());

    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}