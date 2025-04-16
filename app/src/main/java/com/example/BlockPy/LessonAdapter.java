package com.example.BlockPy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonViewHolder> {
    private List<Lesson> lessonList;
    private OnLessonClickListener clickListener;

    public interface OnLessonClickListener {
        void onLessonClick(int position);
    }

    public LessonAdapter(List<Lesson> lessonList, OnLessonClickListener clickListener) {
        this.lessonList = lessonList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_lesson, parent, false);
        return new LessonViewHolder(itemView, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonViewHolder holder, int position) {
        Lesson lesson = lessonList.get(position);
        if (lesson != null) {
            holder.lessonTitle.setText(lesson.getTitle());
            holder.lessonIcon.setImageResource(lesson.getIconResourceId());

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
        }
    }

    @Override
    public int getItemCount() {
        return lessonList == null ? 0 : lessonList.size();
    }

    public static class LessonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView lessonIcon;
        TextView lessonTitle;
        OnLessonClickListener listener;

        public LessonViewHolder(View itemView, OnLessonClickListener listener) {
            super(itemView);
            lessonIcon = itemView.findViewById(R.id.lesson_icon);
            lessonTitle = itemView.findViewById(R.id.lesson_title);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION)
                    listener.onLessonClick(position);
            }
        }
    }
}