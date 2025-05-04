package com.example.BlockPy;

import android.content.Context;
import android.util.Log;

public class LessonProgressManager {
    private static final String TAG = "LessonProgressManager";
    private DatabaseHelper dbHelper;

    public LessonProgressManager(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    public boolean markLessonAsCompleted(String lessonId, int score) {
        if (lessonId == null || lessonId.isEmpty()) {
            Log.e(TAG, "Invalid lesson ID");
            return false;
        }

        boolean success = dbHelper.markLessonAsCompleted(lessonId, score);
        Log.d(TAG, "Lesson completed and saved: " + lessonId + " with score: " + score + " success: " + success);
        return success;
    }

    public boolean isLessonCompleted(String lessonId) {
        if (lessonId == null || lessonId.isEmpty()) {
            return false;
        }
        return dbHelper.isLessonCompleted(lessonId);
    }

    /**
     * Determines if a lesson is unlocked and available to be played
     */
    public boolean isLessonUnlocked(String lessonId) {
        if (lessonId == null || lessonId.isEmpty()) {
            return false;
        }

        // Check if this is a main lesson or sub-lesson
        if (lessonId.contains(".")) {
            return isSubLessonUnlocked(lessonId);
        }

        // First lesson is always unlocked
        if (lessonId.equals("L1")) {
            return true;
        }

        // Check if previous lesson was completed
        String previousLessonId = getPreviousLessonId(lessonId);
        return previousLessonId != null && dbHelper.isLessonCompleted(previousLessonId);
    }

    /**
     * Determines if a sub-lesson is unlocked based on the following rules:
     * - First sub-lesson (X.1): Unlocked if main lesson (X) is completed
     * - Second sub-lesson (X.2): Unlocked if first sub-lesson (X.1) is completed
     * - Third sub-lesson (X.3): Unlocked if second sub-lesson (X.2) is completed
     */
    public boolean isSubLessonUnlocked(String subLessonId) {
        if (subLessonId == null || subLessonId.isEmpty() || !subLessonId.contains(".")) {
            return false;
        }

        // Extract the main lesson ID and sub-lesson number
        String[] parts = subLessonId.split("\\.");
        if (parts.length != 2) {
            return false;
        }

        String mainLessonId = parts[0];
        String subLessonNumber = parts[1];

        // Check if main lesson is unlocked first
        if (!isLessonUnlocked(mainLessonId)) {
            return false;
        }

        // First sub-lesson (X.1) is unlocked if main lesson is completed
        if (subLessonNumber.equals("1")) {
            return isLessonCompleted(mainLessonId);
        }

        // Second sub-lesson (X.2) is unlocked if first sub-lesson is completed
        else if (subLessonNumber.equals("2")) {
            return isLessonCompleted(mainLessonId + ".1");
        }

        // Third sub-lesson (X.3) is unlocked if second sub-lesson is completed
        else if (subLessonNumber.equals("3")) {
            return isLessonCompleted(mainLessonId + ".2");
        }

        return false;
    }

    /**
     * Unlock the next sub-lesson based on current completed sub-lesson
     */
    public void unlockNextSubLesson(String completedSubLessonId) {
        if (completedSubLessonId == null || !completedSubLessonId.contains(".")) {
            return;
        }

        String[] parts = completedSubLessonId.split("\\.");
        if (parts.length != 2) {
            return;
        }

        String mainLessonId = parts[0];
        String completedSubLessonNumber = parts[1];

        // For first sub-lesson completed, prepare to unlock the second
        if (completedSubLessonNumber.equals("1")) {
            markLessonAsCompleted(mainLessonId + ".2", 0);
        }
        // For second sub-lesson completed, prepare to unlock the third
        else if (completedSubLessonNumber.equals("2")) {
            markLessonAsCompleted(mainLessonId + ".3", 0);
        }
    }

    public int getLessonScore(String lessonId) {
        return dbHelper.getLessonScore(lessonId);
    }

    // Helper method to get the previous lesson ID
    private String getPreviousLessonId(String currentId) {
        if (currentId == null) return null;

        // For main lessons
        switch (currentId) {
            case "L2": return "L1";
            case "L3": return "L2";
            case "L4": return "L3";
            case "L5": return "L4";
            case "L6": return "L5";
            case "L7": return "L6";
            default: return null; // L1 has no previous lesson
        }
    }

    /**
     * Gets the parent lesson ID for a sub-lesson
     */
    public String getParentLessonId(String subLessonId) {
        if (subLessonId == null || !subLessonId.contains(".")) {
            return subLessonId; // Return the ID itself if it's not a sub-lesson
        }

        return subLessonId.split("\\.")[0];
    }

    /**
     * Gets the sub-lesson number for a sub-lesson ID
     */
    public String getSubLessonNumber(String subLessonId) {
        if (subLessonId == null || !subLessonId.contains(".")) {
            return null;
        }

        String[] parts = subLessonId.split("\\.");
        if (parts.length != 2) {
            return null;
        }

        return parts[1];
    }
}