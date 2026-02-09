package com.baboom.web.model;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Room {
    private Long id;
    private QuestionList questions;
    private String code;
    private ConcurrentHashMap<String, Long> scores;

    public Room(Long id, String code){
        this.id = id;
        this.code = code;
        this.scores = new ConcurrentHashMap<String, Long>();
    }

    public Long getId(){
        return id;
    }

    public void setQuestions(QuestionList questions){
        this.questions = questions;
    }

    public QuestionList getQuestions(){
        return questions;
    }

    public String getCode(){
        return code;
    }

    public Map<String, Long> getScores() {
        return Collections.unmodifiableMap(scores);
    }

    public void updateScore(String userId, long delta) {
        scores.merge(userId, delta, Long::sum);
    }

    public void resetScore(String userId) {
        scores.put(userId, 0l);
    }
}
