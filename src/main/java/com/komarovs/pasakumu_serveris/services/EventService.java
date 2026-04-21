package com.komarovs.pasakumu_serveris.services;

import com.komarovs.pasakumu_serveris.models.EventModel;
import com.komarovs.pasakumu_serveris.models.RegistrationModel;
import com.komarovs.pasakumu_serveris.models.UserModel;
import com.komarovs.pasakumu_serveris.repository.EventRepository;
import com.komarovs.pasakumu_serveris.repository.RegistrationRepository;
import com.komarovs.pasakumu_serveris.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final RegistrationRepository registrationRepository;
    private final UserRepository userRepository;

    // ── VISI PASĀKUMI ──
    public List<EventModel> getAllEvents() {
        return eventRepository.findAllByOrderByEventDateAsc();
    }

    // ── IZVEIDOT JAUNU PASĀKUMU ──
    public EventModel createEvent(EventModel event, Long creatorId) {
        // ❌ VALIDĀCIJA: Datums nedrīkst būt pagātnē
        if (event.getEventDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Pasākuma datums nevar būt pagātnē!");
        }

        // ❌ VALIDĀCIJA: Nosaukums nevar būt tukšs
        if (event.getTitle() == null || event.getTitle().isBlank()) {
            throw new IllegalArgumentException("Nosaukums ir obligāts!");
        }

        // ❌ VALIDĀCIJA: Dalībnieku skaits > 0
        if (event.getMaxParticipants() == null || event.getMaxParticipants() < 1) {
            throw new IllegalArgumentException("Dalībnieku skaitam jābūt vismaz 1!");
        }

        // Uzstāda izveidotāju
        UserModel eventCreator = userRepository.findById(creatorId)
                .orElseThrow(() -> new RuntimeException("Lietotājs nav atrasts!"));
        event.setCreator(eventCreator);

        return eventRepository.save(event);
    }

    // ── PIETEIKTIES UZ PASĀKUMU ──
    public void joinEvent(Long eventId, Long userId) {
        EventModel event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Pasākums nav atrasts!"));

        // Vai pasākums nav pagātnē?
        if (event.getEventDate().isBefore(LocalDate.now())) {
            throw new IllegalStateException("Nevar pieteikties uz pagātnes pasākumu!");
        }

        // Vai pasākums nav pilns?
        long currentCount = registrationRepository.countByEventId(eventId);
        if (currentCount >= event.getMaxParticipants()) {
            throw new IllegalStateException("Pasākums ir pilns!");
        }

        // Vai jau pieteicies?
        if (registrationRepository.existsByUserIdAndEventId(userId, eventId)) {
            throw new IllegalStateException("Tu jau esi pieteicies uz šo pasākumu!");
        }

        // Viss OK — reģistrēt
        RegistrationModel reg = new RegistrationModel();
        reg.setUser(userRepository.findById(userId).orElseThrow());
        reg.setEvent(event);
        reg.setRegisteredAt(LocalDateTime.now());
        registrationRepository.save(reg);
    }

    // ── ATCELT DALĪBU ──
    public void leaveEvent(Long eventId, Long userId) {
        RegistrationModel reg = registrationRepository
                .findByUserIdAndEventId(userId, eventId)
                .orElseThrow(() -> new IllegalStateException("Tu neesi pieteicies uz šo pasākumu!"));
        registrationRepository.delete(reg);
    }

    // MANI PASĀKUMI (kur esmu pieteicies)
    public List<RegistrationModel> getMyRegistrations(Long userId) {
        return registrationRepository.findAllByUserId(userId);
    }

    // CIK DALĪBNIEKU PASĀKUMĀ
    public long getParticipantCount(Long eventId) {
        return registrationRepository.countByEventId(eventId);
    }

    // VAI LIETOTĀJS IR PIETEICIES
    public boolean isUserJoined(Long eventId, Long userId) {
        return registrationRepository.existsByUserIdAndEventId(userId, eventId);
    }

    // DZĒST PASĀKUMU (tikai izveidotājs)
    public void deleteEvent(Long eventId, Long creatorId) {
        EventModel event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalStateException("Pasākums nav atrasts!"));

        // Pārbauda vai tiešām šis lietotājs ir izveidotājs
        if (!event.getCreator().getId().equals(creatorId)) {
            throw new IllegalStateException("Tu neesi šī pasākuma izveidotājs!");
        }

        // Vispirms dzēš visas reģistrācijas (FK ierobežojums!)
        registrationRepository.deleteAll(
                registrationRepository.findAllByEventId(eventId));

        eventRepository.delete(event);
    }
}