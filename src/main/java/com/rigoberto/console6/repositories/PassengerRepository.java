package com.rigoberto.console6.repositories;

import com.rigoberto.console6.entities.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Integer> {
}

