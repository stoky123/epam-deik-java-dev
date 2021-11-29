package com.epam.training.ticketservice.presentation.cli.handler;

import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.service.AccountService;
import com.epam.training.ticketservice.service.MovieService;
import com.epam.training.ticketservice.service.exception.MovieDoesNotExistsException;
import com.epam.training.ticketservice.service.exception.MovieExistsException;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;

@ShellComponent
public class MovieCommandHandler {

    private final MovieService movieService;
    private final AccountService accountService;

    public MovieCommandHandler(MovieService movieService, AccountService accountService) {
        this.movieService = movieService;
        this.accountService = accountService;
    }

    @ShellMethod(value = "Creates a movie", key = "create movie")
    //@ShellMethodAvailability("isAdmin")
    public String createMovie(final String movieName, final String genre, final int runTime) {
        try {
            this.movieService.createMovie(movieName, genre, runTime);
            return "Movie created.";
        }
        catch (MovieExistsException e) {
            return "This movie already exists.";
        }
    }

    @ShellMethod(value = "Updates an already existing movie", key = "update movie")
    public String updateMovie(final String movieName, final String genre, final int runTime) {
        try {
            this.movieService.updateMovie(movieName, genre, runTime);
            return "Movie updated.";
        } catch (MovieDoesNotExistsException e) {
            return "This movie does not exist.";
        }
    }

    @ShellMethod(value = "Deletes a movie", key = "delete movie")
    public String deleteMovie(final String movieName) {
        try {
            this.movieService.deleteMovie(movieName);
            return "Movie deleted.";
        }
        catch (MovieDoesNotExistsException e) {
            return "This movie does not exits.";
        }
    }

    @ShellMethod(value = "Lists all existing movie", key = "list movies")
    public String listMovies() {
        return this.movieService.listMovies();
    }

    /*public Availability isAdmin() {
        return this.accountService.isAdmin() ? Availability.available() : Availability.unavailable("you are already signed in.");
    }*/
}
