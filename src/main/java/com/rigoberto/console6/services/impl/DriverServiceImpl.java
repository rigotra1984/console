package com.rigoberto.console6.services.impl;

import com.rigoberto.console6.entities.Driver;
import com.rigoberto.console6.repositories.DriverRepository;
import com.rigoberto.console6.services.DriverService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DriverServiceImpl implements DriverService {
    protected final DriverRepository repository;
    private Integer pageSize;

    public DriverServiceImpl(DriverRepository repository, @Value("${console6p1.page-size}") Integer pageSize) {
        this.repository = repository;
        this.pageSize = pageSize;
    }

    @Override
    public Iterable<Driver> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<Driver> findAll(Integer page) {
        Pageable paging = PageRequest.of(page - 1, pageSize, Sort.by("id").descending());
        return repository.findAll(paging);
    }

    @Override
    public Optional<Driver> findById(Integer id) {
        return repository.findById(id);
    }

    @Transactional
    @Override
    public Driver save(Driver entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Collection<Driver> findAllByIdIn(Collection<Integer> driverIds) {
        return repository.findAllByIdIn(driverIds);
    }
}
