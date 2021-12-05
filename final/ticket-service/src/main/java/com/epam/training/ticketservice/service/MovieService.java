package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.service.exception.MovieDoesNotExistsException;
import com.epam.training.ticketservice.service.exception.MovieExistsException;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void createMovie(String movieName, String genre, int runTime) throws MovieExistsException {
        if (this.movieRepository.findById(movieName).isPresent()) {
            throw new MovieExistsException();
        }
        this.movieRepository.save(new Movie(movieName, genre, runTime));
    }

    public void updateMovie(String movieName, String genre, int runTime) {
        if (this.movieRepository.findById(movieName).isPresent()) {
            this.movieRepository.save(new Movie(movieName, genre, runTime));
        } else {
            throw new MovieDoesNotExistsException();
        }
    }

    public void deleteMovie(String movieName) {
        if (this.movieRepository.findById(movieName).isPresent()) {
            this.movieRepository.deleteById(movieName);
        } else {
            throw new MovieDoesNotExistsException();
        }
    }

    public String listMovies() {
        StringBuilder stringBuilder = new StringBuilder();
        this.movieRepository.findAll().forEach(movie -> stringBuilder.append(movie).append("\n"));

        if (stringBuilder.length() == 0) {
            return "There are no movies at the moment";
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }
}
