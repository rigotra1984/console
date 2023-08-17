package com.rigoberto.console.repositories;

import com.rigoberto.console.entities.Transport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;


@Repository
public interface TransportRepository extends JpaRepository<Transport, Integer> {
    Collection<Transport> findAllByIdIn(Collection<Integer> transportIds);
}
