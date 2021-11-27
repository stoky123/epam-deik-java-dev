package com.epam.training.ticketservice.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@Entity
public class Room {

    @Id
    private String name;

    @Column(nullable = false)
    private int rows;

    @Column(nullable = false)
    private int columns;

    @Override
    public String toString() {
        return "Room " + name + " With " + rows * columns + " seats, " + rows + " rows and " + columns + " columns)";
    }
}
