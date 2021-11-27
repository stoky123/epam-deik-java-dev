package com.epam.training.ticketservice.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
public class Movie {

    @Id
    private String name;

    @Column(nullable = false)
    private String genre;

    @Column(nullable = false)
    private int runTime;
}
