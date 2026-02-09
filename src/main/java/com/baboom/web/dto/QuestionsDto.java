package com.baboom.web.dto;

import java.util.Optional;

import com.baboom.web.model.QuestionList;

public record QuestionsDto(Optional<QuestionList> data){};
