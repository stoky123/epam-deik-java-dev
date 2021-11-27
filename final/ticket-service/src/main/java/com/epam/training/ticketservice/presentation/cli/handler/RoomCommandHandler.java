package com.epam.training.ticketservice.presentation.cli.handler;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class RoomCommandHandler {

    @ShellMethod(value = "Creates a room", key = "create room")
    public String createRoom(final String roomName, final int rows, final int columns) {
        return "Room created.";
    }

    @ShellMethod(value = "Updates a room", key = "update room")
    public String updateRoom(final String roomName, final int rows, final int columns) {
        return "Room updated.";
    }

    @ShellMethod(value = "Deletes a room", key = "delete room")
    public String deleteRoom(final String roomName) {
        return "Room deleted.";
    }

    @ShellMethod(value = "Lists all existing room", key = "list rooms")
    public String listRooms() {
        if(false) {
            return "Rooms: ";
        }
        else {
            return "There are no rooms at the moment.";
        }
    }
}
