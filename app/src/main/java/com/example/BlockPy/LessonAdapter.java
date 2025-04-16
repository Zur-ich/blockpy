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

            // Check if lesson is unlocked
            boolean isUnlocked = isLessonUnlocked(position);

            // Set the appropriate lock/unlock icon
            if (isUnlocked) {
                holder.lessonIcon.setImageResource(R.drawable.ic_unlock);
            } else {
                holder.lessonIcon.setImageResource(R.drawable.ic_lock);
            }

            // Set background drawable based on position
            int backgroundResId;
            switch (position) {
                case 0: // 1. Print Statement
                    backgroundResId = R.drawable.lesson_bg_red;
                    break;
                case 1: // 2. Variables
                    backgroundResId = R.drawable.lesson_bg_blue;
                    break;
                case 2: // 3. Operations
                    backgroundResId = R.drawable.lesson_bg_green;
                    break;
                case 3: // 4. Loops
                    backgroundResId = R.drawable.lesson_bg_yellow;
                    break;
                case 4: // 5. Array (List)
                    backgroundResId = R.drawable.lesson_bg_orange;
                    break;
                default:
                    backgroundResId = android.R.color.white;
                    break;
            }

            // Get the lesson container inside the item view
            View lessonContainer = holder.itemView.findViewById(R.id.lesson_container);
            lessonContainer.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), backgroundResId));

            // Set click listener with lock check
            holder.itemView.setOnClickListener(v -> {
                if (isUnlocked) {
                    if (clickListener != null) {
                        clickListener.onLessonClick(position);
                    }
                } else {
                    Toast.makeText(context, "Complete previous lessons to unlock!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return lessonList == null ? 0 : lessonList.size();
    }

    /**
     * Checks if a lesson is unlocked based on position
     * Lesson 0 is always unlocked, others require previous lesson completion
     */
    public boolean isLessonUnlocked(int position) {
        // First lesson is always unlocked
        if (position == 0) {
            return true;
        }

        // Get the lesson ID for the previous position
        String previousLessonId = "L" + position; // This becomes L1, L2, etc.

        // Check if previous lesson was completed using the same key format as LessonActivity
        return sharedPreferences.getBoolean("lesson_completed_" + previousLessonId, false);
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