package com.rigoberto.console.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "driver")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="passport", nullable = false)
    private String passport;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "transport_driver",
            joinColumns = { @JoinColumn(name = "driver_id", referencedColumnName = "id", table = "driver") },
            inverseJoinColumns = { @JoinColumn(name = "transport_id", referencedColumnName = "id", table = "transport")
    })
    private Set<Transport> transports;
}
