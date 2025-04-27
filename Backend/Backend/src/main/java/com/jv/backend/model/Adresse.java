package com.jv.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
//@Data
@NoArgsConstructor
@AllArgsConstructor
public class Adresse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rue;
    private String ville;
    private String codePostal;
    private String pays;


    public void setRue(String rue) {
        this.rue = rue;
    }
    public String getRue() {
        return rue;
    }
    public String getCodePostal() {
        return codePostal;
    }
    public String getPays() {
        return pays;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }


    public void setPays(String pays) {
        this.pays = pays;
    }
    public String getVille() {
        return ville;
    }
}