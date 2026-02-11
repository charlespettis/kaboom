package com.baboom.web.integration;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.baboom.web.model.QuestionList;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.openai.models.chat.completions.StructuredChatCompletion;
import com.openai.models.chat.completions.StructuredChatCompletionCreateParams;

@Component
public class OpenAI {
    private final OpenAIClient client;

    public OpenAI(){
        this.client = OpenAIOkHttpClient.fromEnv();
    }

    public Optional<QuestionList> generate(String prompt){

        StructuredChatCompletionCreateParams<QuestionList> params = ChatCompletionCreateParams.builder()
        .addSystemMessage("""
            You are generating multiple-choice questions for grade school students.
      
            Output must match the QuestionList schema.
      
            Hard rules for EVERY item:
            - Questions MUST be tailored for the provided grade, subject, and difficulty level.
            - answers MUST contain exactly 2 DISTINCT strings
            - correctAnswer MUST exactly equal one of answers (string match)
            - correctAnswer MUST be the actually correct answer to the question
            - Do not include any additional fields
            - Do not output explanations, only the structured output
            - Return exactly twenty (20) items
          """)
        .addUserMessage(prompt)
        .model(ChatModel.GPT_4O_MINI)
        .temperature(0.2)
        .responseFormat(QuestionList.class)
        .build();

        StructuredChatCompletion<QuestionList> output = this.client.chat().completions().create(params);

        return output.choices().get(0).message().content();
    }

}
