USE tournoi_db;

INSERT INTO adresse (rue, ville, code_postal, pays)
VALUES ('10 rue de la paix', 'Paris', '75000', 'France');

SET @adresse_id = LAST_INSERT_ID();


INSERT INTO client (
    username,
    nom,
    prenom,
    email,
    telephone,
    mot_de_passe,
    date_naissance,
    sexe,
    date_inscription,
    adresse_id
) VALUES (
             'johndoe',
             'Doe',
             'John',
             'john.doe@example.com',
             '0612345678',
             'password123',
             '1990-01-01',
             'Homme',
             '2025-04-25',
             @adresse_id
         );

INSERT INTO roles (role) VALUES ('ADMIN');
INSERT INTO roles (role) VALUES ('USER');

