package com.rigoberto.console.services.impl;

import com.rigoberto.console.entities.Event;
import com.rigoberto.console.repositories.EventRepository;
import com.rigoberto.console.services.EventService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {
    protected final EventRepository repository;
    private Integer pageSize;
    public EventServiceImpl(EventRepository repository, @Value("${console6p1.page-size}") Integer pageSize) {
        this.repository = repository;
        this.pageSize = pageSize;
    }

    @Override
    public Iterable<Event> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<Event> findAll(Integer page) {
        Pageable paging = PageRequest.of(page - 1, pageSize, Sort.by("id").descending());
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
