package com.example.BlockPy;

import android.view.LayoutInflater; import android.view.View; import android.view.ViewGroup;
import android.widget.ImageView; import android.widget.TextView;
import androidx.annotation.NonNull; import androidx.recyclerview.widget.RecyclerView;
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
        public LessonViewHolder(View itemView, OnLessonClickListener listener) {super(itemView); lessonIcon = itemView.findViewById(R.id.lesson_icon); lessonTitle = itemView.findViewById(R.id.lesson_title); this.listener = listener; itemView.setOnClickListener(this);}
        @Override public void onClick(View view){if(listener!=null){int position=getAdapterPosition();if(position!=RecyclerView.NO_POSITION)listener.onLessonClick(position);}}
    }
}