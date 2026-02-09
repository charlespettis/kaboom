package com.baboom.web.integration;

public class PromptBuilder {
    public String build(String prompt){
        return """    
        Generate 20 questions based on the following prompt.  
        Each question should have 2 possible answers and one defined correct answer.
        One of the 2 possible answers MUST be equal to the correct answer. 
        Prompt:
        """ + prompt;
    }
}
