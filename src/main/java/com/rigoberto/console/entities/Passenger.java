package com.rigoberto.console.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
