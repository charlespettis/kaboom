package com.baboom.web.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.baboom.web.dto.GenerateDto;
import com.baboom.web.dto.RoomDto;
import com.baboom.web.dto.ScoreDto;
import com.baboom.web.service.RoomService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/room")
public class RoomController {
    private final RoomService service;

    public RoomController(RoomService service) {
        this.service = service;
    }

    @PostMapping
    public RoomDto createRoom() {
        return service.createRoom();
    }

    @GetMapping
    public RoomDto getRoom(@RequestParam() String code){
        return service.getRoom(code);
    }

    @PostMapping("/generateQuestions")
    public boolean generateQuestions(@RequestBody() GenerateDto req){
        return service.generateQuestions(req.prompt(), req.code());
    }
    
    @PostMapping("/incrementScore")
    public void incrementScore(@RequestBody() ScoreDto req){
        service.incrementScore(req.name(), req.code());
    }

    @PostMapping("/resetScore")
    public void resetScore(@RequestBody() ScoreDto req){
        service.resetScore(req.name(), req.code());
    }

}
