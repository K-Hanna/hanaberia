package com.hanaberia.model;

import com.hanaberia.enums.ContactForms;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Messages {

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

    @Enumerated(EnumType.STRING)
    @Column
    private ContactForms contactForm;

    @Column
    private String contact;

    @Column
    private String title;

    @Column
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column
    private LocalDate sentDate;

    @Column
    private boolean isRead;

}
