package com.epam.training.ticketservice.presentation.cli.handler;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class MovieHandler {

    @ShellMethod(value = "Creates a movie", key = "create movie")
    public void createMovie(final String movieName, final String genre, final int runTime) {
        return;
    }


}
