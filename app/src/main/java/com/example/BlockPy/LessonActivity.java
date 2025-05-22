package com.example.BlockPy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.*;
import android.util.Log;
import android.widget.Toast;
import android.widget.ImageButton;

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

        // Set up toolbar
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            ImageButton backButton = toolbar.findViewById(R.id.back_button);
            if (backButton != null) {
                backButton.setOnClickListener(v -> onBackPressed());
            }
        }

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
            if (sIS == null) showSubLessonContent(subLesson);
        } else {
            currentLesson = findLessonById(lessonId);
            if (currentLesson == null) {
                Log.e(TAG, "Lesson not found: " + lessonId);
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
                        "and experienced programmers alike.\n\n" +
                        "Python was created by Guido van Rossum in 1991 and named after the comedy show 'Monty Python's Flying Circus'. " +
                        "It was designed to be readable, simple, and fun to use.\n\n" +
                        "Key features that make Python great for beginners:\n" +
                        "• Easy-to-read syntax that uses indentation and fewer symbols\n" +
                        "• English-like commands that make code easier to understand\n" +
                        "• A large community that creates libraries and tools\n" +
                        "• Versatility - can be used for websites, games, data analysis, AI, and more!",
                0, R.drawable.lesson_bg_red, "Python Explorer!", 0, createQuestionsL1()));

        // Lesson 2 - Print Statement
        allLessons.add(new Lesson("L2", "2. Print Statement", R.drawable.achievement_print,
                "The print() statement is your first step in making Python communicate! " +
                        "It's how you can make your program display text, numbers, and other information.\n\n" +
                        "print() is one of the most fundamental functions in Python - it displays output to the screen. " +
                        "This is essential for showing results, debugging your code, and communicating with users.\n\n" +
                        "Basic usage:\n" +
                        "print(\"Hello, World!\")\n\n" +
                        "You can print:\n" +
                        "• Text using quotes: print(\"Hello!\")\n" +
                        "• Numbers without quotes: print(42)\n" +
                        "• Multiple items separated by commas: print(\"The answer is\", 42)\n" +
                        "• Variables: x = 5, print(x)\n" +
                        "• Calculations: print(5 + 3)\n\n" +
                        "The print() function automatically adds a newline at the end, so the next print() will display on a new line.",
                0, R.drawable.lesson_bg_blue, "Printing Master!", 0, createQuestionsL2()));

        // Lesson 3 - Variables
        allLessons.add(new Lesson("L3", "3. Variables", R.drawable.achievement_variable,
                "Variables are like labeled containers that store information in your program. " +
                        "They allow you to save and reuse data, making your code more flexible and powerful.\n\n" +
                        "In Python, creating variables is simple - just use a name, the equals sign, and a value:\n\n" +
                        "age = 10\n" +
                        "name = \"Alex\"\n" +
                        "is_student = True\n\n" +
                        "Python variables have these important features:\n" +
                        "• No declaration needed - just assign a value\n" +
                        "• Type is determined automatically based on the value\n" +
                        "• Names should use lowercase letters and underscores\n" +
                        "• Names can include letters, numbers, and underscores (but can't start with a number)\n" +
                        "• They're case-sensitive (age and Age are different variables)\n\n" +
                        "Variables can store many types of data:\n" +
                        "• Numbers (integers, decimals)\n" +
                        "• Text (strings)\n" +
                        "• True/False values (booleans)\n" + 
                        "• Collections (lists, dictionaries)\n" +
                        "• And much more!\n\n" +
                        "You can also change a variable's value at any time by assigning a new value to it.",
                0, R.drawable.lesson_bg_green, "Variable Virtuoso!", 0, createQuestionsL3()));

        // Lesson 4 - Operations
        allLessons.add(new Lesson("L4", "4. Operations", R.drawable.achievement_operation,
                "Operations are the actions that transform and combine values in Python. " +
                        "They're essential for calculations, comparisons, and manipulating data.\n\n" +
                        "Mathematical Operations:\n" +
                        "• Addition: 5 + 3 (equals 8)\n" +
                        "• Subtraction: 10 - 4 (equals 6)\n" +
                        "• Multiplication: 3 * 4 (equals 12)\n" +
                        "• Division: 20 / 5 (equals 4.0) - Note: always returns a decimal\n" +
                        "• Integer Division: 20 // 3 (equals 6) - Divides and rounds down\n" +
                        "• Modulus (remainder): 10 % 3 (equals 1) - The remainder after division\n" +
                        "• Exponentiation: 2 ** 3 (equals 8) - 2 raised to the power of 3\n\n" +
                        "Comparison Operations:\n" +
                        "• Equal to: a == b\n" +
                        "• Not equal to: a != b\n" +
                        "• Greater than: a > b\n" +
                        "• Less than: a < b\n" +
                        "• Greater than or equal to: a >= b\n" +
                        "• Less than or equal to: a <= b\n\n" +
                        "These comparison operations return True or False values, which are useful for making decisions in your code.",
                0, R.drawable.lesson_bg_yellow, "Math Whiz!", 0, createQuestionsL4()));

        // Lesson 5 - Conditions
        allLessons.add(new Lesson("L5", "5. Conditions", R.drawable.achievement_condition,
                "Conditions allow your program to make decisions by checking if something is true or false. " +
                        "This is done using if statements, which run code only when certain conditions are met.\n\n" +
                        "Basic if statement structure:\n\n" +
                        "if condition:\n" +
                        "    # Code to run if condition is True\n\n" +
                        "For example:\n\n" +
                        "age = 15\n" +
                        "if age >= 13:\n" +
                        "    print(\"You can watch this movie!\")\n\n" +
                        "You can also add an else statement to handle the case when the condition is False:\n\n" +
                        "if age >= 13:\n" +
                        "    print(\"You can watch this movie!\")\n" +
                        "else:\n" +
                        "    print(\"Sorry, you're too young for this movie.\")\n\n" +
                        "For multiple conditions, use elif (short for 'else if'):\n\n" +
                        "if age < 13:\n" +
                        "    print(\"You can watch G-rated movies.\")\n" +
                        "elif age < 17:\n" +
                        "    print(\"You can watch PG-13 movies.\")\n" +
                        "else:\n" +
                        "    print(\"You can watch any movie.\")\n\n" +
                        "Conditions can use comparison operators (==, !=, >, <, >=, <=) and can be combined with logical operators:\n" +
                        "• and: Both conditions must be True\n" +
                        "• or: At least one condition must be True\n" +
                        "• not: Reverses a condition (True becomes False, False becomes True)",
                0, R.drawable.lesson_bg_orange, "Decision Master!", 0, createQuestionsL5()));

        // Lesson 6 - Loops
        allLessons.add(new Lesson("L6", "6. Loops", R.drawable.achievement_loop,
                "Loops allow you to repeat code multiple times without copy-pasting. " +
                        "They're essential for processing collections of data and automating repetitive tasks.\n\n" +
                        "Python has two main types of loops:\n\n" +
                        "1. For Loops - Used when you know how many times you want to repeat something:\n\n" +
                        "for variable in sequence:\n" +
                        "    # Code to repeat\n\n" +
                        "Examples:\n" +
                        "• Loop through a range of numbers:\n" +
                        "  for i in range(5):  # 0, 1, 2, 3, 4\n" +
                        "      print(i)\n\n" +
                        "• Loop through a list:\n" +
                        "  fruits = [\"apple\", \"banana\", \"cherry\"]\n" +
                        "  for fruit in fruits:\n" +
                        "      print(fruit)\n\n" +
                        "2. While Loops - Used when you want to repeat something until a condition changes:\n\n" +
                        "while condition:\n" +
                        "    # Code to repeat until condition becomes False\n\n" +
                        "Example:\n" +
                        "  count = 0\n" +
                        "  while count < 5:\n" +
                        "      print(count)\n" +
                        "      count += 1  # Increment to eventually end the loop\n\n" +
                        "Special loop controls:\n" +
                        "• break - Exits the loop completely\n" +
                        "• continue - Skips the current iteration and moves to the next one\n\n" +
                        "Loops are powerful tools that help automate repetitive tasks in your code.",
                0, R.drawable.lesson_bg_red, "Loop Master!", 0, createQuestionsL6()));

        // Lesson 7 - Arrays (Lists)
        allLessons.add(new Lesson("L7", "7. Arrays (Lists)", R.drawable.achievement_array,
                "Lists in Python (similar to arrays in other languages) are collections of items stored in a single variable. " +
                        "They allow you to store multiple related values together and work with them as a group.\n\n" +
                        "Creating lists:\n" +
                        "fruits = [\"apple\", \"banana\", \"cherry\"]\n" +
                        "numbers = [1, 2, 3, 4, 5]\n" +
                        "mixed = [\"hello\", 42, True, 3.14]  # Lists can contain different data types\n\n" +
                        "Accessing list items (using indexes that start at 0):\n" +
                        "print(fruits[0])  # Prints \"apple\"\n" +
                        "print(fruits[1])  # Prints \"banana\"\n" +
                        "print(fruits[-1])  # Prints \"cherry\" (negative indexes count from the end)\n\n" +
                        "Common list operations:\n" +
                        "• Add an item: fruits.append(\"orange\")\n" +
                        "• Insert at position: fruits.insert(1, \"kiwi\")\n" +
                        "• Remove item: fruits.remove(\"banana\")\n" +
                        "• Remove by index: del fruits[0]\n" +
                        "• Get list length: len(fruits)\n" +
                        "• Check if item exists: \"apple\" in fruits  # Returns True or False\n" +
                        "• Sort a list: fruits.sort()\n\n" +
                        "Lists are mutable, meaning you can change, add, or remove items after the list is created. " +
                        "This makes them very flexible and useful for storing collections of data that might change.",
                0, R.drawable.lesson_bg_blue, "List Pro!", 0, createQuestionsL7()));
    }

    private void loadAllSubLessonsTemporarily() {
        allSubLessons = new ArrayList<>();

        // Sub-lessons for Lesson 1: Introduction to Python
        allSubLessons.add(new SubLesson("L1.1", "Meet Python the Snake",
                "Python: More Than Just a Snake 🐍\n\n" +
                        "Python is a programming language named after the famous comedy group 'Monty Python', not the snake! " +
                        "Created by Guido van Rossum in 1991, it's designed to be clean, readable, and fun to use.\n\n" +
                        "Key Highlights:\n" +
                        "• Named after Monty Python's Flying Circus\n" +
                        "• Created by Guido van Rossum in 1991\n" +
                        "• Known for its simplicity and readability\n" +
                        "• Used in web development, data science, AI, and more!\n\n" +
                        "Python is recognized by its distinctive indented code blocks and clean syntax. Unlike many other " +
                        "programming languages that use symbols like curly braces {} to group code, Python uses indentation, " +
                        "making it naturally readable even for non-programmers.",
                createQuestionsL1_1()));

        allSubLessons.add(new SubLesson("L1.2", "What is a Computer Program?",
                "Understanding Computer Programs 💻\n\n" +
                        "A computer program is a set of instructions that tells a computer exactly what to do. " +
                        "Think of it like a recipe - a step-by-step guide that the computer follows precisely.\n\n" +
                        "Program Characteristics:\n" +
                        "• Sequence of clear, logical steps\n" +
                        "• Tells the computer how to solve a problem\n" +
                        "• Can range from simple calculations to complex applications\n" +
                        "• Written in programming languages like Python\n\n" +
                        "When you write a Python program, you're essentially creating a list of instructions that " +
                        "the computer will execute in order, from top to bottom. These instructions can include displaying " +
                        "text, performing calculations, making decisions, repeating tasks, and much more.",
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
                        "• And so much more!\n\n" +
                        "Python's readability and focus on simplicity follow a philosophy called 'The Zen of Python'. One famous " +
                        "principle from this philosophy is: 'Simple is better than complex.' This approach makes Python both " +
                        "powerful and accessible to programmers of all skill levels.",
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
                        "Examples:\n" +
                        "print(\"Hello, World!\")  # This will display: Hello, World!\n" +
                        "print(42)             # This will display: 42\n\n" +
                        "The print() function is often the first thing beginners learn because it gives immediate feedback. " +
                        "It confirms that your code is running and shows you what's happening inside your program.",
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
                        "print(\"Hello\" + \" World\")  # Prints: HelloWorld\n" +
                        "print(\"Hello\" + \" \" + \"World\")  # Prints: Hello World\n\n" +
                        "In Python, you can use either single (') or double (\") quotes for text, as long as you're consistent. " +
                        "This flexibility is helpful when your text contains quotes, like: print(\"She said, 'Hello!'\")",
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
                        "print(\"Age: \" + str(25))  # Prints: Age: 25\n\n" +
                        "Notice that in the last example, we had to convert the number 25 to a string using str() " +
                        "before we could combine it with other text. This is because Python doesn't automatically " +
                        "convert between numbers and text when using the + operator.",
                createQuestionsL2_3()));

        // Sub-lessons for Lesson 3: Variables
        allSubLessons.add(new SubLesson("L3.1", "Magic Storage Boxes",
                "Understanding Variables as Containers 📦\n\n" +
                        "Variables are like labeled boxes where you can store information for later use in your program. " +
                        "Each variable has a name (the label on the box) and a value (what's inside the box).\n\n" +
                        "Creating Variables:\n" +
                        "name = \"Alex\"    # A string variable\n" +
                        "age = 10        # A number variable\n" +
                        "is_student = True  # A boolean (True/False) variable\n\n" +
                        "Once you create a variable, you can use it anywhere in your program by referring to its name:\n" +
                        "print(name)     # Prints: Alex\n" +
                        "print(age)      # Prints: 10\n" +
                        "print(\"Hello, \" + name)  # Prints: Hello, Alex\n\n" +
                        "Variables save you from having to type the same values repeatedly and make your code more " +
                        "flexible. If you store a value in a variable and use that variable throughout your code, " +
                        "you only need to change it in one place if the value needs to be updated.",
                createQuestionsL3_1()));

        allSubLessons.add(new SubLesson("L3.2", "Naming Our Boxes",
                "Choosing Good Variable Names 🏷️\n\n" +
                        "Variable names are important because they help you understand what your code does. " +
                        "Good variable names describe what information is being stored.\n\n" +
                        "Rules for Variable Names:\n" +
                        "• Can contain letters, numbers, and underscores\n" +
                        "• Must start with a letter or underscore (not a number)\n" +
                        "• Can't use Python's reserved words (like print, if, for)\n" +
                        "• Are case-sensitive (age and Age are different variables)\n\n" +
                        "Good Examples:\n" +
                        "user_name = \"Alex\"  # Uses underscores to separate words\n" +
                        "age = 10           # Short and descriptive\n" +
                        "is_student = True  # Boolean variables often start with 'is_'\n\n" +
                        "Poor Examples:\n" +
                        "a = \"Alex\"     # Not descriptive\n" +
                        "AGE = 10       # ALL CAPS is usually reserved for constants\n" +
                        "isStudent = True  # Mixed case is not typical Python style\n\n" +
                        "Python programmers typically use 'snake_case' (lowercase with underscores) for variable names.",
                createQuestionsL3_2()));

        allSubLessons.add(new SubLesson("L3.3", "Changing What's Inside",
                "Updating Variable Values 🔄\n\n" +
                        "One of the most powerful features of variables is that you can change their values " +
                        "throughout your program. This is what makes variables 'variable'!\n\n" +
                        "Assigning New Values:\n" +
                        "score = 0             # Initial value\n" +
                        "print(score)          # Prints: 0\n" +
                        "score = 10            # Update the value\n" +
                        "print(score)          # Prints: 10\n\n" +
                        "Updating Based on Current Value:\n" +
                        "count = 5\n" +
                        "count = count + 1     # Add 1 to current value\n" +
                        "print(count)          # Prints: 6\n\n" +
                        "Shorthand Operations:\n" +
                        "count += 1            # Same as: count = count + 1\n" +
                        "count -= 2            # Same as: count = count - 2\n" +
                        "count *= 3            # Same as: count = count * 3\n" +
                        "count /= 2            # Same as: count = count / 2\n\n" +
                        "Being able to change variable values makes programs dynamic and interactive, " +
                        "allowing them to respond to user input and changing conditions.",
                createQuestionsL3_3()));

        // Sub-lessons for Lesson 4: Operations
        allSubLessons.add(new SubLesson("L4.1", "Adding with Python",
                "Mathematical Operations in Python ➕\n\n" +
                        "Python can perform various mathematical calculations using operators. " +
                        "These are the symbols that tell Python what operation to perform on values.\n\n" +
                        "Basic Arithmetic Operators:\n" +
                        "• Addition: +\n" +
                        "• Subtraction: -\n" +
                        "• Multiplication: *\n" +
                        "• Division: /\n" +
                        "• Integer Division: // (divides and rounds down)\n" +
                        "• Modulus (remainder): %\n" +
                        "• Exponentiation: **\n\n" +
                        "Examples:\n" +
                        "print(5 + 3)   # Prints: 8\n" +
                        "print(10 - 4)  # Prints: 6\n" +
                        "print(3 * 4)   # Prints: 12\n" +
                        "print(20 / 5)  # Prints: 4.0 (division always returns a float)\n" +
                        "print(20 // 3) # Prints: 6 (integer division, rounds down)\n" +
                        "print(10 % 3)  # Prints: 1 (remainder after division)\n" +
                        "print(2 ** 3)  # Prints: 8 (2 raised to power of 3)\n\n" +
                        "You can combine these operations and use parentheses to control the order of operations, " +
                        "just like in regular math.",
                createQuestionsL4_1()));

        allSubLessons.add(new SubLesson("L4.2", "Subtracting with Python",
                "Comparison Operations in Python ⚖️\n\n" +
                        "Comparison operators let you compare values and determine relationships between them. " +
                        "These operations always result in a boolean value: True or False.\n\n" +
                        "Comparison Operators:\n" +
                        "• Equal to: ==\n" +
                        "• Not equal to: !=\n" +
                        "• Greater than: >\n" +
                        "• Less than: <\n" +
                        "• Greater than or equal to: >=\n" +
                        "• Less than or equal to: <=\n\n" +
                        "Examples:\n" +
                        "print(5 == 5)   # Prints: True\n" +
                        "print(5 != 8)   # Prints: True\n" +
                        "print(10 > 5)   # Prints: True\n" +
                        "print(3 < 2)    # Prints: False\n" +
                        "print(5 >= 5)   # Prints: True\n" +
                        "print(7 <= 3)   # Prints: False\n\n" +
                        "Note: Don't confuse = (assignment operator used for creating variables) with == (comparison operator " +
                        "used for checking equality). This is a common mistake for beginners!",
                createQuestionsL4_2()));

        allSubLessons.add(new SubLesson("L4.3", "Comparing Numbers",
                "Logical Operations in Python 🧠\n\n" +
                        "Logical operators let you combine multiple conditions and create more complex logical expressions. " +
                        "These are essential for making decisions in your programs.\n\n" +
                        "Logical Operators:\n" +
                        "• and: True if both conditions are True\n" +
                        "• or: True if at least one condition is True\n" +
                        "• not: Inverts the condition (True becomes False, False becomes True)\n\n" +
                        "Examples:\n" +
                        "age = 15\n" +
                        "height = 160\n\n" +
                        "# Using and\n" +
                        "print(age > 12 and height > 150)  # Prints: True (both are True)\n" +
                        "print(age > 16 and height > 150)  # Prints: False (first is False)\n\n" +
                        "# Using or\n" +
                        "print(age > 16 or height > 150)   # Prints: True (second is True)\n" +
                        "print(age > 16 or height > 170)   # Prints: False (both are False)\n\n" +
                        "# Using not\n" +
                        "print(not age < 10)  # Prints: True (age is not less than 10)\n\n" +
                        "Logical operators help you create complex conditions that can check multiple factors at once, " +
                        "which is essential for real-world decision-making in your programs.",
                createQuestionsL4_3()));

        // Sub-lessons for Lesson 5: Conditions
        allSubLessons.add(new SubLesson("L5.1", "Making Choices",
                "Introduction to Conditional Statements 🔀\n\n" +
                        "Conditional statements let your program make decisions based on certain conditions. " +
                        "This allows your code to do different things in different situations.\n\n" +
                        "The most basic form is the if statement:\n\n" +
                        "if condition:\n" +
                        "    # Code to run if condition is True\n\n" +
                        "For example:\n" +
                        "age = 15\n" +
                        "if age >= 13:\n" +
                        "    print(\"You can watch PG-13 movies!\")\n\n" +
                        "If the condition (age >= 13) is True, the indented code underneath will run. " +
                        "If the condition is False, the indented code is skipped.\n\n" +
                        "Indentation is very important in Python! The indented lines show which code " +
                        "belongs to the if statement. Python typically uses 4 spaces for each level of indentation.",
                createQuestionsL5_1()));

        allSubLessons.add(new SubLesson("L5.2", "If This Then That",
                "If-Else Statements 🔄\n\n" +
                        "Often, you want your program to do one thing if a condition is True, " +
                        "and something else if it's False. This is where the if-else statement comes in.\n\n" +
                        "Structure:\n" +
                        "if condition:\n" +
                        "    # Code to run if condition is True\n" +
                        "else:\n" +
                        "    # Code to run if condition is False\n\n" +
                        "Example:\n" +
                        "temperature = 32\n" +
                        "if temperature > 30:\n" +
                        "    print(\"It's hot outside!\")\n" +
                        "else:\n" +
                        "    print(\"It's not very hot today.\")\n\n" +
                        "The else block is optional, but very useful. It ensures that your program always " +
                        "does something, regardless of whether the condition is True or False.\n\n" +
                        "You can think of if-else statements as a fork in the road - your program will " +
                        "always take one path or the other, never both at the same time.",
                createQuestionsL5_2()));

        allSubLessons.add(new SubLesson("L5.3", "Yes or No Questions",
                "Multiple Conditions with Elif 🔄🔄\n\n" +
                        "Sometimes you need to check multiple conditions one after another. " +
                        "The elif statement (short for 'else if') helps you do this without nesting multiple if statements.\n\n" +
                        "Structure:\n" +
                        "if condition1:\n" +
                        "    # Code if condition1 is True\n" +
                        "elif condition2:\n" +
                        "    # Code if condition1 is False and condition2 is True\n" +
                        "elif condition3:\n" +
                        "    # Code if condition1 and condition2 are False and condition3 is True\n" +
                        "else:\n" +
                        "    # Code if all conditions are False\n\n" +
                        "Example:\n" +
                        "score = 85\n\n" +
                        "if score >= 90:\n" +
                        "    grade = \"A\"\n" +
                        "elif score >= 80:\n" +
                        "    grade = \"B\"\n" +
                        "elif score >= 70:\n" +
                        "    grade = \"C\"\n" +
                        "elif score >= 60:\n" +
                        "    grade = \"D\"\n" +
                        "else:\n" +
                        "    grade = \"F\"\n\n" +
                        "print(\"Your grade is:\", grade)  # Prints: Your grade is: B\n\n" +
                        "The conditions are checked in order, and only the block of code corresponding to the " +
                        "first True condition is executed. If none of the conditions are True, the else block runs.",
                createQuestionsL5_3()));

        // Sub-lessons for Lesson 6: Loops
        allSubLessons.add(new SubLesson("L6.1", "Doing Things Again and Again",
                "Understanding Loops in Programming 🔁\n\n" +
                        "Loops allow you to repeat a block of code multiple times without copy-pasting. " +
                        "This is essential for tasks like processing lists of data or repeating actions.\n\n" +
                        "Imagine you want to print the numbers 1 through 5. Without loops:\n" +
                        "print(1)\n" +
                        "print(2)\n" +
                        "print(3)\n" +
                        "print(4)\n" +
                        "print(5)\n\n" +
                        "With a loop, you can do this much more efficiently:\n" +
                        "for i in range(1, 6):\n" +
                        "    print(i)\n\n" +
                        "Loops are especially useful when the number of repetitions is large or unknown in advance. " +
                        "They make your code shorter, more readable, and easier to maintain.\n\n" +
                        "Python has two main types of loops:\n" +
                        "• For loops - repeat a specific number of times\n" +
                        "• While loops - repeat until a condition changes\n\n" +
                        "We'll cover each type in detail in the following lessons.",
                createQuestionsL6_1()));

        allSubLessons.add(new SubLesson("L6.2", "Counting with Loops",
                "For Loops in Python 🔢\n\n" +
                        "For loops are used when you know exactly how many times you want to repeat something, " +
                        "or when you want to process each item in a collection.\n\n" +
                        "Basic structure:\n" +
                        "for variable in sequence:\n" +
                        "    # Code to repeat\n\n" +
                        "Examples:\n" +
                        "1. Looping through a range of numbers:\n" +
                        "for i in range(5):  # 0, 1, 2, 3, 4\n" +
                        "    print(i)\n\n" +
                        "2. Looping with a different range:\n" +
                        "for i in range(1, 6):  # 1, 2, 3, 4, 5\n" +
                        "    print(i)\n\n" +
                        "3. Looping with a step value:\n" +
                        "for i in range(0, 10, 2):  # 0, 2, 4, 6, 8\n" +
                        "    print(i)\n\n" +
                        "4. Looping through a list:\n" +
                        "colors = [\"red\", \"green\", \"blue\"]\n" +
                        "for color in colors:\n" +
                        "    print(color)\n\n" +
                        "In each case, the loop variable (i or color) takes on each value in the sequence, " +
                        "and the indented code runs once for each value.",
                createQuestionsL6_2()));

        allSubLessons.add(new SubLesson("L6.3", "Looping Through Items",
                "While Loops and Loop Control 🔄\n\n" +
                        "While loops repeat as long as a condition remains True. They're useful when you don't know " +
                        "in advance how many repetitions you'll need.\n\n" +
                        "Basic structure:\n" +
                        "while condition:\n" +
                        "    # Code to repeat\n\n" +
                        "Example:\n" +
                        "count = 1\n" +
                        "while count <= 5:\n" +
                        "    print(count)\n" +
                        "    count += 1  # Important! Without this, the loop would never end\n\n" +
                        "Always make sure your while loop's condition will eventually become False, " +
                        "or your program will get stuck in an infinite loop!\n\n" +
                        "Special Loop Controls:\n\n" +
                        "1. break - Exit the loop immediately:\n" +
                        "for i in range(10):\n" +
                        "    if i == 5:\n" +
                        "        break  # Exit when i is 5\n" +
                        "    print(i)  # Prints: 0, 1, 2, 3, 4\n\n" +
                        "2. continue - Skip to the next iteration:\n" +
                        "for i in range(5):\n" +
                        "    if i == 2:\n" +
                        "        continue  # Skip when i is 2\n" +
                        "    print(i)  # Prints: 0, 1, 3, 4",
                createQuestionsL6_3()));

        // Sub-lessons for Lesson 7: Arrays (Lists)
        allSubLessons.add(new SubLesson("L7.1", "Collection Baskets",
                "Introduction to Lists in Python 📋\n\n" +
                        "Lists are one of Python's most versatile data structures. They allow you to store " +
                        "multiple items in a single container, keeping them in a specific order.\n\n" +
                        "Creating Lists:\n" +
                        "empty_list = []  # An empty list\n" +
                        "numbers = [1, 2, 3, 4, 5]  # A list of numbers\n" +
                        "fruits = [\"apple\", \"banana\", \"cherry\"]  # A list of strings\n" +
                        "mixed = [1, \"hello\", True, 3.14]  # Lists can contain different data types\n\n" +
                        "Accessing List Items:\n" +
                        "Lists use index numbers to access specific items. Remember that indexing starts at 0!\n\n" +
                        "fruits = [\"apple\", \"banana\", \"cherry\"]\n" +
                        "print(fruits[0])  # Prints: apple (first item)\n" +
                        "print(fruits[1])  # Prints: banana (second item)\n" +
                        "print(fruits[2])  # Prints: cherry (third item)\n\n" +
                        "Negative indices count from the end of the list:\n" +
                        "print(fruits[-1])  # Prints: cherry (last item)\n" +
                        "print(fruits[-2])  # Prints: banana (second-to-last item)\n\n" +
                        "Lists are essential data structures in Python and are used in almost every program.",
                createQuestionsL7_1()));

        allSubLessons.add(new SubLesson("L7.2", "Adding and Removing Items",
                "Modifying Lists in Python ✏️\n\n" +
                        "Lists in Python are mutable, which means you can change them after they're created " +
                        "by adding, removing, or modifying items.\n\n" +
                        "Adding Items:\n" +
                        "fruits = [\"apple\", \"banana\"]\n\n" +
                        "# Add to the end\n" +
                        "fruits.append(\"cherry\")\n" +
                        "print(fruits)  # ['apple', 'banana', 'cherry']\n\n" +
                        "# Insert at a specific position\n" +
                        "fruits.insert(1, \"orange\")\n" +
                        "print(fruits)  # ['apple', 'orange', 'banana', 'cherry']\n\n" +
                        "Removing Items:\n" +
                        "# Remove by value (first occurrence)\n" +
                        "fruits.remove(\"banana\")\n" +
                        "print(fruits)  # ['apple', 'orange', 'cherry']\n\n" +
                        "# Remove by index\n" +
                        "del fruits[0]\n" +
                        "print(fruits)  # ['orange', 'cherry']\n\n" +
                        "# Remove and return the last item\n" +
                        "last_fruit = fruits.pop()\n" +
                        "print(last_fruit)  # 'cherry'\n" +
                        "print(fruits)  # ['orange']\n\n" +
                        "Changing Items:\n" +
                        "fruits = [\"apple\", \"banana\", \"cherry\"]\n" +
                        "fruits[1] = \"orange\"\n" +
                        "print(fruits)  # ['apple', 'orange', 'cherry']",
                createQuestionsL7_2()));

        allSubLessons.add(new SubLesson("L7.3", "Finding Things in Lists",
                "List Operations and Methods 🔍\n\n" +
                        "Python provides many useful operations and methods for working with lists, " +
                        "making it easy to find, sort, and manipulate list data.\n\n" +
                        "Finding Items:\n" +
                        "fruits = [\"apple\", \"banana\", \"cherry\", \"apple\"]\n\n" +
                        "# Check if an item exists\n" +
                        "print(\"banana\" in fruits)  # True\n" +
                        "print(\"orange\" in fruits)  # False\n\n" +
                        "# Find the index of an item (first occurrence)\n" +
                        "print(fruits.index(\"cherry\"))  # 2\n\n" +
                        "# Count occurrences of an item\n" +
                        "print(fruits.count(\"apple\"))  # 2\n\n" +
                        "Sorting and Reversing:\n" +
                        "numbers = [3, 1, 4, 1, 5, 9, 2]\n\n" +
                        "# Sort the list in place\n" +
                        "numbers.sort()\n" +
                        "print(numbers)  # [1, 1, 2, 3, 4, 5, 9]\n\n" +
                        "# Reverse the list in place\n" +
                        "numbers.reverse()\n" +
                        "print(numbers)  # [9, 5, 4, 3, 2, 1, 1]\n\n" +
                        "# Create a sorted copy without modifying the original\n" +
                        "original = [3, 1, 4, 1, 5]\n" +
                        "sorted_list = sorted(original)\n" +
                        "print(original)     # [3, 1, 4, 1, 5] (unchanged)\n" +
                        "print(sorted_list)  # [1, 1, 3, 4, 5] (sorted copy)",
                createQuestionsL7_3()));
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
                List.of("Bird", "Snake", "Comedy show"), 2));
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
                List.of("print[Hello]", "print(\"Hello\")", "print{Hello}"), 1));
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
                List.of("Always needed for text", "Never needed", "Optional"), 0));
        questions.add(new Question("Double vs single quotes in Python:",
                List.of("No functional difference", "Double quotes are required", "Single quotes are for numbers"), 0));
        questions.add(new Question("print(\"Hello\" + \"World\") shows:",
                List.of("Hello World", "HelloWorld", "Error"), 1));
        questions.add(new Question("To add a space between words:",
                List.of("It's automatic", "Add space in quotes", "Can't be done"), 1));
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
        questions.add(new Question("To mix numbers and text with +:",
                List.of("Just add them", "Convert number to string", "Not possible"), 1));
        questions.add(new Question("print() with multiple types:",
                List.of("Not possible", "Requires commas", "Needs special syntax"), 1));
        return questions;
    }

    private List<Question> createQuestionsL3_1() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("Variables are like:",
                List.of("Calculators", "Labeled storage boxes", "Computer programs"), 1));
        questions.add(new Question("To create a variable named age with value 10:",
                List.of("var age = 10", "age = 10", "create age(10)"), 1));
        questions.add(new Question("Once created, variables can be:",
                List.of("Never used again", "Used only once", "Used many times"), 2));
        questions.add(new Question("What happens when you print a variable?",
                List.of("Shows variable name", "Shows stored value", "Deletes the variable"), 1));
        questions.add(new Question("Why use variables?",
                List.of("Makes code more complex", "Stores values for later use", "Makes programs run faster"), 1));
        return questions;
    }

    private List<Question> createQuestionsL3_2() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("Which is a valid variable name?",
                List.of("2name", "user name", "user_name"), 2));
        questions.add(new Question("Variable names in Python are typically written in:",
                List.of("ALL_CAPS", "snake_case", "camelCase"), 1));
        questions.add(new Question("Variable names should be:",
                List.of("Short but not descriptive", "Long and complicated", "Descriptive of what they contain"), 2));
        questions.add(new Question("age and AGE in Python are:",
                List.of("The same variable", "Different variables", "Invalid names"), 1));
        questions.add(new Question("Which is a good variable name?",
                List.of("x", "temp_value_124", "user_score"), 2));
        return questions;
    }

    private List<Question> createQuestionsL3_3() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("Can variables change their value?",
                List.of("Yes", "No", "Only once"), 0));
        questions.add(new Question("count = 5, then count = 10. What is count now?",
                List.of("5", "10", "15"), 1));
        questions.add(new Question("count += 1 is the same as:",
                List.of("count = 1", "count = count + 1", "count - 1"), 1));
        questions.add(new Question("score *= 2 is the same as:",
                List.of("score = score * 2", "score = 2", "score = score + 2"), 0));
        questions.add(new Question("After x = 5 and y = x, changing x will:",
                List.of("Also change y", "Not affect y", "Delete y"), 1));
        return questions;
    }

    private List<Question> createQuestionsL4_1() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What does + do with numbers?",
                List.of("Subtract", "Add", "Multiply"), 1));
        questions.add(new Question("10 * 5 equals:",
                List.of("50", "15", "5"), 0));
        questions.add(new Question("Division (/) in Python always returns:",
                List.of("An integer", "A float (decimal)", "A string"), 1));
        questions.add(new Question("What is 7 // 2?",
                List.of("3.5", "3", "2"), 1));
        questions.add(new Question("What does ** do?",
                List.of("Multiplication", "Addition", "Exponentiation"), 2));
        return questions;
    }

    private List<Question> createQuestionsL4_2() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("Which compares if values are equal?",
                List.of("=", "==", "==="), 1));
        questions.add(new Question("10 != 5 returns:",
                List.of("True", "False", "Error"), 0));
        questions.add(new Question("5 > 5 returns:",
                List.of("True", "False", "5"), 1));
        questions.add(new Question("3 <= 3 returns:",
                List.of("True", "False", "3"), 0));
        questions.add(new Question("Comparison operators always return:",
                List.of("Numbers", "Strings", "True or False values"), 2));
        return questions;
    }

    private List<Question> createQuestionsL4_3() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("a and b is True when:",
                List.of("a is True, b doesn't matter", "a is True and b is True", "a or b is True"), 1));
        questions.add(new Question("a or b is True when:",
                List.of("a is True, b doesn't matter", "a is True and b is True", "Either a or b (or both) is True"), 2));
        questions.add(new Question("not True returns:",
                List.of("True", "False", "Error"), 1));
        questions.add(new Question("(age > 12) and (height > 150) is True when:",
                List.of("Either condition is True", "Both conditions are True", "Neither condition is True"), 1));
        questions.add(new Question("not (a or b) is the same as:",
                List.of("not a and not b", "not a or not b", "a and b"), 0));
        return questions;
    }

    private List<Question> createQuestionsL5_1() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What does an if statement do?",
                List.of("Repeat code", "Make decisions", "Create variables"), 1));
        questions.add(new Question("In Python, the if block is defined by:",
                List.of("Curly braces {}", "Indentation", "Parentheses ()"), 1));
        questions.add(new Question("if age > 18: print(\"Adult\") will run when:",
                List.of("age is 18", "age is greater than 18", "age is less than 18"), 1));
        questions.add(new Question("if True: executes the code block:",
                List.of("Always", "Never", "Sometimes"), 0));
        questions.add(new Question("if statements check if a condition is:",
                List.of("Equal to zero", "A string", "True or False"), 2));
        return questions;
    }

    private List<Question> createQuestionsL5_2() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("When does the else block run?",
                List.of("Always", "When the if condition is True", "When the if condition is False"), 2));
        questions.add(new Question("How many else blocks can an if statement have?",
                List.of("Any number", "Exactly one", "None"), 1));
        questions.add(new Question("if-else statements are similar to:",
                List.of("A fork in a road", "A loop", "A variable"), 0));
        questions.add(new Question("if score > 90: print(\"A\") else: print(\"B\") prints \"B\" when:",
                List.of("score is 90", "score is 91", "score is 89"), 0));
        questions.add(new Question("Are else blocks required with if statements?",
                List.of("Yes", "No", "Only with numbers"), 1));
        return questions;
    }

    private List<Question> createQuestionsL5_3() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What does elif stand for?",
                List.of("else if", "electronic life", "elevate if"), 0));
        questions.add(new Question("How many elif statements can you have?",
                List.of("Only one", "Up to three", "As many as needed"), 2));
        questions.add(new Question("When does an elif block run?",
                List.of("When its condition is True", "When all conditions are True", "When all previous conditions are False and its condition is True"), 2));
        questions.add(new Question("In an if-elif-else structure, how many blocks will execute?",
                List.of("All of them", "At most one", "At least one"), 1));
        questions.add(new Question("Which is correct order?",
                List.of("if, else, elif", "elif, if, else", "if, elif, else"), 2));
        return questions;
    }

    private List<Question> createQuestionsL6_1() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("Loops are used to:",
                List.of("Create variables", "Repeat code", "Delete code"), 1));
        questions.add(new Question("Without loops, repetitive tasks would require:",
                List.of("Less code", "Copy-pasting code", "No code"), 1));
        questions.add(new Question("Which is NOT a type of loop in Python?",
                List.of("for loop", "while loop", "repeat loop"), 2));
        questions.add(new Question("Loops make code more:",
                List.of("Complex and confusing", "Readable and maintainable", "Colorful and pretty"), 1));
        questions.add(new Question("When should you use loops?",
                List.of("For any code", "For code that needs to repeat", "Only for numbers"), 1));
        return questions;
    }

    private List<Question> createQuestionsL6_2() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("In 'for i in range(5):', what values will i take?",
                List.of("1, 2, 3, 4, 5", "0, 1, 2, 3, 4", "0, 1, 2, 3, 4, 5"), 1));
        questions.add(new Question("for color in colors: iterates through:",
                List.of("Each letter in 'colors'", "Each item in the colors list", "Each color that exists"), 1));
        questions.add(new Question("range(1, 6) generates:",
                List.of("1, 2, 3, 4, 5", "1, 2, 3, 4, 5, 6", "1, 3, 5"), 0));
        questions.add(new Question("range(0, 10, 2) generates:",
                List.of("0, 2, 4, 6, 8", "0, 2, 4, 6, 8, 10", "2, 4, 6, 8"), 0));
        questions.add(new Question("For loops are good when:",
                List.of("You don't know how many iterations you need", "You know exactly how many iterations you need", "You never want to stop"), 1));
        return questions;
    }

    private List<Question> createQuestionsL6_3() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("When should you use a while loop?",
                List.of("When you know exactly how many iterations you need", "When you don't know how many iterations you need", "Never"), 1));
        questions.add(new Question("What causes an infinite loop?",
                List.of("A condition that never becomes False", "A condition that is always False", "Using break"), 0));
        questions.add(new Question("What does the break statement do?",
                List.of("Pauses the loop", "Exits the loop immediately", "Restarts the loop"), 1));
        questions.add(new Question("What does the continue statement do?",
                List.of("Exits the loop", "Goes to the next iteration", "Pauses the loop"), 1));
        questions.add(new Question("What's missing? while count < 5: print(count)",
                List.of("break", "count = 0 before the loop", "count += 1 inside the loop"), 2));
        return questions;
    }

    private List<Question> createQuestionsL7_1() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("Lists in Python are created using:",
                List.of("( and )", "[ and ]", "{ and }"), 1));
        questions.add(new Question("An empty list is written as:",
                List.of("()", "[]", "{}"), 1));
        questions.add(new Question("The first element in a list has index:",
                List.of("1", "0", "-1"), 1));
        questions.add(new Question("fruits[-1] refers to:",
                List.of("First item", "Middle item", "Last item"), 2));
        questions.add(new Question("Can lists store different types of data?",
                List.of("Yes", "No", "Only sometimes"), 0));
        return questions;
    }

    private List<Question> createQuestionsL7_2() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("To add an item to the end of a list, use:",
                List.of("append()", "insert()", "add()"), 0));
        questions.add(new Question("fruits.insert(0, \"apple\") adds \"apple\" where?",
                List.of("At the end", "At the beginning", "In the middle"), 1));
        questions.add(new Question("To remove an item by its value, use:",
                List.of("del", "remove()", "pop()"), 1));
        questions.add(new Question("fruits.pop() removes and returns:",
                List.of("First item", "Last item", "Random item"), 1));
        questions.add(new Question("To change the second item in a list:",
                List.of("list(1) = new_value", "list[1] = new_value", "list.change(1, new_value)"), 1));
        return questions;
    }

    private List<Question> createQuestionsL7_3() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("To check if 'apple' is in a list:",
                List.of("list.find('apple')", "'apple' in list", "list.contains('apple')"), 1));
        questions.add(new Question("list.count('apple') returns:",
                List.of("True if 'apple' is in the list", "The index of 'apple'", "How many times 'apple' appears"), 2));
        questions.add(new Question("To sort a list in place, use:",
                List.of("list.sort()", "sorted(list)", "list.order()"), 0));
        questions.add(new Question("To create a sorted copy without modifying the original:",
                List.of("list.sort()", "sorted(list)", "list.copy().sort()"), 1));
        questions.add(new Question("To reverse a list in place:",
                List.of("list.reverse()", "reversed(list)", "list.backwards()"), 0));
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
                List.of("print[Hello]", "print(\"Hello\")", "print{Hello}"), 1));
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

    private List<Question> createQuestionsL6() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What does a loop do?",
                List.of("Stop", "Go Fast", "Repeat"), 2));
        questions.add(new Question("'Clap 3 times', how many claps?",
                List.of("1", "2", "3"), 2));
        questions.add(new Question("Which word means 'repeat'?",
                List.of("End", "Loop", "Start"), 1));
        questions.add(new Question("For loops can count...",
                List.of("Backward only", "Forward only", "Both ways"), 2));
        questions.add(new Question("Loop is like singing...",
                List.of("Once", "Over & Over", "Silently"), 1));
        return questions;
    }

    private List<Question> createQuestionsL7() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What is a list like?",
                List.of("One thing", "Things in order", "Messy pile"), 1));
        questions.add(new Question("[Car,Ball,Doll], first item?",
                List.of("Ball", "Doll", "Car"), 2));
        questions.add(new Question("Lists can hold different types?",
                List.of("Yes", "No", "Only numbers"), 0));
        questions.add(new Question("Shape of list brackets?",
                List.of("( )", "{ }", "[ ]"), 2));
        questions.add(new Question("Which adds to a list?",
                List.of("append()", "remove()", "find()"), 0));
        return questions;
    }

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
            // This is a main lesson - unlock the next main lesson and its first sub-lesson
            String nextLessonId = getNextMainLessonId(lessonId);
            if (nextLessonId != null) {
                // Unlock the next main lesson (by marking it as completed with score 0)
                progressManager.markLessonAsCompleted(nextLessonId, 0);

                // Also unlock the first sub-lesson of the next main lesson
                String firstSubLessonId = nextLessonId + ".1";
                progressManager.markLessonAsCompleted(firstSubLessonId, 0);
            }

            // Show achievement only for main lessons
            achievementManager.checkAndShowAchievement(lessonId, quizScore);
        }

        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        new Handler(Looper.getMainLooper()).postDelayed(this::finish, 500);
    }

    // Helper method to get the next main lesson ID
    private String getNextMainLessonId(String currentId) {
        if (currentId == null) return null;

        // For main lessons
        switch (currentId) {
            case "L1": return "L2";
            case "L2": return "L3";
            case "L3": return "L4";
            case "L4": return "L5";
            case "L5": return "L6";
            case "L6": return "L7";
            case "L7": return null; // No next lesson after L7
            default: return null;
        }
    }

    @Override
    public void finish() {
        super.finish();
    }
}