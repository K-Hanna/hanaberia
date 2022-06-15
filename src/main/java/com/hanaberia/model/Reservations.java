package com.hanaberia.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/*@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor*/
public class Reservations {
/*
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

*//*    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "products_id")
    private List<Products> productsList;*//*

    @Column
    private boolean expired;*/
}
