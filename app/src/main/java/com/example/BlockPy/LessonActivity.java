package com.example.BlockPy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
    private List<SubLesson> allSubLessons;
    private SharedPreferences sharedPreferences;
    private int quizScore = 0;

    private LessonProgressManager progressManager;
    private AchievementManager achievementManager;

    @Override
    protected void onCreate(Bundle sIS) {
        super.onCreate(sIS);
        setContentView(com.example.BlockPy.R.layout.activity_lesson);

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
        loadAllSubLessonsTemporarily();

        if (lessonId.contains(".")) {
            SubLesson subLesson = findSubLessonById(lessonId);
            if (subLesson == null) {
                Log.e(TAG, "Sub-lesson not found: " + lessonId);
                finish();
                return;
            }

            if (!progressManager.isSubLessonUnlocked(lessonId)) {
                Toast.makeText(this, "This sub-lesson is locked! Complete previous lessons first.", Toast.LENGTH_LONG).show();
                finish();
                return;
            }

            if (sIS == null) showSubLessonContent(subLesson);
        } else {
            currentLesson = findLessonById(lessonId);
            if (currentLesson == null) {
                Log.e(TAG, "Lesson not found: " + lessonId);
                finish();
                return;
            }

            if (!progressManager.isLessonUnlocked(lessonId)) {
                Toast.makeText(this, "This lesson is locked! Complete previous lessons first.", Toast.LENGTH_LONG).show();
                finish();
                return;
            }

            if (sIS == null) showLectureFragment();
        }
    }

    private void loadAllLessonsTemporarily() {
        allLessons = new ArrayList<>();

        // Lesson 1 - Introduction to Python
        allLessons.add(new Lesson("L1", "1. Introduction to Python", R.drawable.achievement_intro,
                "Welcome to the world of Python programming! Python is a powerful, beginner-friendly language " +
                        "that's used by professional developers, data scientists, and tech enthusiasts around the globe.\n\n" +
                        "In this lesson, you'll learn what makes Python special and why it's an excellent language for beginners " +
                        "and experienced programmers alike.",
                0, R.drawable.lesson_bg_red, "Python Explorer!", 0, createQuestionsL1()));

        // Lesson 2 - Print Statement
        allLessons.add(new Lesson("L2", "2. Print Statement", R.drawable.achievement_print,
                "The print() statement is your first step in making Python communicate! " +
                        "It's how you can make your program display text, numbers, and other information.\n\n" +
                        "Learn how to use print() to output messages, perform simple calculations, " +
                        "and start bringing your code to life.",
                0, R.drawable.lesson_bg_blue, "Printing Master!", 0, createQuestionsL2()));

        // Lesson 3 - Variables
        allLessons.add(new Lesson("L3", "3. Variables", R.drawable.achievement_variable,
                "Variables are like containers that store information in your program. " +
                        "They allow you to save and reuse data, making your code more flexible and powerful.\n\n" +
                        "Discover how to create variables, store different types of data, " +
                        "and manipulate information in your Python programs.",
                0, R.drawable.lesson_bg_green, "Variable Virtuoso!", 0, createQuestionsL3()));

        // Lesson 4 - Operations
        allLessons.add(new Lesson("L4", "4. Operations", R.drawable.achievement_operation,
                "Learn how to perform mathematical operations and comparisons in Python! " +
                        "This lesson covers basic arithmetic, how to do calculations, and how to compare values.\n\n" +
                        "You'll learn how to add, subtract, multiply, divide, and compare numbers " +
                        "to make your programs more interactive and intelligent.",
                0, R.drawable.lesson_bg_yellow, "Math Whiz!", 0, createQuestionsL4()));

        // Lesson 5 - Conditions
        allLessons.add(new Lesson("L5", "5. Conditions", R.drawable.achievement_condition,
                "Conditions are how programs make decisions! Learn to use if statements " +
                        "to create logic that makes your programs smart and responsive.\n\n" +
                        "You'll discover how to check different scenarios and make your code " +
                        "take different actions based on specific conditions.",
                0, R.drawable.lesson_bg_orange, "Decision Master!", 0, createQuestionsL5()));
    }

    private void loadAllSubLessonsTemporarily() {
        allSubLessons = new ArrayList<>();

        // Sub-lessons for Lesson 1: Introduction to Python
        allSubLessons.add(new SubLesson("L1.1", "Meet Python the Snake",
                "Python: More Than Just a Snake 🐍\n\n" +
                        "Python is a programming language named after the famous comedy group 'Monty Python'. " +
                        "Created by Guido van Rossum in 1991, it's designed to be clean, readable, and fun to use.\n\n" +
                        "Key Highlights:\n" +
                        "• Named after Monty Python's Flying Circus\n" +
                        "• Created by Guido van Rossum in 1991\n" +
                        "• Known for its simplicity and readability\n" +
                        "• Used in web development, data science, AI, and more!",
                createQuestionsL1_1()));

        allSubLessons.add(new SubLesson("L1.2", "What is a Computer Program?",
                "Understanding Computer Programs 💻\n\n" +
                        "A computer program is a set of instructions that tells a computer exactly what to do. " +
                        "Think of it like a recipe - a step-by-step guide that the computer follows precisely.\n\n" +
                        "Program Characteristics:\n" +
                        "• Sequence of clear, logical steps\n" +
                        "• Tells the computer how to solve a problem\n" +
                        "• Can range from simple calculations to complex applications\n" +
                        "• Written in programming languages like Python",
                createQuestionsL1_2()));

        allSubLessons.add(new SubLesson("L1.3", "How Python Helps Us",
                "Python: Your Problem-Solving Companion 🚀\n\n" +
                        "Python is an incredibly versatile language that can help solve real-world problems " +
                        "across many different fields. Its simplicity makes it perfect for beginners and professionals alike.\n\n" +
                        "What Can Python Do?\n" +
                        "• Create websites and web applications\n" +
                        "• Analyze scientific and statistical data\n" +
                        "• Build artificial intelligence and machine learning models\n" +
                        "• Automate boring tasks\n" +
                        "• Control robots and hardware\n" +
                        "• Create video games\n" +
                        "• And so much more!",
                createQuestionsL1_3()));

        // Sub-lessons for Lesson 2: Print Statement
        allSubLessons.add(new SubLesson("L2.1", "Making Python Talk",
                "Your First Python Communication 🗣️\n\n" +
                        "The print() function is how Python 'speaks' to you. It displays text and information " +
                        "directly on the screen, allowing your program to communicate its results.\n\n" +
                        "Basic Print Usage:\n" +
                        "• Use print() to show text\n" +
                        "• Can display words, numbers, and combinations\n" +
                        "• Helps you understand what your program is doing\n" +
                        "• Essential for debugging and displaying information\n\n" +
                        "Example:\n" +
                        "print(\"Hello, World!\")  # This will display: Hello, World!\n" +
                        "print(42)             # This will display: 42",
                createQuestionsL2_1()));

        allSubLessons.add(new SubLesson("L2.2", "Print with Words",
                "Printing Text in Python 📝\n\n" +
                        "Learn how to print words, sentences, and text using Python's print() function. " +
                        "Understand the difference between single and double quotes, and how to combine text.\n\n" +
                        "Text Printing Techniques:\n" +
                        "• Use quotes to define text (single or double)\n" +
                        "• Combine text with the + operator\n" +
                        "• Add spaces and punctuation\n\n" +
                        "Examples:\n" +
                        "print(\"Hello\")           # Prints: Hello\n" +
                        "print('Python is fun')   # Prints: Python is fun\n" +
                        "print(\"Hello\" + \" World\")  # Prints: Hello World",
                createQuestionsL2_2()));

        allSubLessons.add(new SubLesson("L2.3", "Print with Numbers",
                "Numbers and Calculations in Print 🔢\n\n" +
                        "Discover how to print numbers and perform calculations directly in your print statements. " +
                        "Learn to mix different types of data and understand how Python handles them.\n\n" +
                        "Number Printing Techniques:\n" +
                        "• Print integers and decimal numbers\n" +
                        "• Perform calculations inside print()\n" +
                        "• Combine numbers with text\n\n" +
                        "Examples:\n" +
                        "print(42)           # Prints: 42\n" +
                        "print(3.14)         # Prints: 3.14\n" +
                        "print(5 + 3)        # Prints: 8\n" +
                        "print(\"Age: \" + str(25))  # Prints: Age: 25",
                createQuestionsL2_3()));

        // Add more sub-lessons for subsequent lessons
    }

    // All question methods would be added here (createQuestionsL1(), createQuestionsL1_1(), etc.)
    // Methods for creating questions for each lesson and sub-lesson

    // Existing methods for finding lessons, showing content, etc.
    private SubLesson findSubLessonById(String id) {
        if (id == null || allSubLessons == null) return null;
        for (SubLesson subLesson : allSubLessons) {
            if (subLesson != null && id.equals(subLesson.getId())) return subLesson;
        }
        return null;
    }

    private Lesson findLessonById(String id) {
        if (id == null || allLessons == null) return null;
        for (Lesson lesson : allLessons) {
            if (lesson != null && id.equals(lesson.getId())) return lesson;
        }
        return null;
    }

    private void showSubLessonContent(SubLesson subLesson) {
        SubLessonLectureFragment fragment = SubLessonLectureFragment.newInstance(subLesson);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private void showLectureFragment() {
        if (currentLesson == null) return;
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) == null) {
            LectureFragment f = LectureFragment.newInstance(currentLesson);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
        }
    }

    // Methods to create questions would be very long, so I'd typically separate them into a different file or method
    // I'll show a few example question creation methods

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

    // More question methods would follow...

    // Other existing methods like proceedToQuiz(), showQuizResult(), lessonComplete(), etc.
    public void proceedToQuiz() {
        Log.d(TAG, "Proceed to Quiz for " + lessonId);

        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof QuizFragment) return;

        List<Question> questions = null;

        if (lessonId.contains(".")) {
            SubLesson subLesson = findSubLessonById(lessonId);
            if (subLesson != null) {
                questions = subLesson.getQuestions();
            }
        } else {
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

    public void lessonComplete() {
        Log.d(TAG, "Lesson Complete: " + lessonId);

        // Mark as completed with the quiz score
        boolean success = progressManager.markLessonAsCompleted(lessonId, quizScore);
        Log.d(TAG, "Lesson " + lessonId + " marked complete: " + success);

        // Verify completion
        boolean isCompleted = progressManager.isLessonCompleted(lessonId);
        Log.d(TAG, "Verification - Lesson " + lessonId + " is now marked as completed: " + isCompleted);

        // Special handling for sub-lessons
        if (lessonId.contains(".")) {
            // Unlock the next sub-lesson
            progressManager.unlockNextSubLesson(lessonId);
        } else {
            // Show achievement only for main lessons
            achievementManager.checkAndShowAchievement(lessonId, quizScore);
        }

        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        new Handler(Looper.getMainLooper()).postDelayed(this::finish, 500);
    }

    @Override
    public void finish() {
        super.finish();
    }

    // Question methods for sub-lessons and lessons
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

    private List<Question> createQuestionsL2_1() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What does print() do?",
                List.of("Save data", "Show information", "Delete code"), 1));
        questions.add(new Question("How do you print text?",
                List.of("print[Hello]", "print(Hello)", "print{Hello}"), 1));
        questions.add(new Question("Can print() show numbers?",
                List.of("No", "Yes", "Only integers"), 1));
        questions.add(new Question("print(5 + 3) shows:",
                List.of("5 + 3", "8", "Error"), 1));
        questions.add(new Question("Multiple items can be printed with:",
                List.of("print(x, y)", "print[x, y]", "print{x, y}"), 0));
        return questions;
    }

    private List<Question> createQuestionsL2_2() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("How to print a sentence?",
                List.of("print(Hello world)", "print['Hello world']", "print(\"Hello world\")"), 2));
        questions.add(new Question("Quotes are:",
                List.of("Always needed", "Never needed", "Sometimes needed"), 0));
        questions.add(new Question("Double vs single quotes:",
                List.of("No difference", "Different meanings", "Double quotes are wrong"), 1));
        questions.add(new Question("print(\"Hello\" + \"World\") shows:",
                List.of("Hello World", "HelloWorld", "Error"), 1));
        questions.add(new Question("Spaces in printing:",
                List.of("Always automatic", "Must be manually added", "Never allowed"), 1));
        return questions;
    }

    private List<Question> createQuestionsL2_3() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("Printing numbers is:",
                List.of("Impossible", "Very easy", "Requires special function"), 1));
        questions.add(new Question("print(42) shows:",
                List.of("forty-two", "4 2", "42"), 2));
        questions.add(new Question("Math in print works like:",
                List.of("print(5 + \"3\")", "print(5 + 3)", "print(5 * 3)"), 1));
        questions.add(new Question("Mixing numbers and text:",
                List.of("Always works", "Never works", "Requires conversion"), 2));
        questions.add(new Question("print() with multiple types:",
                List.of("Not possible", "Requires special syntax", "Easily done"), 1));
        return questions;
    }

    // Additional methods for creating questions for other lessons...
    private List<Question> createQuestionsL2() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What command shows text in Python?",
                List.of("show()", "print()", "display()"), 1));
        questions.add(new Question("Can print() show multiple things?",
                List.of("Yes", "No", "Only in special cases"), 0));
        questions.add(new Question("print(5 + 3) will display:",
                List.of("5 + 3", "8", "Error"), 1));
        questions.add(new Question("How do you print text in Python?",
                List.of("print[Hello]", "print(Hello)", "print{Hello}"), 1));
        questions.add(new Question("What happens with print()?",
                List.of("Saves text", "Shows text", "Deletes text"), 1));
        return questions;
    }

    private List<Question> createQuestionsL3() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What is a variable?",
                List.of("A computer", "A storage container", "A type of math"), 1));
        questions.add(new Question("How do you create a variable?",
                List.of("var x", "x = value", "create x"), 1));
        questions.add(new Question("Can a variable's value change?",
                List.of("No", "Yes", "Only once"), 1));
        questions.add(new Question("x = 10, then x = 20. What is x now?",
                List.of("10", "20", "1020"), 1));
        questions.add(new Question("Variables can store:",
                List.of("Only numbers", "Only text", "Numbers, text, and more"), 2));
        return questions;
    }

    private List<Question> createQuestionsL4() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What does + do?",
                List.of("Subtract", "Add", "Multiply"), 1));
        questions.add(new Question("How do you divide?",
                List.of("//", "/", "%"), 1));
        questions.add(new Question("What is 10 % 3?",
                List.of("3", "1", "0"), 1));
        questions.add(new Question("Which compares if equal?",
                List.of("=", "==", "==="), 1));
        questions.add(new Question("5 > 3 returns:",
                List.of("5", "True", "False"), 1));
        return questions;
    }

    private List<Question> createQuestionsL5() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What does an if statement do?",
                List.of("Repeat code", "Make decisions", "Draw graphics"), 1));
        questions.add(new Question("Conditions return:",
                List.of("Words", "Numbers", "True/False"), 2));
        questions.add(new Question("else: is used when:",
                List.of("First condition", "No conditions are True", "Always"), 1));
        questions.add(new Question("Can conditions be combined?",
                List.of("No", "Yes", "Only sometimes"), 1));
        questions.add(new Question("Which checks multiple conditions?",
                List.of("if-then", "if-else", "elif"), 2));
        return questions;
    }
}