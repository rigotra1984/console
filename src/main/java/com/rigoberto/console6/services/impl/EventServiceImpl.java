package com.rigoberto.console6.services.impl;

import com.rigoberto.console6.config.ApiConstants;
import com.rigoberto.console6.entities.Event;
import com.rigoberto.console6.repositories.EventRepository;
import com.rigoberto.console6.services.EventService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {
    protected final EventRepository repository;
    public EventServiceImpl(EventRepository repository) {
        this.repository = repository;
    }

    @Override
    public Iterable<Event> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<Event> findAll(Integer page) {
        Pageable paging = PageRequest.of(page - 1, ApiConstants.PAGE_SIZE, Sort.by("id").descending());
        return repository.findAll(paging);
    }

    @Override
    public Optional<Event> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Event save(Event entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
