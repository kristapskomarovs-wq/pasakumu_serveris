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
    private String title;

    private String description;

    @Column(nullable = false)
    private LocalDate eventDate;

    @Column(nullable = false)
    private LocalTime eventTime;

    @Column(nullable = false)
    private String location; //

    @Column(nullable = false)
    private Integer maxParticipants;

    @ManyToOne // vairākiem pasākumiem var būt viens un tas pats izveidotājs
    @JoinColumn(name = "creator_id") // Ārējas atslēgas kolonna DB
    @com.fasterxml.jackson.annotation.JsonIgnoreProperties({ "password" }) // Neiekļauj paroli JSON atbildē
    private UserModel creator;
    // Kurš lietotājs izveidoja šo pasākumu
}