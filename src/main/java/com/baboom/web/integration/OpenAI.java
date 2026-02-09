package com.baboom.web.integration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.baboom.web.model.QuestionList;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.openai.models.chat.completions.StructuredChatCompletion;
import com.openai.models.chat.completions.StructuredChatCompletionCreateParams;

public class OpenAI {
    public Optional<QuestionList> generate(String prompt){
        OpenAIClient client = OpenAIOkHttpClient.fromEnv();

        StructuredChatCompletionCreateParams<QuestionList> params = ChatCompletionCreateParams.builder()
        .addSystemMessage("""
            You are generating K-8 multiple-choice questions.
      
            Output must match the QuestionList schema.
      
            Hard rules for EVERY item:
            - answers MUST contain exactly 2 DISTINCT strings
            - correctAnswer MUST exactly equal one of answers (string match)
            - correctAnswer MUST be the actually correct answer to the question
            - Do not include any additional fields
            - Do not output explanations, only the structured output
          """)
        .addUserMessage(prompt)
        .model(ChatModel.GPT_4O_MINI)
        .temperature(0.2)
        .responseFormat(QuestionList.class)
        .build();

        StructuredChatCompletion<QuestionList> output = client.chat().completions().create(params);

        return output.choices().get(0).message().content();
    }

    public boolean validateQuestions(QuestionList ql){
        List<String> errors = new ArrayList<>();
         
        for (int i = 0; i < ql.questions.size(); i++) {
            var q = ql.questions.get(i);

            if (q.answers == null || q.answers.size() != 2){ 
                errors.add(i + ": answers size != 2");
            }else {
                if (Objects.equals(q.answers.get(0), q.answers.get(1))) errors.add(i + ": answers not distinct");
                if (!q.answers.contains(q.correctAnswer)) errors.add(i + ": correctAnswer not in answers");
            }
        }
        
        if(errors.size() > 0){
            return true;
        } return false;
    }

}
