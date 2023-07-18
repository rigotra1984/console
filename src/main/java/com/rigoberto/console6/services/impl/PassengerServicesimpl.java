package com.rigoberto.console6.services.impl;

import com.rigoberto.console6.entities.Passenger;
import com.rigoberto.console6.repositories.PassengerRepository;
import com.rigoberto.console6.services.PassengerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

public class PassengerServicesimpl implements PassengerService {
    protected final PassengerRepository repository;
    private Integer pageSize;

    public PassengerServicesimpl(PassengerRepository repository, @Value("${console6p1.page-size}") Integer pageSize) {
        this.repository = repository;
        this.pageSize = pageSize;
    }

    @Override
    public Iterable<Passenger> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<Passenger> findAll(Integer page) {
        Pageable paging = PageRequest.of(page - 1, pageSize, Sort.by("id").descending());
        return repository.findAll(paging);
    }

    @Override
    public Optional<Passenger> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Passenger save(Passenger entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
