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
    private List<SubLesson> allSubLessons; // Added for sub-lessons
    private SharedPreferences sharedPreferences;
    private int quizScore = 0; // Track the quiz score

    private LessonProgressManager progressManager;
    private AchievementManager achievementManager;

    @Override
    protected void onCreate(Bundle sIS) {
        super.onCreate(sIS);
        setContentView(com.example.BlockPy.R.layout.activity_lesson);

        // Initialize SharedPreferences for lesson progress
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        progressManager = new LessonProgressManager(this);
        achievementManager = new AchievementManager(this);

        Intent i = getIntent();
        if (i != null && i.hasExtra("LESSON_ID"))
            lessonId = i.getStringExtra("LESSON_ID");
        else {
            finish();
            return;
        }

        loadAllLessonsTemporarily();
        loadAllSubLessonsTemporarily(); // Load all sub-lessons

        // Check if this is a sub-lesson or main lesson
        if (lessonId.contains(".")) {
            // This is a sub-lesson
            SubLesson subLesson = findSubLessonById(lessonId);
            if (subLesson == null) {
                Log.e(TAG, "Sub-lesson not found: " + lessonId);
                finish();
                return;
            }

            // Check if sub-lesson is unlocked
            if (!progressManager.isSubLessonUnlocked(lessonId)) {
                Toast.makeText(this, "This sub-lesson is locked! Complete previous lessons first.", Toast.LENGTH_LONG).show();
                finish();
                return;
            }

            // Show sub-lesson content
            if (sIS == null) showSubLessonContent(subLesson);
        } else {
            // This is a main lesson
            currentLesson = findLessonById(lessonId);
            if (currentLesson == null) {
                Log.e(TAG, "Lesson not found: " + lessonId);
                finish();
                return;
            }

            // Check if lesson is unlocked
            if (!progressManager.isLessonUnlocked(lessonId)) {
                Toast.makeText(this, "This lesson is locked! Complete previous lessons first.", Toast.LENGTH_LONG).show();
                finish();
                return;
            }

            // Show main lesson content
            if (sIS == null) showLectureFragment();
        }
    }

    // Load all main lessons temporarily
    private void loadAllLessonsTemporarily() {
        // Previous implementation...
        allLessons = new ArrayList<>();

        // Add main lessons here...
        // Lesson 1 - Introduction to Python
        allLessons.add(new Lesson("L1", "1. Introduction to Python", R.drawable.achievement_intro,
                "Welcome to Python programming! Learn what makes Python special.",
                0, R.drawable.lesson_bg_red, "Python Explorer!", 0, createQuestionsL1()));

        // Additional lessons
        // ...
    }

    // Load all sub-lessons temporarily
    private void loadAllSubLessonsTemporarily() {
        allSubLessons = new ArrayList<>();

        // Sub-lessons for Lesson 1
        allSubLessons.add(new SubLesson("L1.1", "Meet Python the Snake",
                "Python is a programming language named after the snake!\n\n" +
                        "Python was created in 1991 by Guido van Rossum. It's designed to be easy to read and write. " +
                        "The name Python was inspired by the British comedy show 'Monty Python's Flying Circus'.\n\n" +
                        "Python is one of the most popular programming languages in the world, used for websites, games, " +
                        "apps, and even artificial intelligence!",
                createQuestionsL1_1()));

        allSubLessons.add(new SubLesson("L1.2", "What is a Computer Program?",
                "A computer program is a set of instructions that tells a computer what to do.\n\n" +
                        "Think of it like a recipe - it's a list of steps to follow in order. " +
                        "The computer follows these steps exactly as written.\n\n" +
                        "Programs can do many things, from simple calculators to complex video games. " +
                        "Python is one language we can use to write these instructions in a way " +
                        "that's easier for humans to understand, but that the computer can still follow.",
                createQuestionsL1_2()));

        allSubLessons.add(new SubLesson("L1.3", "How Python Helps Us",
                "Python makes programming easier and more fun!\n\n" +
                        "Python is designed to be easy to read and write. It uses simple English words " +
                        "and doesn't need complicated symbols like other languages.\n\n" +
                        "Python can help us:\n" +
                        "- Solve math problems\n" +
                        "- Work with text\n" +
                        "- Make games\n" +
                        "- Create websites\n" +
                        "- Analyze data\n" +
                        "- Control robots\n\n" +
                        "In this course, we'll learn how to use Python to solve problems and create cool things!",
                createQuestionsL1_3()));

        // Sub-lessons for other main lessons
        // ...
    }

    // Find a sub-lesson by ID
    private SubLesson findSubLessonById(String id) {
        if (id == null || allSubLessons == null) return null;
        for (SubLesson subLesson : allSubLessons) {
            if (subLesson != null && id.equals(subLesson.getId())) return subLesson;
        }
        return null;
    }

    // Find a main lesson by ID
    private Lesson findLessonById(String id) {
        if (id == null || allLessons == null) return null;
        for (Lesson lesson : allLessons) {
            if (lesson != null && id.equals(lesson.getId())) return lesson;
        }
        return null;
    }

    // Show sub-lesson content
    private void showSubLessonContent(SubLesson subLesson) {
        // Create a fragment for the sub-lesson lecture
        SubLessonLectureFragment fragment = SubLessonLectureFragment.newInstance(subLesson);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    // Show main lesson content
    private void showLectureFragment() {
        if (currentLesson == null) return;
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) == null) {
            LectureFragment f = LectureFragment.newInstance(currentLesson);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
        }
    }

    // Proceed to quiz from either a main lesson or sub-lesson
    public void proceedToQuiz() {
        Log.d(TAG, "Proceed to Quiz for " + lessonId);

        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof QuizFragment) return;

        List<Question> questions = null;

        if (lessonId.contains(".")) {
            // This is a sub-lesson
            SubLesson subLesson = findSubLessonById(lessonId);
            if (subLesson != null) {
                questions = subLesson.getQuestions();
            }
        } else {
            // This is a main lesson
            if (currentLesson != null) {
                questions = currentLesson.getQuestions();
            }
        }

        if (questions == null || questions.isEmpty()) {
            Log.e(TAG, "No Questions for " + lessonId);
            lessonComplete();
            return;
        }

        QuizFragment f = QuizFragment.newInstance(new ArrayList<>(questions));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, f)
                .addToBackStack("Quiz")
                .commit();
    }

    // Show quiz result
    public void showQuizResult(int score, int total) {
        Log.d(TAG, "Show Result:" + score + "/" + total);
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

    // Complete the lesson or sub-lesson
    public void lessonComplete() {
        Log.d(TAG, "Lesson Complete: " + lessonId);

        // Mark as completed with the quiz score
        boolean success = progressManager.markLessonAsCompleted(lessonId, quizScore);
        Log.d(TAG, "Lesson " + lessonId + " marked complete: " + success);

        // Verify completion
        boolean isCompleted = progressManager.isLessonCompleted(lessonId);
        Log.d(TAG, "Verification - Lesson " + lessonId + " is now marked as completed: " + isCompleted);

        // Show achievement only for main lessons
        if (!lessonId.contains(".")) {
            achievementManager.checkAndShowAchievement(lessonId, quizScore);
        }

        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        new Handler(Looper.getMainLooper()).postDelayed(this::finish, 500);
    }

    @Override
    public void finish() {
        super.finish();
    }

    // Helper methods to create questions for the main lessons and sub-lessons
    // Here are example question sets for Lesson 1 and its sub-lessons

    private List<Question> createQuestionsL1() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What kind of animal is Python named after?",
                List.of("Bird", "Snake", "Fish"), 1));
        questions.add(new Question("What does a programmer create?",
                List.of("Food", "Computer Programs", "Books"), 1));
        questions.add(new Question("What can Python help us do?",
                List.of("Swim Fast", "Solve Problems", "Fly"), 1));
        questions.add(new Question("Python is easy to:",
                List.of("Eat", "Read", "Throw"), 1));
        questions.add(new Question("Python was created in:",
                List.of("2020", "1991", "1800"), 1));
        return questions;
    }

    private List<Question> createQuestionsL1_1() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("Who created Python?",
                List.of("Steve Jobs", "Guido van Rossum", "Bill Gates"), 1));
        questions.add(new Question("When was Python created?",
                List.of("1981", "1991", "2001"), 1));
        questions.add(new Question("What inspired the name Python?",
                List.of("A real snake", "Monty Python's Flying Circus", "A mountain in Greece"), 1));
        questions.add(new Question("Python is designed to be:",
                List.of("Hard to read but fast", "Easy to read and write", "Only for experts"), 1));
        questions.add(new Question("Is Python a popular programming language?",
                List.of("Yes, one of the most popular", "No, very few people use it", "It's only used in schools"), 0));
        return questions;
    }

    private List<Question> createQuestionsL1_2() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("A computer program is:",
                List.of("A type of computer", "A set of instructions", "A video game"), 1));
        questions.add(new Question("What can programs be compared to?",
                List.of("A recipe", "A painting", "A house"), 0));
        questions.add(new Question("How does a computer follow program instructions?",
                List.of("Makes its own decisions", "Follows steps exactly", "Randomly picks steps"), 1));
        questions.add(new Question("Which of these can be a computer program?",
                List.of("A calculator", "A wooden desk", "A pencil"), 0));
        questions.add(new Question("Programming languages help humans:",
                List.of("Write instructions computers understand", "Build physical computers", "Design keyboards"), 0));
        return questions;
    }

    private List<Question> createQuestionsL1_3() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("Python uses:",
                List.of("Complicated symbols", "Simple English words", "Only numbers"), 1));
        questions.add(new Question("Which of these can Python help with?",
                List.of("Cooking food", "Creating websites", "Building houses"), 1));
        questions.add(new Question("Python is good for beginners because:",
                List.of("It's easy to read", "It's the oldest language", "It only works on phones"), 0));
        questions.add(new Question("Can Python be used to make games?",
                List.of("Yes", "No", "Only card games"), 0));
        questions.add(new Question("Python can help analyze:",
                List.of("Weather", "Data", "Paintings"), 1));
        return questions;
    }

    // Create question sets for other lessons and sub-lessons...
}