package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.model.Screening;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import com.epam.training.ticketservice.service.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ScreeningServiceTest {

    private ScreeningService underTest;

    @Mock
    private ScreeningRepository screeningRepository;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private RoomRepository roomRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final Room existingRoom = new Room("existingRoom", 10, 10);
    private final Movie existingMovie = new Movie("existingMovie", "genre", 60);
    private final Screening existingScreening = new Screening(
            existingMovie,
            existingRoom,
            LocalDateTime.parse(
                    "2000-01-01 16:40",
                    formatter
            )
    );

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new ScreeningService(screeningRepository, movieRepository, roomRepository);
        BDDMockito.given(roomRepository.findById("existingRoom"))
                .willReturn(Optional.of(existingRoom));
        BDDMockito.given(movieRepository.findById("existingMovie"))
                .willReturn(Optional.of(existingMovie));
    }

    @Test
    void testListScreeningShouldReturnFormattedListOfScreeningsWhenScreeningsExist() {
        // Given
        final String expected = "movie (genre, 10 minutes), screened in room room, at 2000-01-01 16:30\n"
                + "existingMovie (genre, 60 minutes), screened in room existingRoom, at 2000-01-01 16:40";
        BDDMockito.given(screeningRepository.findAll())
                .willReturn(List.of(
                        new Screening(
                                new Movie("movie", "genre", 10),
                                new Room("room", 10, 10),
                                LocalDateTime.parse(
                                        "2000-01-01 16:30",
                                        formatter
                                )
                        ),
                        new Screening(
                                existingMovie,
                                existingRoom,
                                LocalDateTime.parse(
                                        "2000-01-01 16:40",
                                        formatter
                                )
                        )
                ));
        // When
        final String actual = underTest.listScreenings();
        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testListRoomsShouldReturnThereAreNoRoomsAtTheMomentWhenNoRoomsExist() {
        // Given
        final String expected = "There are no screenings";
        BDDMockito.given(screeningRepository.findAll())
                .willReturn(List.of());
        // When
        final String actual = underTest.listScreenings();
        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testCreateScreeningShouldThrowMovieDoesNotExistsExceptionWhenMovieDoesNotExist() {
        // Given in setup
        // When + Then
        assertThrows(MovieDoesNotExistsException.class,
                () -> underTest.createScreening(
                        "notExistingMovie",
                        "existingRoom",
                        "2000-01-01 16:30"
                )
        );
    }

    @Test
    void testCreateScreeningShouldThrowRoomDoesNotExistsExceptionWhenRoomDoesNotExist() {
        // Given in setup
        // When + Then
        assertThrows(RoomDoesNotExistsException.class,
                () -> underTest.createScreening(
                        "existingMovie",
                        "notExistingRoom",
                        "2000-01-01 16:30"
                )
        );
    }

    @Test
    void testDeleteScreeningShouldThrowMovieDoesNotExistsExceptionWhenMovieDoesNotExist() {
        // Given in setup
        // When + Then
        assertThrows(MovieDoesNotExistsException.class,
                () -> underTest.deleteScreening(
                        "notExistingMovie",
                        "existingRoom",
                        "2000-01-01 16:30"
                )
        );
    }

    @Test
    void testDeleteScreeningShouldThrowRoomDoesNotExistsExceptionWhenRoomDoesNotExist() {
        // Given in setup
        // When + Then
        assertThrows(RoomDoesNotExistsException.class,
                () -> underTest.deleteScreening(
                        "existingMovie",
                        "notExistingRoom",
                        "2000-01-01 16:30"
                )
        );
    }

    /*@Test
    void testDeleteScreeningShouldDeleteScreeningWhenGivenScreeningExists() {
        // Given in setup
        BDDMockito.given(screeningRepository.findByMovieAndRoomAndStartingDate(
                        existingMovie,
                        existingRoom,
                        LocalDateTime.parse(
                                "2000-01-01 16:40",
                                formatter
                        )
                ))
                .willReturn(Optional.of(existingScreening));
        BDDMockito.given(movieRepository.getById("existingMovie")).willReturn(existingMovie);
        BDDMockito.given(roomRepository.getById("existingRoom")).willReturn(existingRoom);
        // When
        underTest.deleteScreening("existingMovie", "existingRoom", "2000-01-01 16:40");
        // Then
        Mockito.verify(screeningRepository).findByMovieAndRoomAndStartingDate(
                existingMovie,
                existingRoom,
                LocalDateTime.parse(
                        "2000-01-01 16:40",
                        formatter
                )
        );
        Mockito.verify(screeningRepository).deleteById(Mockito.any(Long.class));
        Mockito.verifyNoMoreInteractions(screeningRepository);
    }*/

    @Test
    void testDeleteScreeningShouldThrowScreeningNotFoundExceptionWhenScreeningDoesNotExist() {
        // Given in setup
        // When

        // Then
        assertThrows(ScreeningNotFoundException.class,
                () -> underTest.deleteScreening(
                        "existingMovie",
                        "existingRoom",
                        "2002-01-01 16:40")
        );
    }

    @Test
    void testCreateScreeningShouldCreateScreeningWhenNotOverlapping() {
        // Given
        BDDMockito.given(screeningRepository.findAll()).willReturn(
                List.of(existingScreening)
        );
        BDDMockito.given(movieRepository.getById("existingMovie")).willReturn(existingMovie);
        // When
        underTest.createScreening("existingMovie", "existingRoom", "2020-01-01 16:00");
        // Then
        Mockito.verify(screeningRepository).findAll();
        Mockito.verify(screeningRepository).save(Mockito.any(Screening.class));
        Mockito.verifyNoMoreInteractions(screeningRepository);
    }

    @Test
    void testCreateScreeningShouldThrowOverLappingScreeningExceptionWhenOverlapping() {
        // Given
        BDDMockito.given(screeningRepository.findAll()).willReturn(
                List.of(existingScreening)
        );
        BDDMockito.given(movieRepository.getById("existingMovie")).willReturn(existingMovie);
        // When + Then

        assertThrows(OverLappingScreeningException.class,
                () -> underTest.createScreening(
                        "existingMovie",
                        "existingRoom",
                        "2000-01-01 16:00")
        );

    }

    @Test
    void testCreateScreeningShouldThrowBreakPeriodExceptionWhenNewScreeningStartsInTheBreakPeriod() {
        // Given
        BDDMockito.given(screeningRepository.findAll()).willReturn(
                List.of(existingScreening)
        );
        BDDMockito.given(movieRepository.getById("existingMovie")).willReturn(existingMovie);
        // When + Then

        assertThrows(BreakPeriodException.class,
                () -> underTest.createScreening(
                        "existingMovie",
                        "existingRoom",
                        "2000-01-01 17:45")
        );

    }

    @Test
    void testCreateScreeningShouldThrowBreakPeriodExceptionWhenNewScreeningEndsInTheBreakPeriod() {
        // Given
        BDDMockito.given(screeningRepository.findAll()).willReturn(
                List.of(existingScreening)
        );
        BDDMockito.given(movieRepository.getById("existingMovie")).willReturn(existingMovie);
        // When + Then

        assertThrows(BreakPeriodException.class,
                () -> underTest.createScreening(
                        "existingMovie",
                        "existingRoom",
                        "2000-01-01 15:35")
        );

    }
}
