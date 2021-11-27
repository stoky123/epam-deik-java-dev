package com.epam.training.ticketservice.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.text.SimpleDateFormat;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@Entity
public class Screening {

    @Id
    private String movieName;

    @Id
    private String roomName;

    @Id
    private SimpleDateFormat startingDate;

    @Override
    public String toString() {
        // TODO
        return "";
    }
}
