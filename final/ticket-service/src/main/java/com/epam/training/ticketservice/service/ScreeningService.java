package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.Screening;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import com.epam.training.ticketservice.service.exception.BreakPeriodException;
import com.epam.training.ticketservice.service.exception.MovieDoesNotExistsException;
import com.epam.training.ticketservice.service.exception.OverLappingScreeningException;
import com.epam.training.ticketservice.service.exception.RoomDoesNotExistsException;
import com.epam.training.ticketservice.service.exception.ScreeningNotFoundException;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Getter
@Service
public class ScreeningService {
    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public ScreeningService(ScreeningRepository screeningRepository,
                            MovieRepository movieRepository,
                            RoomRepository roomRepository) {
        this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
    }

    public void createScreening(String movieName, String roomName, String date) {
        if (!this.movieRepository.findById(movieName).isPresent()) {
            throw new MovieDoesNotExistsException();
        }
        if (!this.roomRepository.findById(roomName).isPresent()) {
            throw new RoomDoesNotExistsException();
        }
        LocalDateTime startingDate = LocalDateTime.parse(date, this.formatter);
        LocalDateTime endingDate = startingDate
                .plusMinutes(this.movieRepository.findById(movieName).get().getRunTime());
        List<Screening> overlappingScreenings = this.screeningRepository.findAll();
        for (Screening screening : overlappingScreenings) {
            LocalDateTime existingStartingDate = screening.getStartingDate();
            LocalDateTime existingEndingDate = existingStartingDate.plusMinutes(screening.getMovie().getRunTime());
            if ((startingDate.isBefore(existingStartingDate) && endingDate.isAfter(existingStartingDate))
                    || (existingStartingDate.isBefore(startingDate) && existingEndingDate.isAfter(startingDate))
                    || startingDate.isEqual(existingStartingDate)
                    || startingDate.isEqual(existingEndingDate)
                    || endingDate.isEqual(existingStartingDate)
                    || endingDate.isEqual(existingEndingDate)) {
                throw new OverLappingScreeningException();
            }
            if ((existingEndingDate.isBefore(startingDate)
                    && existingEndingDate.plusMinutes(10).isAfter(startingDate))
                    || (endingDate.isBefore(existingStartingDate)
                    && endingDate.plusMinutes(10).isAfter(existingStartingDate))) {
                throw new BreakPeriodException();
            }
        }

        this.screeningRepository.save(
                new Screening(
                        this.movieRepository.findById(movieName).get(),
                        this.roomRepository.findById(roomName).get(),
                        startingDate)
        );
    }

    public void deleteScreening(String movieName, String roomName, String date) {
        if (!this.movieRepository.findById(movieName).isPresent()) {
            throw new MovieDoesNotExistsException();
        }
        if (!this.roomRepository.findById(roomName).isPresent()) {
            throw new RoomDoesNotExistsException();
        }
        LocalDateTime startingDate = LocalDateTime.parse(date, this.formatter);
        Optional<Screening> screeningToBeDeleted = this.screeningRepository
                .findByMovieAndRoomAndStartingDate(
                        this.movieRepository.getById(movieName),
                        this.roomRepository.getById(roomName),
                        startingDate
                );
        if (screeningToBeDeleted.isPresent()) {
            this.screeningRepository.deleteById(screeningToBeDeleted.get().getId());
        } else {
            throw new ScreeningNotFoundException();
        }
    }

    public String listScreenings() {
        StringBuilder stringBuilder = new StringBuilder();
        this.screeningRepository.findAll().forEach(screening -> stringBuilder.append(screening).append("\n"));

        if (stringBuilder.length() == 0) {
            return "There are no screenings";
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }
}
