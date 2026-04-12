package com.komarovs.pasakumu_serveris.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity // Šī klase = DB tabula
@Data // Lombok: automātiski getters/setters/toString
@Table(name = "users") // Tabulas nosaukums PostgreSQL
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PostgreSQL pats ģenerē ID (1, 2, 3...)
    private Long id;

    @Column(unique = true, nullable = false) // Email jābūt unikālam un nevar būt tukšs
    private String email;

    @Column(nullable = false) // Parole nevar būt tukša
    private String password;
}