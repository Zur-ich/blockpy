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

import java.util.HashMap;
import java.util.Map;

public class AchievementManager {
    private static final String TAG = "AchievementManager";
    private static final int ACHIEVEMENT_DISPLAY_DURATION = 3000; // 3 seconds

    // Map of lesson IDs to their achievement titles
    private static final Map<String, String> ACHIEVEMENT_TITLES = new HashMap<>();

    static {
        ACHIEVEMENT_TITLES.put("L1", "Python Explorer");
        ACHIEVEMENT_TITLES.put("L2", "Print Punisher");
        ACHIEVEMENT_TITLES.put("L3", "Variable Virtuoso");
        ACHIEVEMENT_TITLES.put("L4", "Operation Operator");
        ACHIEVEMENT_TITLES.put("L5", "Conditional Commander");
        ACHIEVEMENT_TITLES.put("L6", "Loop Legend");
        ACHIEVEMENT_TITLES.put("L7", "Array Ace");

        // Sub-lesson achievements (for future use)
        ACHIEVEMENT_TITLES.put("L1.1", "Python Beginner");
        ACHIEVEMENT_TITLES.put("L1.2", "Program Builder");
        ACHIEVEMENT_TITLES.put("L1.3", "Python Helper");

        ACHIEVEMENT_TITLES.put("L2.1", "Python Speaker");
        ACHIEVEMENT_TITLES.put("L2.2", "Word Printer");
        ACHIEVEMENT_TITLES.put("L2.3", "Number Printer");

        ACHIEVEMENT_TITLES.put("L3.1", "Box Master");
        ACHIEVEMENT_TITLES.put("L3.2", "Name Giver");
        ACHIEVEMENT_TITLES.put("L3.3", "Value Changer");

        ACHIEVEMENT_TITLES.put("L4.1", "Addition Ace");
        ACHIEVEMENT_TITLES.put("L4.2", "Subtraction Star");
        ACHIEVEMENT_TITLES.put("L4.3", "Comparison King");

        ACHIEVEMENT_TITLES.put("L5.1", "Choice Champion");
        ACHIEVEMENT_TITLES.put("L5.2", "If-Then Expert");
        ACHIEVEMENT_TITLES.put("L5.3", "Question Answerer");

        ACHIEVEMENT_TITLES.put("L6.1", "Repeat Master");
        ACHIEVEMENT_TITLES.put("L6.2", "Count Captain");
        ACHIEVEMENT_TITLES.put("L6.3", "Loop Navigator");

        ACHIEVEMENT_TITLES.put("L7.1", "Basket Builder");
        ACHIEVEMENT_TITLES.put("L7.2", "List Editor");
        ACHIEVEMENT_TITLES.put("L7.3", "Item Finder");
    }

    // Map of lesson IDs to their achievement icons (resource IDs)
    private static final Map<String, Integer> ACHIEVEMENT_ICONS = new HashMap<>();

    static {
        // Updated with specific icons for each lesson
        int introIcon = R.drawable.achievement_print;
        int printIcon = R.drawable.achievement_print;
        int variableIcon = R.drawable.achievement_variable;
        int operationIcon = R.drawable.achievement_condition;
        int conditionalIcon = R.drawable.achievement_operation;
        int loopsIcon = R.drawable.achievement_loop;
        int arrayIcon = R.drawable.achievement_array;

        // Main lesson achievements
        ACHIEVEMENT_ICONS.put("L1", introIcon);
        ACHIEVEMENT_ICONS.put("L2", printIcon);
        ACHIEVEMENT_ICONS.put("L3", variableIcon);
        ACHIEVEMENT_ICONS.put("L4", operationIcon);
        ACHIEVEMENT_ICONS.put("L5", conditionalIcon);
        ACHIEVEMENT_ICONS.put("L6", loopsIcon);
        ACHIEVEMENT_ICONS.put("L7", arrayIcon);

        // Sub-lesson achievements use the same icons as their parents
        for (int i = 1; i <= 3; i++) {
            ACHIEVEMENT_ICONS.put("L1." + i, introIcon);
            ACHIEVEMENT_ICONS.put("L2." + i, printIcon);
            ACHIEVEMENT_ICONS.put("L3." + i, variableIcon);
            ACHIEVEMENT_ICONS.put("L4." + i, operationIcon);
            ACHIEVEMENT_ICONS.put("L5." + i, conditionalIcon);
            ACHIEVEMENT_ICONS.put("L6." + i, loopsIcon);
            ACHIEVEMENT_ICONS.put("L7." + i, arrayIcon);
        }
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
}