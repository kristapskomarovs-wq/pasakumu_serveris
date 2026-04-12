package com.komarovs.pasakumu_serveris.repository;

import com.komarovs.pasakumu_serveris.models.EventModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventModel, Long> {

    List<EventModel> findAllByOrderByEventDateAsc();

    // Atgriež visus pasākumus, sakārtotus pēc datuma (agrākais pirmais)
}