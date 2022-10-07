package com.hanaberia.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservations {

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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column
    private LocalDate expiringDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    @JsonBackReference(value = "pUser")
    private Users user;

    @OneToMany(mappedBy = "reservation", fetch = FetchType.EAGER)
    @JsonManagedReference(value = "pReservation")
    private Set<Products> productsSet = new HashSet<>();
}
