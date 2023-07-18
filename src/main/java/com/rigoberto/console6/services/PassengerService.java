package com.rigoberto.console6.services;

import com.rigoberto.console6.entities.Passenger;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface PassengerService {
    Iterable<Passenger> findAll();

    Page<Passenger> findAll(Integer page);

    Optional<Passenger> findById(Integer id);

    Passenger save(Passenger entity);

    void deleteById(Integer id);
}
