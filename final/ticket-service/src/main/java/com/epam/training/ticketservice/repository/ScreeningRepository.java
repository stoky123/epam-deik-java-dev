package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.model.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ScreeningRepository extends JpaRepository<Screening, Long> {
    Optional<Screening> findByMovieAndRoomAndStartingDate(Movie movie, Room room, LocalDateTime startingDate);
}
