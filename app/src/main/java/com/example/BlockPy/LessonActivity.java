package com.example.BlockPy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.*;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.*;
import android.util.Log;
import android.widget.Toast;

import com.example.BlockPy.R;

import java.io.Serializable;
import java.util.*;

public class LessonActivity extends AppCompatActivity {
    private static final String TAG = "LessonActivity";
    private static final String PREF_NAME = "BlockPyLessonProgress";
    private static final String KEY_LESSON_COMPLETED = "lesson_completed_";

    private String lessonId;
    private Lesson currentLesson;
    private List<Lesson> allLessons;
    private SharedPreferences sharedPreferences;
    private int quizScore = 0; // Track the quiz score

    private LessonProgressManager progressManager;
    private AchievementManager achievementManager; // Add AchievementManager

    @Override
    protected void onCreate(Bundle sIS) {
        super.onCreate(sIS);
        setContentView(com.example.BlockPy.R.layout.activity_lesson);

        // Initialize SharedPreferences for lesson progress
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        progressManager = new LessonProgressManager(this);
        achievementManager = new AchievementManager(this); // Initialize AchievementManager

        Intent i = getIntent();
        if (i != null && i.hasExtra("LESSON_ID"))
            lessonId = i.getStringExtra("LESSON_ID");
        else {
            finish();
            return;
        }

        loadAllLessonsTemporarily();
        currentLesson = findLessonById(lessonId);

        if (currentLesson == null) {
            Log.e(TAG, "Not found:" + lessonId);
            finish();
            return;
        }

        // Check if lesson is unlocked
        if (!isLessonUnlocked(lessonId)) {
            Toast.makeText(this, "This lesson is locked! Complete previous lessons first.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        if (sIS == null) showLectureFragment();
    }

    // --- Data Loading (MUST MATCH MainActivity.loadLessons EXACTLY) ---
    private void loadAllLessonsTemporarily() {
        Log.d(TAG, "loadAllLessonsTemporarily called.");
        allLessons = new ArrayList<>();
        // PASTE FULL LESSON DATA FROM MainActivity.loadLessons HERE, using allLessons.add()
        int placeholderIcon = android.R.drawable.star_on;
        int placeholderBackground = R.color.purple_200;
        int noAudio = 0;
        int noSound = 0;
        // L1
        String cd1 = "To make Python show something on the screen...";
        List<Question> q1 = new ArrayList<>();
        q1.add(new Question("What makes things appear?", List.of("Thinking", "Printing", "Sleeping"), 1));
        q1.add(new Question("Which word means 'show this'?", List.of("Hide", "Print", "Run"), 1));
        q1.add(new Question("If code says print \"Apple\"...", List.of("Banana", "Apple", "Orange"), 1));
        q1.add(new Question("What shape are quotes \" \"?", List.of("Circles", "Squares", "Curvy Lines"), 2));
        q1.add(new Question("Does print 'Hello' == print \"Hello\"?", List.of("Yes", "No", "Maybe"), 0));
        allLessons.add(new Lesson("L1", "1. Print Statement", placeholderIcon, cd1, noAudio, placeholderBackground, "Awesome Printing!", noSound, q1));
        // L2
        String cd2 = "Variables are like containers or boxes...";
        List<Question> q2 = new ArrayList<>();
        q2.add(new Question("What is a variable like?", List.of("A Cloud", "A Labeled Box", "A River"), 1));
        q2.add(new Question("If 'box = Banana'...", List.of("Apple", "Banana", "Grapes"), 1));
        q2.add(new Question("Can a variable box hold different things?", List.of("Yes", "No", "Only Toys"), 0));
        q2.add(new Question("What does the '=' sign do?", List.of("Empty", "Put IN", "Check same"), 1));
        q2.add(new Question("If 'pet = \"Dog\"', name is?", List.of("Dog", "\"Dog\"", "pet"), 2));
        allLessons.add(new Lesson("L2", "2. Variables", placeholderIcon, cd2, noAudio, placeholderBackground, "Super Variable!", noSound, q2));
        // L3
        String cd3 = "Python can do math! Use '+'...";
        List<Question> q3 = new ArrayList<>();
        q3.add(new Question("What does '+' do?", List.of("Take Away", "Count", "Add Together"), 2));
        q3.add(new Question("What is 2 + 3?", List.of("4", "5", "6"), 1));
        q3.add(new Question("What does '-' do?", List.of("Add", "Take Away", "Bigger"), 1));
        q3.add(new Question("What is 5 - 1?", List.of("3", "4", "6"), 1));
        q3.add(new Question("2 apples + 2 more = ?", List.of("2", "3", "4"), 2));
        allLessons.add(new Lesson("L3", "3. Operations", placeholderIcon, cd3, noAudio, placeholderBackground, "Math Whiz!", noSound, q3));
        // L4
        String cd4 = "Loops let you repeat actions!...";
        List<Question> q4 = new ArrayList<>();
        q4.add(new Question("What does a loop do?", List.of("Stop", "Go Fast", "Repeat"), 2));
        q4.add(new Question("'Clap 3 times', how many claps?", List.of("1", "2", "3"), 2));
        q4.add(new Question("Which word means 'repeat'?", List.of("End", "Loop", "Start"), 1));
        q4.add(new Question("Loops help do things easily?", List.of("Yes", "No", "Once"), 0));
        q4.add(new Question("Loop is like singing...", List.of("Once", "Over & Over", "Silently"), 1));
        allLessons.add(new Lesson("L4", "4. Loops", placeholderIcon, cd4, noAudio, placeholderBackground, "Loop Master!", noSound, q4));
        // L5
        String cd5 = "A List stores multiple items in order...";
        List<Question> q5 = new ArrayList<>();
        q5.add(new Question("What is a list like?", List.of("One thing", "Things in order", "Messy pile"), 1));
        q5.add(new Question("[Car,Ball,Doll], first toy?", List.of("Ball", "Doll", "Car"), 2));
        q5.add(new Question("List hold different kinds?", List.of("Yes", "No", "Only blocks"), 0));
        q5.add(new Question("Shape of list brackets [ ]?", List.of("Circles", "Wavy", "Square"), 2));
        q5.add(new Question("A list helps keep things...", List.of("Hidden", "Organized", "Lost"), 1));
        allLessons.add(new Lesson("L5", "5. Array (List)", placeholderIcon, cd5, noAudio, placeholderBackground, "List Pro!", noSound, q5));
        Log.d(TAG, "Loaded " + allLessons.size() + " lessons temp.");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (allLessons.stream().anyMatch(l -> l == null || l.getQuestions() == null || l.getQuestions().isEmpty()))
                Log.e(TAG, "WARNING: Temp data incomplete!");
        }
    }

    private Lesson findLessonById(String id) {
        if (id == null || allLessons == null) return null;
        for (Lesson l : allLessons) {
            if (l != null && id.equals(l.getId())) return l;
        }
        Log.w(TAG, "ID not found:" + id);
        return null;
    }

    // Check if a lesson is unlocked based on previous lesson completion
    // In LessonActivity.java - replace the isLessonUnlocked method with this:
    private boolean isLessonUnlocked(String lessonId) {
        return progressManager.isLessonUnlocked(lessonId);
    }

    // Check if a lesson has been completed
    private boolean isLessonCompleted(String lessonId) {
        return sharedPreferences.getBoolean(KEY_LESSON_COMPLETED + lessonId, false);
    }

    // Mark the current lesson as completed
    private void markCurrentLessonAsCompleted() {
        if (currentLesson == null) return;

        String lessonId = currentLesson.getId();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_LESSON_COMPLETED + lessonId, true);
        editor.apply();

        Log.d(TAG, "Lesson marked as completed: " + lessonId);
    }

    // Helper method to get the previous lesson ID
    private String getPreviousLessonId(String currentId) {
        if (currentId == null) return null;

        switch (currentId) {
            case "L2": return "L1";
            case "L3": return "L2";
            case "L4": return "L3";
            case "L5": return "L4";
            default: return null; // L1 has no previous lesson
        }
    }

    private void showLectureFragment() {
        if (currentLesson == null) return;
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) == null) {
            LectureFragment f = LectureFragment.newInstance(currentLesson);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
        }
    }

    public void proceedToQuiz() {
        Log.d(TAG, "Proceed to Quiz");
        if (currentLesson == null) return;
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof QuizFragment) return;
        List<Question> q = currentLesson.getQuestions();
        boolean nq = (q == null || q.isEmpty());
        if (nq) {
            Log.e(TAG, "No Questions!");
            lessonComplete();
            return;
        }
        QuizFragment f = QuizFragment.newInstance(new ArrayList<>(q));
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).addToBackStack("Quiz").commit();
    }

    public void showQuizResult(int score, int total) {
        Log.d(TAG, "Show Result:" + score + "/" + total);
        // Save the score for when lesson is completed
        this.quizScore = score;

        getSupportFragmentManager().popBackStack("Quiz", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        QuizResultFragment f = QuizResultFragment.newInstance(score, total);
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        try {
            t.replace(R.id.fragment_container, f).commit();
        } catch (IllegalStateException e) {
            t.commitAllowingStateLoss();
        }
    }


    public void lessonComplete() {
        Log.d(TAG, "Lesson Complete");

        // Mark this lesson as completed with the quiz score
        if (currentLesson != null) {
            String lessonId = currentLesson.getId();

            // Mark the lesson as completed in the database
            boolean success = progressManager.markLessonAsCompleted(lessonId, quizScore);

            // Add logging to verify
            Log.d(TAG, "Lesson " + lessonId + " marked complete: " + success);

            // Check if the database actually shows the lesson as completed
            boolean isCompleted = progressManager.isLessonCompleted(lessonId);
            Log.d(TAG, "Verification - Lesson " + lessonId + " is now marked as completed: " + isCompleted);

            // Check and show achievement
            achievementManager.checkAndShowAchievement(lessonId, quizScore);
        }

        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        new Handler(Looper.getMainLooper()).postDelayed(this::finish, 500);
    }
    @Override
    public void finish() {
        super.finish();
    }
}