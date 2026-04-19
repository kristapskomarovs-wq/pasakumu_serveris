package com.komarovs.pasakumu_serveris.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@Table(name = "events")
public class EventModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title; // "Angular meetup"

    private String description; // "Mācāmies Angular 19..."

    @Column(nullable = false)
    private LocalDate eventDate; // 2026-05-15

    @Column(nullable = false)
    private LocalTime eventTime; // 18:00

    @Column(nullable = false)
    private String location; // "Rīga, Brīvības 100"

    @Column(nullable = false)
    private Integer maxParticipants; // 20

    @ManyToOne // Daudzi pasākumi → viens izveidotājs
    @JoinColumn(name = "creator_id") // Ārējas atslēgas kolonna DB
    @com.fasterxml.jackson.annotation.JsonIgnoreProperties({ "password" })
    private UserModel creator;
    // ↑ Kurš lietotājs izveidoja šo pasākumu
}