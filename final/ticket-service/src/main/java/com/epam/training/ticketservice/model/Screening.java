package com.epam.training.ticketservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
@Entity
public class Screening {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "room")
    private Room room;

    @Column(nullable = false)
    private LocalDateTime startingDate;

    public Screening(Movie movie, Room room, LocalDateTime startingDate) {
        this.movie = movie;
        this.room = room;
        this.startingDate = startingDate;
    }

    @Override
    public String toString() {
        return this.movie.getName() + " (" + this.movie.getGenre()
                + ", " + this.movie.getRunTime() + " minutes), screened in room "
                + this.room.getName() + ", at "
                + this.startingDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
