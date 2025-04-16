package com.example.BlockPy;

import android.content.Context;
import android.util.Log;

public class LessonProgressManager {
    private static final String TAG = "LessonProgressManager";
    private DatabaseHelper dbHelper;

    public LessonProgressManager(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    public void markLessonAsCompleted(String lessonId, int score) {
        if (lessonId == null || lessonId.isEmpty()) {
            Log.e(TAG, "Invalid lesson ID");
            return;
        }

        dbHelper.markLessonAsCompleted(lessonId, score);
        Log.d(TAG, "Lesson completed and saved: " + lessonId + " with score: " + score);
    }

    public boolean isLessonCompleted(String lessonId) {
        if (lessonId == null || lessonId.isEmpty()) {
            return false;
        }
        return dbHelper.isLessonCompleted(lessonId);
    }

    public boolean isLessonUnlocked(String lessonId) {
        if (lessonId == null || lessonId.isEmpty()) {
            return false;
        }

        // First lesson is always unlocked
        if (lessonId.equals("L1")) {
            return true;
        }

        // Check if previous lesson was completed
        String previousLessonId = getPreviousLessonId(lessonId);
        if (previousLessonId != null) {
            return dbHelper.isLessonCompleted(previousLessonId);
        }

        return false;
    }

    public int getLessonScore(String lessonId) {
        return dbHelper.getLessonScore(lessonId);
    }

    // Helper method to get the previous lesson ID
    private String getPreviousLessonId(String currentId) {
        if (currentId == null) return null;

        switch (currentId) {
            case "L2": return "L1";
            case "L3": return "L2";
            case "L4": return "L3";
            case "L5": return "L4";
            default: return null; // L1 has no previous lesson
        }
    }
}