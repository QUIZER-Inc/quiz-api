package ru.project.quiz.domain.dto.response;

import lombok.Data;

@Data
public class QuestionResponse {
    private int numberOfSavedQuestions = 0;
    private int numberOfRepeatedQuestions = 0;
    private int numberOfErrorQuestions = 0;

    public void incrementByStatusCode(int statusCode) {
        switch (statusCode) {
            case 0:
                this.numberOfSavedQuestions++;
                break;
            case 1:
                this.numberOfRepeatedQuestions++;
                break;
            case 2:
                this.numberOfErrorQuestions++;
                break;
        }
    }
}
