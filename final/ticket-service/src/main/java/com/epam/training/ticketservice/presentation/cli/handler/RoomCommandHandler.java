package com.epam.training.ticketservice.presentation.cli.handler;

import com.epam.training.ticketservice.service.AccountService;
import com.epam.training.ticketservice.service.RoomService;
import com.epam.training.ticketservice.service.exception.RoomAlreadyExistsException;
import com.epam.training.ticketservice.service.exception.RoomDoesNotExistsException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
public class RoomCommandHandler extends AbstractCommandHandler {

    private final RoomService roomService;

    public RoomCommandHandler(RoomService roomService, AccountService accountService) {
        this.roomService = roomService;
        this.accountService = accountService;
    }

    @ShellMethod(value = "Creates a room", key = "create room")
    @ShellMethodAvailability("signedIn")
    public String createRoom(final String roomName, final int rows, final int columns) {
        try {
            this.roomService.createRoom(roomName, rows, columns);
            return "Room created.";
        }
        catch (RoomAlreadyExistsException e) {
            return "This room already exists";
        }
    }

    @ShellMethod(value = "Updates a room", key = "update room")
    @ShellMethodAvailability("signedIn")
    public String updateRoom(final String roomName, final int rows, final int columns) {
        try {
            this.roomService.updateRoom(roomName, rows, columns);
            return "Room updated.";
        }
        catch (RoomDoesNotExistsException e) {
            return "This room does not exist.";
        }
    }

    @ShellMethod(value = "Deletes a room", key = "delete room")
    @ShellMethodAvailability("signedIn")
    public String deleteRoom(final String roomName) {
        try {
            this.roomService.deleteRoom(roomName);
            return "Room deleted.";
        }
        catch (RoomDoesNotExistsException e) {
            return "This room does not exist.";
        }
    }

    @ShellMethod(value = "Lists all existing room", key = "list rooms")
    public String listRooms() {
        return this.roomService.listRooms();
    }
}
