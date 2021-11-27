package com.epam.training.ticketservice.presentation.cli.handler;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.text.SimpleDateFormat;

@ShellComponent
public class ScreeningCommandHandler {

    @ShellMethod(value = "Creates a new screening", key = "create screening")
    public String createScreening(final String movieName, final String roomName, final SimpleDateFormat date) {
        // Overlapping
        if (true) {
            return "There is an overlapping screening.";
        }
        // Break period
        else if (false) {
            return "This would start in the break period after another screening in this room.";
        }
        else {
            return "Screening created.";
        }
    }

    @ShellMethod(value = "Deletes a screening", key = "delete screening")
    public String deleteScreening(final String movieName, final String roomName, final SimpleDateFormat date) {
        return "Screening deleted";
    }

    @ShellMethod(value = "Lists all existing screening", key = "list screenings")
    public String listScreenings() {
        if (false) {
            return "Screenings: ";
        }
        else {
            return "There are no screenings.";
        }
    }

}
