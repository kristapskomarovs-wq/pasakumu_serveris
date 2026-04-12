package com.komarovs.pasakumu_serveris.controllers;

import com.komarovs.pasakumu_serveris.models.EventModel;
import com.komarovs.pasakumu_serveris.models.RegistrationModel;
import com.komarovs.pasakumu_serveris.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    // ── GET /api/v1/events — Visi pasākumi ──
    @GetMapping("/api/v1/events")
    public ResponseEntity<List<EventModel>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    // ── POST /api/v1/events?creatorId=1 — Izveidot pasākumu ──
    @PostMapping("/api/v1/events")
    public ResponseEntity<?> createEvent(@RequestBody EventModel event,
            @RequestParam Long creatorId) {
        try {
            EventModel created = eventService.createEvent(event, creatorId);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ── POST /api/v1/events/{id}/join?userId=1 — Pieteikties ──
    @PostMapping("/api/v1/events/{eventId}/join")
    public ResponseEntity<?> joinEvent(@PathVariable Long eventId,
            @RequestParam Long userId) {
        try {
            eventService.joinEvent(eventId, userId);
            return ResponseEntity.ok("Veiksmīgi pieteicies!");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ── DELETE /api/v1/events/{id}/leave?userId=1 — Atcelt dalību ──
    @DeleteMapping("/api/v1/events/{eventId}/leave")
    public ResponseEntity<?> leaveEvent(@PathVariable Long eventId,
            @RequestParam Long userId) {
        try {
            eventService.leaveEvent(eventId, userId);
            return ResponseEntity.ok("Dalība atcelta!");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ── GET /api/v1/events/my?userId=1 — Mani pasākumi ──
    @GetMapping("/api/v1/events/my")
    public ResponseEntity<List<RegistrationModel>> getMyEvents(@RequestParam Long userId) {
        return ResponseEntity.ok(eventService.getMyRegistrations(userId));
    }

    // ── GET /api/v1/events/{id}/count — Cik dalībnieku ──
    @GetMapping("/api/v1/events/{eventId}/count")
    public ResponseEntity<Long> getParticipantCount(@PathVariable Long eventId) {
        return ResponseEntity.ok(eventService.getParticipantCount(eventId));
    }

    // ── GET /api/v1/events/{id}/joined?userId=1 — Vai esmu pieteicies? ──
    @GetMapping("/api/v1/events/{eventId}/joined")
    public ResponseEntity<Boolean> isJoined(@PathVariable Long eventId,
            @RequestParam Long userId) {
        return ResponseEntity.ok(eventService.isUserJoined(eventId, userId));
    }
}
