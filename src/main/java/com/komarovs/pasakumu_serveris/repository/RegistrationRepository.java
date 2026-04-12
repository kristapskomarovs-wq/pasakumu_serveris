package com.komarovs.pasakumu_serveris.repository;

import com.komarovs.pasakumu_serveris.models.RegistrationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RegistrationRepository extends JpaRepository<RegistrationModel, Long> {

    long countByEventId(Long eventId);
    // Cik dalībnieku jau pieteikušies

    boolean existsByUserIdAndEventId(Long userId, Long eventId);
    // Vai šis lietotājs jau ir pieteicies uz šo pasākumu?

    Optional<RegistrationModel> findByUserIdAndEventId(Long userId, Long eventId);
    // Atrod konkrēto reģistrāciju (priekš atcelšanas)

    List<RegistrationModel> findAllByUserId(Long userId);
    // Visi pasākumi, uz kuriem šis lietotājs ir pieteicies

    List<RegistrationModel> findAllByEventId(Long eventId);
    // Visi dalībnieki konkrētā pasākumā
}