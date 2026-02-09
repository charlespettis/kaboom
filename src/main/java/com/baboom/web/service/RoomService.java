package com.baboom.web.service;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.baboom.web.dto.RoomDto;
import com.baboom.web.integration.OpenAI;
import com.baboom.web.integration.PromptBuilder;
import com.baboom.web.model.QuestionList;
import com.baboom.web.model.Room;
import com.baboom.web.store.InMemoryRoomStore;

@Service
public class RoomService {
    private final InMemoryRoomStore store;

    public RoomService(InMemoryRoomStore store){
        this.store = store;
    }

    private String generateRandomRoomCode(){
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }

    public RoomDto createRoom(){
        Long id = store.generateId();

        String roomCode = generateRandomRoomCode();

        Room room = new Room(id, roomCode);
        
        store.save(room);

        return new RoomDto(room.getId(), room.getCode(), room.getQuestions(), room.getScores());
    }

    public RoomDto getRoom(String code){

        Room room = store.findByCode(code)
        .orElseThrow(() -> new RuntimeException("No Room Found with code: " + code));
        
        return new RoomDto(room.getId(), room.getCode(), room.getQuestions(), room.getScores());
    }

    public boolean generateQuestions(String prompt, String code){
        
        OpenAI openAI = new OpenAI();
        PromptBuilder promptBuilder = new PromptBuilder();
        String formattedPrompt = promptBuilder.build(prompt);

        QuestionList questions = openAI.generate(formattedPrompt)
        .orElseThrow(() -> new RuntimeException("Error generating questions"));

        Room room = store.findByCode(code)
        .orElseThrow(() -> new RuntimeException("Room not found"));

        boolean validated = openAI.validateQuestions(questions);
        if(!validated) new RuntimeException("Error generating questions");

        room.setQuestions(questions);

        return true;
    }

    public void incrementScore(String name, String code){
        Room room = store.findByCode(code)
        .orElseThrow(() -> new RuntimeException("No room found with code: " + code));
        
        room.updateScore(name, 1);
    }

    public void resetScore(String name, String code){
        Room room = store.findByCode(code)
        .orElseThrow(() -> new RuntimeException("No room found with code: " + code));

        room.resetScore(name);
    }
}

