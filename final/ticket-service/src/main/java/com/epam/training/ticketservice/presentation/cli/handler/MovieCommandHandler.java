package com.epam.training.ticketservice.presentation.cli.handler;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class MovieCommandHandler {

    @ShellMethod(value = "Creates a movie", key = "create movie")
    public String createMovie(final String movieName, final String genre, final int runTime) {
        return "Movie created.";
    }

    @ShellMethod(value = "Updates an already existing movie", key = "update movie")
    public String updateMovie(final String movieName, final String genre, final int runTime) {
        return "Movie updated.";
    }

    @ShellMethod(value = "Deletes a movie", key = "delete movie")
    public String deleteMovie(final String movieName) {
        return "Movie deleted.";
    }

    @ShellMethod(value = "Lists all existing movie", key = "list movies")
    public String listMovies() {
        if(false) {
            return "Movies: ";
        }
        else {
            return "There are no movies at the moment.";
        }
    }
}
