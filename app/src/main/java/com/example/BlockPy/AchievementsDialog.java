package com.example.BlockPy;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AchievementsDialog extends Dialog {
    private Context context;
    private AchievementManager achievementManager;
    private List<AchievementManager.AchievementItem> earnedAchievements;

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

        // Find RecyclerView
        RecyclerView recyclerView = findViewById(R.id.achievements_recycler_view);
        TextView emptyView = findViewById(R.id.achievements_empty_view);

        // Get earned achievements
        earnedAchievements = achievementManager.getEarnedAchievements();

        // Setup RecyclerView
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        AchievementsAdapter adapter = new AchievementsAdapter(earnedAchievements);
        recyclerView.setAdapter(adapter);

        // Show/hide empty view
        if (earnedAchievements.isEmpty()) {
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

        public AchievementsAdapter(List<AchievementManager.AchievementItem> achievements) {
            this.achievements = achievements;
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
            holder.achievementIcon.setImageResource(achievement.getIconResourceId());
            holder.achievementTitle.setText(achievement.getTitle());
        }

        @Override
        public int getItemCount() {
            return achievements.size();
        }

        static class AchievementViewHolder extends RecyclerView.ViewHolder {
            ImageView achievementIcon;
            TextView achievementTitle;

            public AchievementViewHolder(@NonNull View itemView) {
                super(itemView);
                achievementIcon = itemView.findViewById(R.id.achievement_icon);
                achievementTitle = itemView.findViewById(R.id.achievement_title);
            }
        }
    }
}