package com.rigoberto.console6.services;

import com.rigoberto.console6.entities.Transport;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface TransportService {
    Iterable<Transport> findAll();

    Page<Transport> findAll(Integer page);

    Optional<Transport> findById(Integer id);

    Transport save(Transport entity);

    void deleteById(Integer id);
}
