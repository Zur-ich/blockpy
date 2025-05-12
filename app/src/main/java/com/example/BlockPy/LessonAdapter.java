package com.example.BlockPy;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonViewHolder> {
    private List<Lesson> lessonList;
    private OnLessonClickListener clickListener;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "BlockPyLessonProgress";
    private Context context;

    public interface OnLessonClickListener {
        void onLessonClick(int position);
    }

    public LessonAdapter(Context context, List<Lesson> lessonList, OnLessonClickListener clickListener) {
        this.context = context;
        this.lessonList = lessonList;
        this.clickListener = clickListener;
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_lesson, parent, false);
        return new LessonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonViewHolder holder, int position) {
        Lesson lesson = lessonList.get(position);
        if (lesson != null) {
            holder.lessonTitle.setText(lesson.getTitle());

            // Always unlocked
            // Set background drawable based on position
            int backgroundResId;
            switch (position) {
                case 0: backgroundResId = R.drawable.lesson_bg_red; break;
                case 1: backgroundResId = R.drawable.lesson_bg_blue; break;
                case 2: backgroundResId = R.drawable.lesson_bg_green; break;
                case 3: backgroundResId = R.drawable.lesson_bg_yellow; break;
                case 4: backgroundResId = R.drawable.lesson_bg_orange; break;
                default: backgroundResId = android.R.color.white; break;
            }
            View lessonContainer = holder.itemView.findViewById(R.id.lesson_container);
            lessonContainer.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), backgroundResId));

            // Always allow click
            holder.itemView.setOnClickListener(v -> {
                if (clickListener != null) {
                    clickListener.onLessonClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return lessonList == null ? 0 : lessonList.size();
    }

    /**
     * Checks if a lesson is unlocked (now always true)
     */
    public boolean isLessonUnlocked(int position) {
        return true;
    }

    /**
     * Marks a lesson as completed to unlock the next one
     */
    public void markLessonAsCompleted(int position) {
        String lessonId = "L" + (position + 1); // Current lesson ID
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("lesson_completed_" + lessonId, true);
        editor.apply();
        notifyDataSetChanged();
    }

    public static class LessonViewHolder extends RecyclerView.ViewHolder {
        ImageView lessonIcon;
        TextView lessonTitle;

        public LessonViewHolder(View itemView) {
            super(itemView);
            lessonIcon = itemView.findViewById(R.id.lesson_icon);
            lessonTitle = itemView.findViewById(R.id.lesson_title);
        }
    }
}