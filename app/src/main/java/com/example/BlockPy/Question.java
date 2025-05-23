package com.example.BlockPy;

import  java.io.Serializable;
import java.util.List;

/**
 * Question represents a single quiz question in the BlockPy application.
 *
 * This class stores:
 * - The question text to be displayed to the user
 * - A list of possible answer options
 * - The index of the correct answer
 *
 * Questions are used within quizzes to test user understanding of lesson content.
 * The class implements Serializable to allow for easy passing between components.
 */
public class Question implements Serializable {
    private String questionText;
    private List<String> options;
    private int correctAnswerIndex;

    public Question(String questionText, List<String> options, int correctAnswerIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }
    public int getCorrectAnswerIndex() { return correctAnswerIndex; }
}

