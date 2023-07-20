package com.rigoberto.console6.services;

import com.rigoberto.console6.entities.Driver;

import java.util.Collection;

public interface DriverService extends GeneryService<Driver, Integer> {
    Collection<Driver> findAllByIdIn(Collection<Integer> driverIds);
}
