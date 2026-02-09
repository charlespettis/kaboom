package com.baboom.web.dto;

import java.util.Map;

import com.baboom.web.model.QuestionList;

public record RoomDto(Long id, String code, QuestionList data, Map<String, Long> scores){}
