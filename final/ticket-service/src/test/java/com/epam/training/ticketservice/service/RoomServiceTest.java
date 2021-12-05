package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.service.exception.RoomAlreadyExistsException;
import com.epam.training.ticketservice.service.exception.RoomDoesNotExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RoomServiceTest {

    private final Room existingRoom = new Room("existingRoom", 10, 10);

    private RoomService underTest;

    @Mock
    private RoomRepository roomRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new RoomService(roomRepository);
        BDDMockito.given(roomRepository.findById("existingRoom"))
                .willReturn(Optional.of(existingRoom));
    }

    @Test
    void testCreateRoomShouldThrowRoomAlreadyExistsExceptionWhenRoomAlreadyExists() {
        // Given in setup
        // When + Then
        assertThrows(RoomAlreadyExistsException.class,
                () -> underTest.createRoom("existingRoom", 10, 10));
    }

    @Test
    void testCreateRoomShouldCreateRoomWhenGivenRoomDoesNotExists() {
        // Given in setup
        // When
        underTest.createRoom("notExistingRoom", 50, 50);
        // Then
        Mockito.verify(roomRepository).findById("notExistingRoom");
        Mockito.verify(roomRepository).save(Mockito.any(Room.class));
        Mockito.verifyNoMoreInteractions(roomRepository);
    }

    @Test
    void testUpdateRoomShouldUpdateRoomWhenGivenRoomExists() {
        // Given in setup
        // When
        underTest.updateRoom("existingRoom", 50, 50);
        // Then
        Mockito.verify(roomRepository).findById("existingRoom");
        Mockito.verify(roomRepository).save(Mockito.any(Room.class));
        Mockito.verifyNoMoreInteractions(roomRepository);
    }

    @Test
    void testUpdateRoomShouldThrowRoomDoesNotExistsExceptionWhenRoomAlreadyExists() {
        // Given in setup
        // When + Then
        assertThrows(RoomDoesNotExistsException.class,
                () -> underTest.updateRoom("notExistingRoom", 10, 10));
    }

    @Test
    void testDeleteMovieShouldDeleteMovieWhenGivenMovieExists() {
        // Given in setup
        // When
        underTest.deleteRoom("existingRoom");
        // Then
        Mockito.verify(roomRepository).findById("existingRoom");
        Mockito.verify(roomRepository).deleteById("existingRoom");
        Mockito.verifyNoMoreInteractions(roomRepository);
    }

    @Test
    void testDeleteRoomShouldThrowRoomDoesNotExistsExceptionWhenRoomAlreadyExists() {
        // Given in setup
        // When + Then
        assertThrows(RoomDoesNotExistsException.class,
                () -> underTest.deleteRoom("notExistingRoom"));
    }

    @Test
    void testListRoomsShouldReturnFormattedListOfRoomsWhenRoomsExist() {
        // Given
        final String expected = "Room room with 200 seats, 10 rows and 20 columns\n"
                + "Room existingRoom with 100 seats, 10 rows and 10 columns";
        BDDMockito.given(roomRepository.findAll())
                .willReturn(List.of(
                        new Room("room", 10, 20),
                        existingRoom
                ));
        // When
        final String actual = underTest.listRooms();
        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testListRoomsShouldReturnThereAreNoMoviesAtTheMomentWhenNoRoomsExist() {
        // Given
        final String expected = "There are no rooms at the moment";
        BDDMockito.given(roomRepository.findAll())
                .willReturn(List.of());
        // When
        final String actual = underTest.listRooms();
        // Then
        assertEquals(expected, actual);
    }

}
