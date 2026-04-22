package com.komarovs.pasakumu_serveris.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "registrations", uniqueConstraints = @UniqueConstraint(columnNames = { "user_id", "event_id" }))
// Viens lietotājs nevar pieteikties uz vienu pasākumu divreiz!
public class RegistrationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // daudziem eventiem var būt pieteicies viens un tas pats lietotājs
    @JoinColumn(name = "user_id", nullable = false) // Ārējas atslēgas kolonna DB
    @com.fasterxml.jackson.annotation.JsonIgnoreProperties({ "password" }) // Neiekļauj paroli JSON atbildē
    private UserModel user; // Kurš pieteicās

    @ManyToOne // daudzi lietotāji var būt pieteikušies uz vienu un to pašu pasākumu
    @JoinColumn(name = "event_id", nullable = false) // Ārējas atslēgas kolonna DB
    private EventModel event; // Uz kuru pasākumu

    private LocalDateTime registeredAt; // Kad pieteicās
}