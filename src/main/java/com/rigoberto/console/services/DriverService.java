package com.rigoberto.console.services;

import com.rigoberto.console.entities.Driver;

import java.util.Collection;

public interface DriverService extends GeneryService<Driver, Integer> {
    Collection<Driver> findAllByIdIn(Collection<Integer> driverIds);
}
