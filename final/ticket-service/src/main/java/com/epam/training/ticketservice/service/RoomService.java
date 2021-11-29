package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.repository.RoomRepository;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }
}
