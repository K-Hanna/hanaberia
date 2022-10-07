package com.hanaberia.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hanaberia.enums.Categories;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Products {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long id;

    @Column
    private String imageName;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categories category;

    @Column(nullable = false)
    private boolean available;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reservations_id")
    @JsonBackReference(value = "pReservation")
    private Reservations reservation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "orders_id")
    @JsonBackReference(value = "pOrder")
    private Orders order;

    @Override
    public String toString(){
        return category + ", " + name + ", " + description + ", " + price + ", " + available;
    }
}
