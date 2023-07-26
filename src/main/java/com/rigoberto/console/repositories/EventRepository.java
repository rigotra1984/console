package com.rigoberto.console.repositories;

import com.rigoberto.console.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findAllByDateAfter(Date date);
    @Query("SELECT e FROM Event e WHERE e.priority = :prioridad")
    Event damePorPrioridad(@Param("prioridad") String prioridad);
}
