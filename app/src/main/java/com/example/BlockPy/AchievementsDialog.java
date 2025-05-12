package com.example.BlockPy;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AchievementsDialog extends Dialog {
    private Context context;
    private AchievementManager achievementManager;
    private List<AchievementManager.AchievementItem> allAchievements;
    private List<String> earnedAchievementIds;

    public AchievementsDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        this.achievementManager = new AchievementManager(context);
    }

    @Override     
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_achievement);

        // Find header views
        TextView unlockedCountView = findViewById(R.id.achievements_unlocked_count);
        ProgressBar progressBar = findViewById(R.id.achievements_progress_bar);

        // Find RecyclerView
        RecyclerView recyclerView = findViewById(R.id.achievements_recycler_view);
        TextView emptyView = findViewById(R.id.achievements_empty_view);

        // Get all achievements and earned achievement IDs
        List<AchievementManager.AchievementItem> all = achievementManager.getAllAchievements();
        List<String> earnedIds = new java.util.ArrayList<>();
        for (AchievementManager.AchievementItem item : achievementManager.getEarnedAchievements()) {
            earnedIds.add(item.getId());
        }
        allAchievements = all;
        earnedAchievementIds = earnedIds;

        // Set header
        int total = all.size();
        int unlocked = earnedIds.size();
        unlockedCountView.setText(unlocked + "/" + total + " unlocked");
        int progress = total > 0 ? (int) (100.0 * unlocked / total) : 0;
        progressBar.setProgress(progress);

        // Setup RecyclerView with vertical list
        recyclerView.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(context));
        AchievementsAdapter adapter = new AchievementsAdapter(allAchievements, earnedAchievementIds);
        recyclerView.setAdapter(adapter);

        // Show/hide empty view
        if (allAchievements.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        // Close button
        findViewById(R.id.achievements_close_button).setOnClickListener(v -> dismiss());
    }

    // Adapter for achievements RecyclerView
    private static class AchievementsAdapter extends RecyclerView.Adapter<AchievementsAdapter.AchievementViewHolder> {
        private List<AchievementManager.AchievementItem> achievements;
        private List<String> earnedAchievementIds;

        public AchievementsAdapter(List<AchievementManager.AchievementItem> achievements, List<String> earnedAchievementIds) {
            this.achievements = achievements;
            this.earnedAchievementIds = earnedAchievementIds;
        }

        @NonNull
        @Override
        public AchievementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_achievement, parent, false);
            return new AchievementViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AchievementViewHolder holder, int position) {
            AchievementManager.AchievementItem achievement = achievements.get(position);
            boolean isUnlocked = earnedAchievementIds.contains(achievement.getId());
            if (isUnlocked) {
                holder.achievementIcon.setImageResource(R.drawable.ic_unlock);
                holder.achievementIcon.setAlpha(1.0f);
                holder.achievementTitle.setAlpha(1.0f);
                holder.achievementDescription.setAlpha(1.0f);
                holder.achievementTitle.setTextColor(0xFF4A148C);
                holder.achievementDescription.setTextColor(0xFF444444);
            } else {
                holder.achievementIcon.setImageResource(R.drawable.ic_lock);
                holder.achievementIcon.setAlpha(0.4f);
                holder.achievementTitle.setAlpha(0.5f);
                holder.achievementDescription.setAlpha(0.5f);
                holder.achievementTitle.setTextColor(0xFF888888);
                holder.achievementDescription.setTextColor(0xFFAAAAAA);
            }
            holder.achievementTitle.setText(achievement.getTitle());
            holder.achievementDescription.setText(achievement.getDescription());
        }

        @Override
        public int getItemCount() {
            return achievements.size();
        }

        static class AchievementViewHolder extends RecyclerView.ViewHolder {
            ImageView achievementIcon;
            TextView achievementTitle;
            TextView achievementDescription;

            public AchievementViewHolder(@NonNull View itemView) {
                super(itemView);
                achievementIcon = itemView.findViewById(R.id.achievement_icon);
                achievementTitle = itemView.findViewById(R.id.achievement_title);
                achievementDescription = itemView.findViewById(R.id.achievement_description);
            }
        }
    }
}