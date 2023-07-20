package com.rigoberto.console6.services;

import com.rigoberto.console6.entities.Transport;

import java.util.Collection;

public interface TransportService extends GeneryService<Transport, Integer> {
    Collection<Transport> findAllByIdIn(Collection<Integer> transportIds);
}
