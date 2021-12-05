package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.service.exception.RoomAlreadyExistsException;
import com.epam.training.ticketservice.service.exception.RoomDoesNotExistsException;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public void createRoom(String roomName, int rows, int columns) throws RoomAlreadyExistsException {
        if (this.roomRepository.findById(roomName).isPresent()) {
            throw new RoomAlreadyExistsException();
        }
        this.roomRepository.save(new Room(roomName, rows, columns));
    }

    public void updateRoom(String roomName, int rows, int columns) throws RoomDoesNotExistsException {
        if (this.roomRepository.findById(roomName).isPresent()) {
            this.roomRepository.save(new Room(roomName, rows, columns));
        } else {
            throw new RoomDoesNotExistsException();
        }
    }

    public void deleteRoom(String roomName) {
        if (!this.roomRepository.findById(roomName).isPresent()) {
            throw new RoomDoesNotExistsException();
        }
        this.roomRepository.deleteById(roomName);
    }

    public String listRooms() {
        StringBuilder stringBuilder = new StringBuilder();
        this.roomRepository.findAll().forEach(room -> stringBuilder.append(room).append("\n"));

        if (stringBuilder.length() == 0) {
            return "There are no rooms at the moment";
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }
}
