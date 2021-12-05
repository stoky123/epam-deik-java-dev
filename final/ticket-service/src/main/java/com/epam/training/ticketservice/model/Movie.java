package com.epam.training.ticketservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Movie {

    @Id
    private String name;

    @Column(nullable = false)
    private String genre;

    @Column(nullable = false)
    private int runTime;

    @Override
    public String toString() {
        return name + " (" + genre + ", " + runTime + " minutes)";
    }
}
