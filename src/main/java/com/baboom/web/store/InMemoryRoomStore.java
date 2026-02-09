package com.baboom.web.store;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;

import com.baboom.web.model.Room;

@Component
public class InMemoryRoomStore implements RoomStore {
    private final ConcurrentHashMap<Long, Room> rooms = new ConcurrentHashMap<>();
    private final AtomicLong idSeq = new AtomicLong(1);

    @Override
    public Optional<Room> findById(Long id){
        return Optional.ofNullable(rooms.get(id));
    }

    @Override
    public Optional<Room> findByCode(String code){
        return rooms.values()
        .stream()
        .filter(room -> room.getCode().equals(code))
        .findFirst();
    }

    @Override
    public Long generateId(){
        Long id = idSeq.incrementAndGet();
        return id;
    }

    @Override
    public void save(Room room) {
        rooms.put(room.getId(), room);
    }
}
