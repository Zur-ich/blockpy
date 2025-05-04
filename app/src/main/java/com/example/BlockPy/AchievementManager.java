package com.example.BlockPy;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AchievementManager {
    private static final String TAG = "AchievementManager";
    private static final int ACHIEVEMENT_DISPLAY_DURATION = 3000; // 3 seconds

    // Map of lesson IDs to their achievement titles
    private static final Map<String, String> ACHIEVEMENT_TITLES = new HashMap<>();
    private static final Map<String, Integer> ACHIEVEMENT_ICONS = new HashMap<>();

    static {
        // Main lesson achievements
        ACHIEVEMENT_TITLES.put("L1", "Python Explorer");
        ACHIEVEMENT_TITLES.put("L2", "Print Punisher");
        ACHIEVEMENT_TITLES.put("L3", "Variable Virtuoso");
        ACHIEVEMENT_TITLES.put("L4", "Operation Operator");
        ACHIEVEMENT_TITLES.put("L5", "Conditional Commander");
        ACHIEVEMENT_TITLES.put("L6", "Loop Legend");
        ACHIEVEMENT_TITLES.put("L7", "Array Ace");

        // Sub-lesson achievements
        ACHIEVEMENT_TITLES.put("L1.1", "Python Beginner");
        ACHIEVEMENT_TITLES.put("L1.2", "Program Builder");
        ACHIEVEMENT_TITLES.put("L1.3", "Python Helper");
        ACHIEVEMENT_TITLES.put("L2.1", "Python Speaker");
        ACHIEVEMENT_TITLES.put("L2.2", "Word Printer");
        ACHIEVEMENT_TITLES.put("L2.3", "Number Printer");

        // Achievement Icons
        ACHIEVEMENT_ICONS.put("L1", R.drawable.achievement_intro);
        ACHIEVEMENT_ICONS.put("L2", R.drawable.achievement_print);
        ACHIEVEMENT_ICONS.put("L3", R.drawable.achievement_variable);
        ACHIEVEMENT_ICONS.put("L4", R.drawable.achievement_operation);
        ACHIEVEMENT_ICONS.put("L5", R.drawable.achievement_condition);
        ACHIEVEMENT_ICONS.put("L6", R.drawable.achievement_loop);
        ACHIEVEMENT_ICONS.put("L7", R.drawable.achievement_array);

        // Sub-lesson icons use the same icons as their parent lessons
        ACHIEVEMENT_ICONS.put("L1.1", R.drawable.achievement_intro);
        ACHIEVEMENT_ICONS.put("L1.2", R.drawable.achievement_intro);
        ACHIEVEMENT_ICONS.put("L1.3", R.drawable.achievement_intro);
        ACHIEVEMENT_ICONS.put("L2.1", R.drawable.achievement_print);
        ACHIEVEMENT_ICONS.put("L2.2", R.drawable.achievement_print);
        ACHIEVEMENT_ICONS.put("L2.3", R.drawable.achievement_print);
    }

    private Context context;
    private DatabaseHelper dbHelper;
    private AchievementStorage storage;

    public AchievementManager(Context context) {
        this.context = context;
        this.dbHelper = new DatabaseHelper(context);
        this.storage = new AchievementStorage(context);
    }

    /**
     * Check if a lesson completion should trigger an achievement and show it
     * @param lessonId The ID of the completed lesson
     * @param score The score achieved
     */
    public void checkAndShowAchievement(String lessonId, int score) {
        if (lessonId == null || !ACHIEVEMENT_TITLES.containsKey(lessonId)) {
            return;
        }

        // Minimum score required for achievement (e.g., 80% correct)
        int minScoreRequired = 4; // Assuming 5 questions per lesson

        // Only show achievement if score meets minimum requirement
        if (score >= minScoreRequired) {
            // Check if this achievement was already earned before
            boolean isFirstTime = !storage.hasEarnedAchievement(lessonId);

            // Mark as earned
            storage.markAchievementEarned(lessonId);

            // Only show notification if this is the first time earning it
            if (isFirstTime) {
                // Get achievement details
                String achievementTitle = ACHIEVEMENT_TITLES.get(lessonId);
                int iconResourceId = ACHIEVEMENT_ICONS.getOrDefault(lessonId, android.R.drawable.star_on);

                // Show achievement notification
                if (context instanceof Activity) {
                    showAchievementToast((Activity) context, achievementTitle, iconResourceId);
                }
            }
        }
    }

    /**
     * Display a custom achievement toast in the upper corner
     */
    private void showAchievementToast(@NonNull final Activity activity, String achievementTitle, int iconResId) {
        // Need to run on UI thread
        new Handler(Looper.getMainLooper()).post(() -> {
            LayoutInflater inflater = activity.getLayoutInflater();
            View layout = inflater.inflate(R.layout.achievement_toast,
                    (ViewGroup) activity.findViewById(R.id.achievement_toast_container));

            ImageView icon = layout.findViewById(R.id.achievement_icon);
            TextView title = layout.findViewById(R.id.achievement_title);
            TextView message = layout.findViewById(R.id.achievement_message);

            icon.setImageResource(iconResId);
            title.setText(achievementTitle);
            message.setText("Achievement Unlocked!");

            Toast toast = new Toast(activity);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 50);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
        });
    }

    /**
     * Get the total number of achievements earned
     */
    public int getAchievementsEarnedCount() {
        return storage.getEarnedAchievementCount();
    }

    // Method to get all earned achievements with details
    public List<AchievementItem> getEarnedAchievements() {
        List<AchievementItem> earnedAchievements = new ArrayList<>();

        // Get all earned achievement IDs
        for (String achievementId : storage.getEarnedAchievements()) {
            String title = ACHIEVEMENT_TITLES.get(achievementId);
            Integer iconResourceId = ACHIEVEMENT_ICONS.get(achievementId);

            if (title != null && iconResourceId != null) {
                earnedAchievements.add(new AchievementItem(
                        achievementId,
                        title,
                        iconResourceId
                ));
            }
        }

        return earnedAchievements;
    }

    // Achievement Item class to represent individual achievements
    public static class AchievementItem {
        private String id;
        private String title;
        private int iconResourceId;

        public AchievementItem(String id, String title, int iconResourceId) {
            this.id = id;
            this.title = title;
            this.iconResourceId = iconResourceId;
        }

        public String getId() { return id; }
        public String getTitle() { return title; }
        public int getIconResourceId() { return iconResourceId; }
    }
}