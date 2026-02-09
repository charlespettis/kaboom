package com.baboom.web.store;

import java.util.Optional;

import com.baboom.web.model.Room;

public interface RoomStore {
    Optional<Room> findById(Long id);
    void save(Room room);
    Long generateId();
    Optional<Room> findByCode(String code);
}
