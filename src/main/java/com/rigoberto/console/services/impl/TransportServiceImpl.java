package com.rigoberto.console.services.impl;

import com.rigoberto.console.entities.Transport;
import com.rigoberto.console.repositories.TransportRepository;
import com.rigoberto.console.services.TransportService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class TransportServiceImpl implements TransportService {
    protected final TransportRepository repository;
    private Integer pageSize;

    public TransportServiceImpl(TransportRepository repository, @Value("${console6p1.page-size}") Integer pageSize) {
        this.repository = repository;
        this.pageSize = pageSize;
    }

    @Override
    public Iterable<Transport> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<Transport> findAll(Integer page) {
        Pageable paging = PageRequest.of(page - 1, pageSize, Sort.by("id").descending());
        return repository.findAll(paging);
    }

    @Override
    public Optional<Transport> findById(Integer id) {
        return repository.findById(id);
    }

    @Transactional
    @Override
    public Transport save(Transport entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Collection<Transport> findAllByIdIn(Collection<Integer> transportIds) {
        return repository.findAllByIdIn(transportIds);
    }
}
