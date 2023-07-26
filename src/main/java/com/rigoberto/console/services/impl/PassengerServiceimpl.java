package com.rigoberto.console.services.impl;

import com.rigoberto.console.entities.Address;
import com.rigoberto.console.entities.Passenger;
import com.rigoberto.console.repositories.PassengerRepository;
import com.rigoberto.console.services.PassengerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PassengerServiceimpl implements PassengerService {
    protected final PassengerRepository repository;
    private Integer pageSize;

    public PassengerServiceimpl(PassengerRepository repository, @Value("${console6p1.page-size}") Integer pageSize) {
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

    @Transactional
    @Override
    public Passenger save(Passenger entity) {
        Passenger entityNew = entity;

        if(entity.getId() == null) {
            Address address = entity.getAddress();
            entity.setAddress(null);

            entityNew = repository.save(entity);
            address.setPassenger(entityNew);
            entityNew.setAddress(address);
        }

        return repository.save(entityNew);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
