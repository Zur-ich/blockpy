package com.example.BlockPy;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * Handles storing and retrieving achievement information
 */
public class AchievementStorage {
    private static final String PREF_NAME = "BlockPyAchievements";
    private static final String KEY_EARNED_ACHIEVEMENTS = "earned_achievements";

    private SharedPreferences sharedPreferences;

    public AchievementStorage(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Record that a specific achievement has been earned
     * @param achievementId The ID of the achievement (lesson ID like "L1")
     */
    public void markAchievementEarned(String achievementId) {
        Set<String> earnedAchievements = getEarnedAchievements();
        if (!earnedAchievements.contains(achievementId)) {
            earnedAchievements.add(achievementId);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putStringSet(KEY_EARNED_ACHIEVEMENTS, earnedAchievements);
            editor.apply();
        }
    }

    /**
     * Check if a specific achievement has been earned before
     * @param achievementId The ID to check
     * @return true if the achievement has been earned
     */
    public boolean hasEarnedAchievement(String achievementId) {
        return getEarnedAchievements().contains(achievementId);
    }

    /**
     * Get the set of all earned achievement IDs
     * @return A Set of achievement IDs that have been earned
     */
    public Set<String> getEarnedAchievements() {
        return new HashSet<>(sharedPreferences.getStringSet(KEY_EARNED_ACHIEVEMENTS, new HashSet<>()));
    }

    /**
     * Count how many achievements have been earned
     * @return The number of earned achievements
     */
    public int getEarnedAchievementCount() {
        return getEarnedAchievements().size();
    }
}