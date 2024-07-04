package com.arqui.GreenCoom.Eventos.Repositorio;

import com.arqui.GreenCoom.Eventos.Entidad.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findById(Long id);

    @Transactional(readOnly = true)
    @Query("SELECT p FROM Event p ORDER BY p.fecha ASC ")
    List<Event> findAllByOrderByFechaDesc();
}
