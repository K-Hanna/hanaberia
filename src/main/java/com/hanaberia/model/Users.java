package com.hanaberia.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hanaberia.enums.ContactForms;
import com.hanaberia.enums.Questions;
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
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence")
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

    @Enumerated(EnumType.STRING)
    @Column
    private Questions question;

    @Column
    private String answer;

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonManagedReference(value = "rUser")
    private Reservations reservations;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonManagedReference(value = "oUser")
    private List<Orders> orders;

    @Override
    public String toString(){
        return id + ". " + userName;
    }

}