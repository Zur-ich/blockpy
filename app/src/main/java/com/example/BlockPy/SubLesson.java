package com.example.BlockPy;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * Class representing a sub-lesson with its own content and questions
 */
public class SubLesson implements Serializable {
    private String id; // Sub-lesson ID (e.g., "L1.1")
    private String title; // Title of the sub-lesson
    private String content; // Content/description of the sub-lesson
    private List<Question> questions; // Questions for this sub-lesson

    public SubLesson(String id, String title, String content, List<Question> questions) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.questions = (questions != null) ? new ArrayList<>(questions) : new ArrayList<>();
    }

    // Get the main lesson ID from the sub-lesson ID (e.g., "L1" from "L1.1")
    public String getMainLessonId() {
        if (id != null && id.contains(".")) {
            return id.substring(0, id.indexOf("."));
        }
        return id;
    }

    // Get the sub-lesson number (e.g., "1" from "L1.1")
    public String getSubLessonNumber() {
        if (id != null && id.contains(".")) {
            return id.substring(id.indexOf(".") + 1);
        }
        return null;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}