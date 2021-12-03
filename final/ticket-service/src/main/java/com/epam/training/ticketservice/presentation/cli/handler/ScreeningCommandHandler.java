package com.epam.training.ticketservice.presentation.cli.handler;

import com.epam.training.ticketservice.service.AccountService;
import com.epam.training.ticketservice.service.ScreeningService;
import com.epam.training.ticketservice.service.exception.BreakPeriodException;
import com.epam.training.ticketservice.service.exception.MovieDoesNotExistsException;
import com.epam.training.ticketservice.service.exception.OverLappingScreeningException;
import com.epam.training.ticketservice.service.exception.RoomDoesNotExistsException;
import com.epam.training.ticketservice.service.exception.ScreeningNotFoundException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
public class ScreeningCommandHandler extends AbstractCommandHandler {

    private final ScreeningService screeningService;

    public ScreeningCommandHandler(ScreeningService screeningService, AccountService accountservice) {
        this.screeningService = screeningService;
        this.accountService = accountservice;
    }

    @ShellMethod(value = "Creates a new screening", key = "create screening")
    @ShellMethodAvailability("isAdmin")
    public String createScreening(final String movieName, final String roomName, final String date) {
        try {
            screeningService.createScreening(movieName, roomName, date);
            return "Screening created";
        } catch (MovieDoesNotExistsException e) {
            return "This movie does not exist";
        } catch (RoomDoesNotExistsException e) {
            return "This room does not exits";
        } catch (OverLappingScreeningException e) {
            return "There is an overlapping screening";
        } catch (BreakPeriodException e) {
            return "This would start in the break period after another screening in this room";
        }
    }

    @ShellMethod(value = "Deletes a screening", key = "delete screening")
    @ShellMethodAvailability("isAdmin")
    public String deleteScreening(final String movieName, final String roomName, final String date) {
        try {
            this.screeningService.deleteScreening(movieName, roomName, date);
            return "Screening deleted successfully";
        } catch (ScreeningNotFoundException e) {
            return "This screening does not exist";
        }
    }

    @ShellMethod(value = "Lists all existing screening", key = "list screenings")
    public String listScreenings() {
        return this.screeningService.listScreenings();
    }

}
