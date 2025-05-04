CREATE DATABASE IF NOT EXISTS tournoi_db;
USE tournoi_db;

DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS team;


CREATE TABLE user (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                email VARCHAR(100) UNIQUE NOT NULL,
                username VARCHAR(100) UNIQUE NOT NULL,
                password VARCHAR(255) NOT NULL
);

CREATE TABLE role (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(50) UNIQUE NOT NULL
);
-- Table de jointure User <-> Role
CREATE TABLE user_roles (
                            user_id BIGINT NOT NULL,
                            role_id BIGINT NOT NULL,
                            PRIMARY KEY (user_id, role_id),
                            FOREIGN KEY (user_id) REFERENCES user(id),
                            FOREIGN KEY (role_id) REFERENCES role(id)
);


CREATE TABLE adresse (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         rue VARCHAR(255),
                         ville VARCHAR(100),
                         code_postal VARCHAR(20),
                         pays VARCHAR(100)
);

DROP TABLE IF EXISTS client;
CREATE TABLE client (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        username VARCHAR(100) UNIQUE,
                        mot_de_passe VARCHAR(255),
                        nom VARCHAR(100),
                        prenom VARCHAR(100),
                        email VARCHAR(150),
                        telephone VARCHAR(30),
                        sexe VARCHAR(10),
                        date_naissance VARCHAR(50),
                        date_inscription VARCHAR(50),
                        adresse_id BIGINT,
                        FOREIGN KEY (adresse_id) REFERENCES adresse(id)
);


CREATE TABLE tournament (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(100),
                            location VARCHAR(100),
                            start_date DATE,
                            end_date DATE
);

CREATE TABLE team (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(100),
                      description TEXT,
                      logo VARCHAR(255),
                      color VARCHAR(50),
                      country VARCHAR(100),
                      city VARCHAR(100),
                      address VARCHAR(255),
                      phone_number VARCHAR(30),
                      email VARCHAR(150),
                      user_id BIGINT,
                      tournament_id BIGINT,

                      FOREIGN KEY (user_id) REFERENCES user(id),
                      FOREIGN KEY (tournament_id) REFERENCES tournament(id)
);