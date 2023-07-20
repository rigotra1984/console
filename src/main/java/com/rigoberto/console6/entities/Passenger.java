package com.rigoberto.console6.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "passenger")
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "transport_id", nullable=false)
    private Transport transport;

    @OneToOne(mappedBy = "passenger", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Address address;
}
