package com.rigoberto.console.services;

import com.rigoberto.console.entities.Transport;

import java.util.Collection;

public interface TransportService extends GeneryService<Transport, Integer> {
    Collection<Transport> findAllByIdIn(Collection<Integer> transportIds);
}
