package com.example.connect.model;

import javax.persistence.*;

@Entity
@Table(name="connections")
public class Connections {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
