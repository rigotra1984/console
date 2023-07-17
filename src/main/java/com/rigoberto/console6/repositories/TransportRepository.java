package com.rigoberto.console6.repositories;

import com.rigoberto.console6.entities.Transport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportRepository extends JpaRepository<Transport, Integer> {

}
