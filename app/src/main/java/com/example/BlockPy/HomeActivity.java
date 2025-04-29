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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView lessonRecyclerView;
    private List<LessonItem> allLessonItems;
    private List<LessonItem> visibleLessonItems;

    // Map to store which parent lessons have been expanded to show sub-lessons
    private Map<String, Boolean> expandedLessons = new HashMap<>();

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

    // --- COMPLETE Lesson Data with Sub-Lessons as Separate Cards ---
    private void loadLessons() {
        allLessonItems = new ArrayList<>();

        // -------------------- LESSON 1: Introduction to Python --------------------
        Lesson lesson1 = new Lesson(
                "L1",
                "1. Introduction to Python",
                R.drawable.achievement_intro,
                "Introduction to Python\n\nWelcome to Python programming! Learn what makes Python special.",
                0, // noAudio
                R.drawable.lesson_bg_red,
                "Python Explorer!",
                0, // noSound
                createQuestions1()
        );

        // Add main lesson
        allLessonItems.add(new LessonItem(lesson1, "Introduction", null, false));

        // Add sub-lessons
        allLessonItems.add(new LessonItem(lesson1, "Meet Python the Snake", "L1.1", true));
        allLessonItems.add(new LessonItem(lesson1, "What is a Computer Program?", "L1.2", true));
        allLessonItems.add(new LessonItem(lesson1, "How Python Helps Us", "L1.3", true));

        // -------------------- LESSON 2: Print Statement --------------------
        Lesson lesson2 = new Lesson(
                "L2",
                "2. Print Statement",
                R.drawable.achievement_print,
                "Print Statement\n\nLearn how to make Python display text and numbers on the screen.",
                0, // noAudio
                R.drawable.lesson_bg_blue,
                "Printing Master!",
                0, // noSound
                createQuestions2()
        );

        // Add main lesson
        allLessonItems.add(new LessonItem(lesson2, "Introduction", null, false));

        // Add sub-lessons
        allLessonItems.add(new LessonItem(lesson2, "Making Python Talk", "L2.1", true));
        allLessonItems.add(new LessonItem(lesson2, "Print with Words", "L2.2", true));
        allLessonItems.add(new LessonItem(lesson2, "Print with Numbers", "L2.3", true));

        // -------------------- LESSON 3: Variables --------------------
        Lesson lesson3 = new Lesson(
                "L3",
                "3. Variables",
                R.drawable.achievement_variable,
                "Variables\n\nVariables are like labeled containers that store data for later use.",
                0, // noAudio
                R.drawable.lesson_bg_green,
                "Variable Virtuoso!",
                0, // noSound
                createQuestions3()
        );

        // Add main lesson
        allLessonItems.add(new LessonItem(lesson3, "Introduction", null, false));

        // Add sub-lessons
        allLessonItems.add(new LessonItem(lesson3, "Magic Storage Boxes", "L3.1", true));
        allLessonItems.add(new LessonItem(lesson3, "Naming Our Boxes", "L3.2", true));
        allLessonItems.add(new LessonItem(lesson3, "Changing What's Inside", "L3.3", true));

        // -------------------- LESSON 4: Operations --------------------
        Lesson lesson4 = new Lesson(
                "L4",
                "4. Operations",
                R.drawable.achievement_operation,
                "Operations\n\nLearn how to do math and compare values in Python.",
                0, // noAudio
                R.drawable.lesson_bg_yellow,
                "Math Whiz!",
                0, // noSound
                createQuestions4()
        );

        // Add main lesson
        allLessonItems.add(new LessonItem(lesson4, "Introduction", null, false));

        // Add sub-lessons
        allLessonItems.add(new LessonItem(lesson4, "Adding with Python", "L4.1", true));
        allLessonItems.add(new LessonItem(lesson4, "Subtracting with Python", "L4.2", true));
        allLessonItems.add(new LessonItem(lesson4, "Comparing Numbers", "L4.3", true));

        // -------------------- LESSON 5: Conditions --------------------
        Lesson lesson5 = new Lesson(
                "L5",
                "5. Conditions",
                R.drawable.achievement_condition,
                "Conditions\n\nLearn how to make Python make decisions based on conditions.",
                0, // noAudio
                R.drawable.lesson_bg_orange,
                "Decision Master!",
                0, // noSound
                createQuestions5()
        );

        // Add main lesson
        allLessonItems.add(new LessonItem(lesson5, "Introduction", null, false));

        // Add sub-lessons
        allLessonItems.add(new LessonItem(lesson5, "Making Choices", "L5.1", true));
        allLessonItems.add(new LessonItem(lesson5, "If This Then That", "L5.2", true));
        allLessonItems.add(new LessonItem(lesson5, "Yes or No Questions", "L5.3", true));

        // -------------------- LESSON 6: Loops --------------------
        Lesson lesson6 = new Lesson(
                "L6",
                "6. Loops",
                R.drawable.achievement_loop,
                "Loops\n\nLearn how to repeat actions without writing the same code over and over.",
                0, // noAudio
                R.drawable.lesson_bg_red,
                "Loop Master!",
                0, // noSound
                createQuestions6()
        );

        // Add main lesson
        allLessonItems.add(new LessonItem(lesson6, "Introduction", null, false));

        // Add sub-lessons
        allLessonItems.add(new LessonItem(lesson6, "Doing Things Again and Again", "L6.1", true));
        allLessonItems.add(new LessonItem(lesson6, "Counting with Loops", "L6.2", true));
        allLessonItems.add(new LessonItem(lesson6, "Looping Through Items", "L6.3", true));

        // -------------------- LESSON 7: Arrays (Lists) --------------------
        Lesson lesson7 = new Lesson(
                "L7",
                "7. Arrays (Lists)",
                R.drawable.achievement_array,
                "Arrays (Lists)\n\nLearn how to store multiple items in a single container called a list.",
                0, // noAudio
                R.drawable.lesson_bg_blue,
                "List Pro!",
                0, // noSound
                createQuestions7()
        );

        // Add main lesson
        allLessonItems.add(new LessonItem(lesson7, "Introduction", null, false));

        // Add sub-lessons
        allLessonItems.add(new LessonItem(lesson7, "Collection Baskets", "L7.1", true));
        allLessonItems.add(new LessonItem(lesson7, "Adding and Removing Items", "L7.2", true));
        allLessonItems.add(new LessonItem(lesson7, "Finding Things in Lists", "L7.3", true));

        // Initialize expanded state for all lessons (all closed by default)
        for (String lessonId : new String[]{"L1", "L2", "L3", "L4", "L5", "L6", "L7"}) {
            expandedLessons.put(lessonId, false);
        }

        Log.d("HomeActivity", "Loaded " + allLessonItems.size() + " lesson items.");

        // Initially only show main lessons (no sub-lessons)
        updateVisibleLessonItems();
    }

    // Update the list of visible lesson items based on expanded state
    private void updateVisibleLessonItems() {
        visibleLessonItems = new ArrayList<>();

        for (LessonItem item : allLessonItems) {
            if (!item.isSubLesson()) {
                // Always show main lessons
                visibleLessonItems.add(item);
            } else {
                // Show sub-lessons only if their parent is expanded
                String mainLessonId = item.getMainLessonId();
                boolean isParentExpanded = expandedLessons.getOrDefault(mainLessonId, false);

                if (isParentExpanded) {
                    visibleLessonItems.add(item);
                }
            }
        }
    }

    // Helper methods to create questions for each lesson
    private List<Question> createQuestions1() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What kind of animal is Python named after?", List.of("Bird", "Snake", "Fish"), 1));
        questions.add(new Question("What does a programmer create?", List.of("Food", "Computer Programs", "Books"), 1));
        questions.add(new Question("What can Python help us do?", List.of("Swim Fast", "Solve Problems", "Fly"), 1));
        questions.add(new Question("Python is easy to:", List.of("Eat", "Read", "Throw"), 1));
        questions.add(new Question("Python was created in:", List.of("2020", "1991", "1800"), 1));
        return questions;
    }

    // Other createQuestions methods omitted for brevity...
    private List<Question> createQuestions2() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What command makes Python show text?", List.of("hide()", "print()", "talk()"), 1));
        questions.add(new Question("What goes around words in print()?", List.of("[ ]", "\" \"", "( )"), 1));
        questions.add(new Question("Can print() show numbers?", List.of("Yes", "No", "Maybe"), 0));
        questions.add(new Question("What does print(\"Hello\") do?", List.of("Says Hello", "Shows Hello", "Nothing"), 1));
        questions.add(new Question("What appears after print(5+3)?", List.of("5+3", "8", "Error"), 1));
        return questions;
    }

    private List<Question> createQuestions3() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What is a variable like?", List.of("A Cloud", "A Labeled Box", "A River"), 1));
        questions.add(new Question("If 'box = Banana', what is in the box?", List.of("Apple", "Banana", "Grapes"), 1));
        questions.add(new Question("Can a variable box hold different things?", List.of("Yes", "No", "Only Toys"), 0));
        questions.add(new Question("What does the '=' sign do?", List.of("Compares", "Puts In", "Takes Out"), 1));
        questions.add(new Question("If 'age = 10' then age + 2 equals?", List.of("10", "12", "Age2"), 1));
        return questions;
    }

    private List<Question> createQuestions4() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What does '+' do?", List.of("Take Away", "Count", "Add Together"), 2));
        questions.add(new Question("What is 2 + 3?", List.of("4", "5", "6"), 1));
        questions.add(new Question("What does '-' do?", List.of("Add", "Take Away", "Bigger"), 1));
        questions.add(new Question("What does '>' mean?", List.of("Less than", "Equal to", "Greater than"), 2));
        questions.add(new Question("2 apples + 2 more = ?", List.of("2", "3", "4"), 2));
        return questions;
    }

    private List<Question> createQuestions5() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What do conditionals help us do?", List.of("Jump", "Make Decisions", "Print"), 1));
        questions.add(new Question("Which symbol means 'equal to'?", List.of("=", "==", "!="), 1));
        questions.add(new Question("What does 'elif' stand for?", List.of("Else", "Else If", "End Life"), 1));
        questions.add(new Question("When does code in an 'if' run?", List.of("Always", "When condition is True", "Never"), 1));
        questions.add(new Question("Can we nest if statements?", List.of("Yes", "No", "Maybe"), 0));
        return questions;
    }

    private List<Question> createQuestions6() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What does a loop do?", List.of("Stop", "Go Fast", "Repeat"), 2));
        questions.add(new Question("'Clap 3 times', how many claps?", List.of("1", "2", "3"), 2));
        questions.add(new Question("Which word means 'repeat'?", List.of("End", "Loop", "Start"), 1));
        questions.add(new Question("For loops can count...", List.of("Backward only", "Forward only", "Both ways"), 2));
        questions.add(new Question("Loop is like singing...", List.of("Once", "Over & Over", "Silently"), 1));
        return questions;
    }

    private List<Question> createQuestions7() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What is a list like?", List.of("One thing", "Things in order", "Messy pile"), 1));
        questions.add(new Question("[Car,Ball,Doll], first toy?", List.of("Ball", "Doll", "Car"), 2));
        questions.add(new Question("Lists can hold different types?", List.of("Yes", "No", "Only numbers"), 0));
        questions.add(new Question("Shape of list brackets?", List.of("( )", "{ }", "[ ]"), 2));
        questions.add(new Question("Which adds to a list?", List.of("append()", "remove()", "find()"), 0));
        return questions;
    }

    // Add this inner class to represent lesson items (both main lessons and sub-lessons)
    public static class LessonItem {
        private Lesson lesson;
        private String subtitle;
        private String subLessonId;
        private boolean isSubLesson;

        public LessonItem(Lesson lesson, String subtitle, String subLessonId, boolean isSubLesson) {
            this.lesson = lesson;
            this.subtitle = subtitle;
            this.subLessonId = subLessonId;
            this.isSubLesson = isSubLesson;
        }

        public Lesson getLesson() {
            return lesson;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public String getSubLessonId() {
            return subLessonId;
        }

        public boolean isSubLesson() {
            return isSubLesson;
        }

        public String getMainLessonId() {
            return lesson.getId();
        }
    }

    // Add this custom adapter class
    private class LessonListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int VIEW_TYPE_MAIN_LESSON = 0;
        private static final int VIEW_TYPE_SUB_LESSON = 1;

        private List<LessonItem> lessonItems;
        private LessonProgressManager progressManager;

        public LessonListAdapter(List<LessonItem> lessonItems) {
            this.lessonItems = lessonItems;
            this.progressManager = new LessonProgressManager(HomeActivity.this);
        }

        @Override
        public int getItemViewType(int position) {
            return lessonItems.get(position).isSubLesson() ? VIEW_TYPE_SUB_LESSON : VIEW_TYPE_MAIN_LESSON;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == VIEW_TYPE_MAIN_LESSON) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_lesson, parent, false);
                return new MainLessonViewHolder(view);
            } else {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_sub_lesson, parent, false);
                return new SubLessonViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            LessonItem item = lessonItems.get(position);
            Lesson lesson = item.getLesson();

            if (holder instanceof MainLessonViewHolder) {
                // Main lesson
                MainLessonViewHolder mainHolder = (MainLessonViewHolder) holder;

                mainHolder.lessonIcon.setImageResource(lesson.getIconResourceId());

                // Extract the lesson number and title
                String title = lesson.getTitle();
                int dotIndex = title.indexOf('.');
                if (dotIndex > 0 && dotIndex < title.length() - 1) {
                    mainHolder.lessonNumber.setText(title.substring(0, dotIndex + 1));
                    mainHolder.lessonTitle.setText(title.substring(dotIndex + 1).trim());
                } else {
                    mainHolder.lessonNumber.setText("");
                    mainHolder.lessonTitle.setText(title);
                }

                // Set the subtitle
                mainHolder.lessonSubtitle.setText(item.getSubtitle());

                // Check if the lesson is unlocked
                boolean isUnlocked = progressManager.isLessonUnlocked(lesson.getId());

                // Set the lock/unlock icon
                if (isUnlocked) {
                    mainHolder.lockIcon.setImageResource(R.drawable.ic_unlock);
                } else {
                    mainHolder.lockIcon.setImageResource(R.drawable.ic_lock);
                }

                // Set background color based on the lesson
                int lessonNumber;
                try {
                    lessonNumber = Integer.parseInt(lesson.getId().substring(1));
                } catch (NumberFormatException e) {
                    lessonNumber = 1;
                }

                // Set background color
                int backgroundResId;
                switch ((lessonNumber - 1) % 7) {
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
                    case 5: // L6
                        backgroundResId = R.drawable.lesson_bg_red;
                        break;
                    case 6: // L7
                        backgroundResId = R.drawable.lesson_bg_blue;
                        break;
                    default:
                        backgroundResId = android.R.color.white;
                        break;
                }

                // Apply background
                View lessonContainer = mainHolder.itemView.findViewById(R.id.lesson_container);
                lessonContainer.setBackground(ContextCompat.getDrawable(mainHolder.itemView.getContext(), backgroundResId));

                // Handle click based on lock status
                mainHolder.itemView.setOnClickListener(v -> {
                    if (isUnlocked) {
                        // Toggle expanded state for this lesson
                        String lessonId = lesson.getId();
                        boolean isExpanded = expandedLessons.getOrDefault(lessonId, false);
                        expandedLessons.put(lessonId, !isExpanded);

                        // Update visible lessons and refresh adapter
                        updateVisibleLessonItems();
                        notifyDataSetChanged();

                        // If not expanded, launch the main lesson
                        if (!isExpanded) {
                            Intent intent = new Intent(HomeActivity.this, LessonActivity.class);
                            intent.putExtra("LESSON_ID", lesson.getId());
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(HomeActivity.this,
                                "Complete previous lessons to unlock!",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            } else if (holder instanceof SubLessonViewHolder) {
                // Sub-lesson
                SubLessonViewHolder subHolder = (SubLessonViewHolder) holder;

                subHolder.subLessonTitle.setText(item.getSubtitle());

                // Check if this sub-lesson is unlocked
                // Note: Different unlocking logic - first sub-lesson unlocks when main lesson is completed
                String mainLessonId = item.getMainLessonId();
                String subLessonId = item.getSubLessonId();

                boolean isUnlocked;

                // Check if main lesson is completed
                boolean isMainLessonCompleted = progressManager.isLessonCompleted(mainLessonId);

                // First sub-lesson is unlocked when main lesson is completed
                if (subLessonId != null && subLessonId.endsWith(".1") && isMainLessonCompleted) {
                    isUnlocked = true;
                }

                // Second sub-lesson is unlocked when first sub-lesson is completed
                else if (subLessonId != null && subLessonId.endsWith(".2")) {
                    String firstSubLessonId = subLessonId.substring(0, subLessonId.length() - 1) + "1";
                    isUnlocked = progressManager.isLessonCompleted(firstSubLessonId);
                }

                // Third sub-lesson is unlocked when second sub-lesson is completed
                else if (subLessonId != null && subLessonId.endsWith(".3")) {
                    String secondSubLessonId = subLessonId.substring(0, subLessonId.length() - 1) + "2";
                    isUnlocked = progressManager.isLessonCompleted(secondSubLessonId);
                } else {
                    isUnlocked = false;
                }

                // Set the lock/unlock icon
                if (isUnlocked) {
                    subHolder.lockIcon.setImageResource(R.drawable.ic_unlock);
                } else {
                    subHolder.lockIcon.setImageResource(R.drawable.ic_lock);
                }

                // Set background color (lighter version of main lesson)
                int lessonNumber;
                try {
                    lessonNumber = Integer.parseInt(mainLessonId.substring(1));
                } catch (NumberFormatException e) {
                    lessonNumber = 1;
                }

                // Set background color
                int backgroundResId;
                switch ((lessonNumber - 1) % 7) {
                    case 0: // L1
                        backgroundResId = R.drawable.lesson_bg_red_light;
                        break;
                    case 1: // L2
                        backgroundResId = R.drawable.lesson_bg_blue_light;
                        break;
                    case 2: // L3
                        backgroundResId = R.drawable.lesson_bg_green_light;
                        break;
                    case 3: // L4
                        backgroundResId = R.drawable.lesson_bg_yellow_light;
                        break;
                    case 4: // L5
                        backgroundResId = R.drawable.lesson_bg_orange_light;
                        break;
                    case 5: // L6
                        backgroundResId = R.drawable.lesson_bg_red_light;
                        break;
                    case 6: // L7
                        backgroundResId = R.drawable.lesson_bg_blue_light;
                        break;
                    default:
                        backgroundResId = android.R.color.white;
                        break;
                }

                // Apply background
                View subLessonContainer = subHolder.itemView.findViewById(R.id.sub_lesson_container);
                subLessonContainer.setBackground(ContextCompat.getDrawable(subHolder.itemView.getContext(), backgroundResId));

                // Handle click on sub-lesson
                subHolder.itemView.setOnClickListener(v -> {
                    if (isUnlocked) {
                        // Launch the sub-lesson
                        Intent intent = new Intent(HomeActivity.this, LessonActivity.class);
                        intent.putExtra("LESSON_ID", subLessonId);
                        startActivity(intent);
                    } else {
                        if (isMainLessonCompleted) {
                            Toast.makeText(HomeActivity.this,
                                    "Complete previous sub-lessons to unlock!",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(HomeActivity.this,
                                    "Complete the main lesson first to unlock this sub-lesson!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return lessonItems.size();
        }

        public class MainLessonViewHolder extends RecyclerView.ViewHolder {
            ImageView lessonIcon;
            ImageView lockIcon;
            TextView lessonTitle;
            TextView lessonNumber;
            TextView lessonSubtitle;

            public MainLessonViewHolder(@NonNull View itemView) {
                super(itemView);
                lessonIcon = itemView.findViewById(R.id.lesson_icon);
                lockIcon = itemView.findViewById(R.id.lock_icon);
                lessonTitle = itemView.findViewById(R.id.lesson_title);
                lessonNumber = itemView.findViewById(R.id.lesson_number);
                lessonSubtitle = itemView.findViewById(R.id.lesson_subtitle);
            }
        }

        public class SubLessonViewHolder extends RecyclerView.ViewHolder {
            TextView subLessonTitle;
            ImageView lockIcon;

            public SubLessonViewHolder(@NonNull View itemView) {
                super(itemView);
                subLessonTitle = itemView.findViewById(R.id.sub_lesson_title);
                lockIcon = itemView.findViewById(R.id.sub_lesson_lock_icon);
            }
        }
    }

    // Setup the lesson list
    private void setupLessonList() {
        LessonListAdapter adapter = new LessonListAdapter(visibleLessonItems);
        lessonRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Update visible lessons based on expanded state
        updateVisibleLessonItems();

        // Create a fresh adapter to ensure updated data
        LessonListAdapter adapter = new LessonListAdapter(visibleLessonItems);
        lessonRecyclerView.setAdapter(adapter);

        // Add detailed logging to track lesson unlock status
        Log.d("HomeActivity", "onResume: Refreshing lesson adapter");
        LessonProgressManager progressMgr = new LessonProgressManager(this);

        for (LessonItem item : allLessonItems) {
            if (!item.isSubLesson()) {
                String lessonId = item.getLesson().getId();
                boolean isCompleted = progressMgr.isLessonCompleted(lessonId);
                boolean isUnlocked = progressMgr.isLessonUnlocked(lessonId);
                Log.d("HomeActivity", "Lesson " + lessonId +
                        " - Completed: " + isCompleted +
                        ", Unlocked: " + isUnlocked);
            }
        }
    }
}