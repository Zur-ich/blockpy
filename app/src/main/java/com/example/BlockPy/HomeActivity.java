package com.example.BlockPy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView lessonRecyclerView;
    private List<Lesson> lessonList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        lessonRecyclerView = findViewById(R.id.lessonRecyclerView);
        initializeRecyclerView();

        loadLessons();
        setupLessonList();
    }

    private void initializeRecyclerView() {
        if (lessonRecyclerView.getLayoutManager() == null) {
            lessonRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    // --- COMPLETE Lesson Data with ACTUAL Questions & Descriptions ---
    private static final int PLACEHOLDER_ICON = android.R.drawable.star_on;
    private static final int PLACEHOLDER_BACKGROUND = R.color.purple_200;
    private void loadLessons() {
        lessonList = new ArrayList<>();
        int placeholderIcon=android.R.drawable.star_on; int placeholderBackground=R.color.purple_200; int noAudio=0; int noSound=0;
        // Lesson 1
        String cd1="The 'print()' command tells Python to show text or numbers on the screen...\n\nExample:\n`print(\"Hello, Python!\")`\n\nThis will display:\n`Hello, Python!`"; List<Question> q1=new ArrayList<>();
        q1.add(new Question("What makes things appear on the screen?", List.of("Thinking", "Printing", "Sleeping"), 1)); q1.add(new Question("Which word means 'show this'?", List.of("Hide", "Print", "Run"), 1)); q1.add(new Question("If code says print \"Apple\", what do you see?", List.of("Banana", "Apple", "Orange"), 1)); q1.add(new Question("What shape are the 'quotes' \" \"?", List.of("Circles", "Squares", "Curvy Lines"), 2)); q1.add(new Question("Does print 'Hello' == print \"Hello\"?", List.of("Yes", "No", "Maybe"), 0));
        lessonList.add(new Lesson("L1", "1. Print Statement", placeholderIcon, cd1, noAudio, placeholderBackground, "Awesome Printing!", noSound, q1));
        // Lesson 2
        String cd2="Variables are like containers or boxes...\n\nExample:\n`name = \"BlockPy\"`\n`print(name)`\n\nThis stores \"BlockPy\"..."; List<Question> q2=new ArrayList<>();
        q2.add(new Question("What is a variable like?", List.of("A Cloud", "A Labeled Box", "A River"), 1)); q2.add(new Question("If 'box = Banana', what is in the box?", List.of("Apple", "Banana", "Grapes"), 1)); q2.add(new Question("Can a variable box hold different things?", List.of("Yes", "No", "Only Toys"), 0)); q2.add(new Question("What does the '=' sign do?", List.of("Empty", "Put IN", "Check same"), 1)); q2.add(new Question("If 'pet = \"Dog\"', name is?", List.of("Dog", "\"Dog\"", "pet"), 2));
        lessonList.add(new Lesson("L2", "2. Variables", placeholderIcon, cd2, noAudio, placeholderBackground, "Super Variable!", noSound, q2));
        // Lesson 3
        String cd3="Python can do math! Use familiar symbols...\n\nExample:\n`result = 5 + 3`\n`print(result)`\n\nThis will display: `8`"; List<Question> q3=new ArrayList<>();
        q3.add(new Question("What does '+' do?", List.of("Take Away", "Count", "Add Together"), 2)); q3.add(new Question("What is 2 + 3?", List.of("4", "5", "6"), 1)); q3.add(new Question("What does '-' do?", List.of("Add", "Take Away", "Bigger"), 1)); q3.add(new Question("What is 5 - 1?", List.of("3", "4", "6"), 1)); q3.add(new Question("2 apples + 2 more = ?", List.of("2", "3", "4"), 2));
        lessonList.add(new Lesson("L3", "3. Operations", placeholderIcon, cd3, noAudio, placeholderBackground, "Math Whiz!", noSound, q3));
        // Lesson 4
        String cd4="Loops let you repeat actions!...\n\nExample (prints 0, 1, 2):\n`for i in range(3):`\n`    print(i)`\n\nNotice the indent!"; List<Question> q4=new ArrayList<>();
        q4.add(new Question("What does a loop do?", List.of("Stop", "Go Fast", "Repeat"), 2)); q4.add(new Question("'Clap 3 times', how many claps?", List.of("1", "2", "3"), 2)); q4.add(new Question("Which word means 'repeat'?", List.of("End", "Loop", "Start"), 1)); q4.add(new Question("Loops help do things easily?", List.of("Yes", "No", "Once"), 0)); q4.add(new Question("Loop is like singing...", List.of("Once", "Over & Over", "Silently"), 1));
        lessonList.add(new Lesson("L4", "4. Loops", placeholderIcon, cd4, noAudio, placeholderBackground, "Loop Master!", noSound, q4));
        // Lesson 5
        String cd5="A List stores multiple items in order...\n\nExample:\n`colors = [\"red\", \"green\", \"blue\"]`\n`print(colors[0])`\n\nThis prints `red`..."; List<Question> q5=new ArrayList<>();
        q5.add(new Question("What is a list like?", List.of("One thing", "Things in order", "Messy pile"), 1)); q5.add(new Question("[Car,Ball,Doll], first toy?", List.of("Ball", "Doll", "Car"), 2)); q5.add(new Question("List hold different kinds?", List.of("Yes", "No", "Only blocks"), 0)); q5.add(new Question("Shape of list brackets [ ]?", List.of("Circles", "Wavy", "Square"), 2)); q5.add(new Question("A list helps keep things...", List.of("Hidden", "Organized", "Lost"), 1));
        lessonList.add(new Lesson("L5", "5. Array (List)", placeholderIcon, cd5, noAudio, placeholderBackground, "List Pro!", noSound, q5));
        Log.d("HomeActivity", "Loaded "+lessonList.size()+" lessons.");
    }

    // Add this inner class to your MainActivity class
    private class LessonListAdapter extends RecyclerView.Adapter<LessonListAdapter.ViewHolder> {
        private List<Lesson> lessons;
        private LessonProgressManager progressManager;

        public LessonListAdapter(List<Lesson> lessons) {
            this.lessons = lessons;
            this.progressManager = new LessonProgressManager(HomeActivity.this);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_lesson, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Lesson lesson = lessons.get(position);
            holder.lessonTitle.setText(lesson.getTitle());

            // Check if the lesson is unlocked
            boolean isUnlocked = progressManager.isLessonUnlocked(lesson.getId());

            // Set the lock/unlock icon
            if (isUnlocked) {
                holder.lessonIcon.setImageResource(R.drawable.ic_unlock);
            } else {
                holder.lessonIcon.setImageResource(R.drawable.ic_lock);
            }

            // Set background color based on the lesson position
            int backgroundResId;
            switch (position) {
                case 0: // L1
                    backgroundResId = R.drawable.lesson_bg_red;
                    break;
                case 1: // L2
                    backgroundResId = R.drawable.lesson_bg_blue;
                    break;
                case 2: // L3
                    backgroundResId = R.drawable.lesson_bg_green;
                    break;
                case 3: // L4
                    backgroundResId = R.drawable.lesson_bg_yellow;
                    break;
                case 4: // L5
                    backgroundResId = R.drawable.lesson_bg_orange;
                    break;
                default:
                    backgroundResId = android.R.color.white;
                    break;
            }

            // Apply background
            View lessonContainer = holder.itemView.findViewById(R.id.lesson_container);
            lessonContainer.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), backgroundResId));

            // Handle click based on lock status
            holder.itemView.setOnClickListener(v -> {
                if (isUnlocked) {
                    // Start LessonActivity with the selected lesson
                    Intent intent = new Intent(HomeActivity.this, LessonActivity.class);
                    intent.putExtra("LESSON_ID", lesson.getId());
                    startActivity(intent);
                } else {
                    Toast.makeText(HomeActivity.this,
                            "Complete previous lessons to unlock!",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return lessons.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView lessonIcon;
            TextView lessonTitle;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                lessonIcon = itemView.findViewById(R.id.lesson_icon);
                lessonTitle = itemView.findViewById(R.id.lesson_title);
            }
        }
    }

    // Then in your MainActivity where you setup the RecyclerView:
    private void setupLessonList() {
        // Use the already initialized lessonRecyclerView from onCreate
        LessonListAdapter adapter = new LessonListAdapter(lessonList);
        lessonRecyclerView.setAdapter(adapter);
    }

    @Override
    // In MainActivity.java, enhance the onResume() method:
    protected void onResume() {
        super.onResume();

        // Create a fresh adapter each time to ensure updated data
        LessonListAdapter adapter = new LessonListAdapter(lessonList);
        lessonRecyclerView.setAdapter(adapter);

        // Add detailed logging to track lesson unlock status
        Log.d("HomeActivity", "onResume: Refreshing lesson adapter");
        LessonProgressManager progressMgr = new LessonProgressManager(this);

        for (Lesson lesson : lessonList) {
            boolean isCompleted = progressMgr.isLessonCompleted(lesson.getId());
            boolean isUnlocked = progressMgr.isLessonUnlocked(lesson.getId());
            Log.d("HomeActivity", "Lesson " + lesson.getId() +
                    " - Completed: " + isCompleted +
                    ", Unlocked: " + isUnlocked);
        }
    }
}