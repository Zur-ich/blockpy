package com.example.BlockPy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.BlockPy.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LessonAdapter.OnLessonClickListener {
    private RecyclerView lessonRecyclerView;
    private LessonAdapter lessonAdapter;
    private List<Lesson> lessonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.BlockPy.R.layout.activity_main);

        lessonRecyclerView = findViewById(R.id.lessonRecyclerView);
        initializeRecyclerView();

        loadLessons();
        lessonAdapter = new LessonAdapter(lessonList, this);
        lessonRecyclerView.setAdapter(lessonAdapter);
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
        Log.d("MainActivity", "Loaded "+lessonList.size()+" lessons.");
    }

    @Override
    public void onLessonClick(int pos) {
        if (lessonList == null || pos < 0 || pos >= lessonList.size()) {
            return;
        }
        Lesson selectedLesson = lessonList.get(pos);
        if (selectedLesson == null || selectedLesson.getId() == null) {
            return;
        }
        Intent intent = new Intent(this, LessonActivity.class);
        intent.putExtra("LESSON_ID", selectedLesson.getId());
        startActivity(intent);
    }
}
