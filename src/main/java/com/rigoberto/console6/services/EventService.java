package com.rigoberto.console6.services;

import com.rigoberto.console6.entities.Event;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface EventService {
    Iterable<Event> findAll();
    Page<Event> findAll(Integer page);
    Optional<Event> findById(Integer id);
    Event save(Event entity);
    void deleteById(Integer id);
}
