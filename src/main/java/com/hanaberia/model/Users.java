package com.hanaberia.model;

import javax.persistence.*;

import com.hanaberia.enums.ContactForms;
import com.hanaberia.enums.Roles;
import lombok.*;

import java.util.List;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Enumerated(EnumType.STRING)
    @Column
    private ContactForms contactForm;

    @Column
    private String contact;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String confirm;

    @Enumerated(EnumType.STRING)
    @Column
    private Roles roles;

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    private Reservations reservations;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Orders> orders;

}