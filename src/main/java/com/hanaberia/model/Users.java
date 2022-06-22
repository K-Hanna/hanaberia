package com.hanaberia.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Users {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Column
    private String email;

    @Column
    private String phone;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String confirm;

    @Enumerated(EnumType.STRING)
    @Column
    private Roles roles;
/*
    @Column
    private boolean emailPreferred;*/

/*    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservations_id")
    private Products reservations;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id")
    private Products orders;*/

}