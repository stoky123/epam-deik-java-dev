package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.service.exception.MovieDoesNotExistsException;
import com.epam.training.ticketservice.service.exception.MovieExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MovieServiceTest {

    private final Movie existingMovie = new Movie("existingMovie", "genre", 60);

    private MovieService underTest;

    @Mock
    private MovieRepository movieRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new MovieService(movieRepository);
        BDDMockito.given(movieRepository.findById("existingMovie"))
                .willReturn(Optional.of(existingMovie));
    }

    @Test
    void testCreateMovieShouldCreateMovieWhenGivenMovieDoesNotExists() {
        // Given in setup
        // When
        underTest.createMovie("notExistingMovie", "genre", 60);
        // Then
        Mockito.verify(movieRepository).findById("notExistingMovie");
        Mockito.verify(movieRepository).save(Mockito.any(Movie.class));
        Mockito.verifyNoMoreInteractions(movieRepository);
    }

    @Test
    void testCreateMovieShouldThrowMovieExistsExceptionWhenMovieAlreadyExists() {
        // Given in setup
        // When + Then
        assertThrows(MovieExistsException.class,
                () -> underTest.createMovie("existingMovie", "genre", 30));
    }

    @Test
    void testUpdateMovieShouldUpdateMovieWhenGivenMovieExists() {
        // Given in setup
        // When
        underTest.updateMovie("existingMovie", "changedGenre", 60);
        // Then
        Mockito.verify(movieRepository).findById("existingMovie");
        Mockito.verify(movieRepository).save(Mockito.any(Movie.class));
        Mockito.verifyNoMoreInteractions(movieRepository);
    }

    @Test
    void testUpdateMovieShouldThrowMovieDoesNotExistExceptionWhenMovieDoesNotExist() {
        // Given in setup
        // When + Then
        assertThrows(MovieDoesNotExistsException.class,
                () -> underTest.updateMovie("doesNotExist", "genre", 30));
    }

    @Test
    void testDeleteMovieShouldDeleteMovieWhenGivenMovieExists() {
        // Given in setup
        // When
        underTest.deleteMovie("existingMovie");
        // Then
        Mockito.verify(movieRepository).findById("existingMovie");
        Mockito.verify(movieRepository).deleteById("existingMovie");
        Mockito.verifyNoMoreInteractions(movieRepository);
    }

    @Test
    void testDeleteMovieShouldThrowMovieDoesNotExistExceptionWhenMovieDoesNotExist() {
        // Given in setup
        // When + Then
        assertThrows(MovieDoesNotExistsException.class,
                () -> underTest.deleteMovie("doesNotExist"));
    }

    @Test
    void testListMoviesShouldReturnFormattedListOfMoviesWhenMoviesExist() {
        // Given
        final String expected = "movie (genre, 20 minutes)\n"
                + "existingMovie (genre, 60 minutes)";
        BDDMockito.given(movieRepository.findAll())
                .willReturn(List.of(
                        new Movie("movie", "genre", 20),
                        existingMovie
                ));
        // When
        final String actual = underTest.listMovies();
        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testListMoviesShouldReturnThereAreNoMoviesAtTheMomentWhenNoMoviesExist() {
        // Given
        final String expected = "There are no movies at the moment";
        BDDMockito.given(movieRepository.findAll())
                .willReturn(List.of());
        // When
        final String actual = underTest.listMovies();
        // Then
        assertEquals(expected, actual);
    }

}
