package com.example.BlockPy;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class LessonProgressManager {
    private static final String TAG = "LessonProgressManager";
    private static final String PREF_NAME = "BlockPyLessonProgress";
    private static final String KEY_LESSON_COMPLETED = "lesson_completed_";
    private static final String KEY_LAST_UNLOCKED = "last_unlocked_lesson";

    private SharedPreferences sharedPreferences;

    public LessonProgressManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        // First lesson (L1) is always unlocked by default
        if (!sharedPreferences.contains(KEY_LAST_UNLOCKED)) {
            setLastUnlockedLesson("L1");
        }
    }

    public void markLessonAsCompleted(String lessonId) {
        if (lessonId == null || lessonId.isEmpty()) {
            Log.e(TAG, "Invalid lesson ID");
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_LESSON_COMPLETED + lessonId, true);

        // Unlock the next lesson based on lesson sequence
        String nextLessonId = getNextLessonId(lessonId);
        if (nextLessonId != null) {
            setLastUnlockedLesson(nextLessonId);
        }

        editor.apply();
        Log.d(TAG, "Lesson completed and saved: " + lessonId + ", Next: " + nextLessonId);
    }

    public boolean isLessonCompleted(String lessonId) {
        if (lessonId == null || lessonId.isEmpty()) {
            return false;
        }
        return sharedPreferences.getBoolean(KEY_LESSON_COMPLETED + lessonId, false);
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
            return isLessonCompleted(previousLessonId);
        }

        return false;
    }

    private void setLastUnlockedLesson(String lessonId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_LAST_UNLOCKED, lessonId);
        editor.apply();
    }

    public String getLastUnlockedLesson() {
        return sharedPreferences.getString(KEY_LAST_UNLOCKED, "L1");
    }

    // Helper method to get the next lesson ID based on current sequence
    private String getNextLessonId(String currentId) {
        if (currentId == null) return null;

        switch (currentId) {
            case "L1": return "L2";
            case "L2": return "L3";
            case "L3": return "L4";
            case "L4": return "L5";
            default: return null; // No more lessons after L5
        }
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