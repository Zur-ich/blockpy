package com.example.BlockPy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    private static final String DATABASE_NAME = "blockpy.db";
    private static final int DATABASE_VERSION = 1;

    // Table name
    public static final String TABLE_LESSONS = "lessons";

    // Column names
    public static final String COLUMN_ID = "lesson_id";
    public static final String COLUMN_COMPLETED = "completed";
    public static final String COLUMN_SCORE = "score";
    public static final String COLUMN_COMPLETION_DATE = "completion_date";

    // Create table statement
    private static final String CREATE_TABLE_LESSONS = "CREATE TABLE " + TABLE_LESSONS + "("
            + COLUMN_ID + " TEXT PRIMARY KEY,"
            + COLUMN_COMPLETED + " INTEGER DEFAULT 0,"
            + COLUMN_SCORE + " INTEGER DEFAULT 0,"
            + COLUMN_COMPLETION_DATE + " TEXT DEFAULT NULL"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_LESSONS);
        // Initialize with all lessons as not completed
        initializeLesson(db, "L1"); // First lesson automatically unlocked
        initializeLesson(db, "L2");
        initializeLesson(db, "L3");
        initializeLesson(db, "L4");
        initializeLesson(db, "L5");
    }

    private void initializeLesson(SQLiteDatabase db, String lessonId) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, lessonId);
        values.put(COLUMN_COMPLETED, 0); // 0 = not completed
        values.put(COLUMN_SCORE, 0);
        db.insert(TABLE_LESSONS, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LESSONS);
        // Create tables again
        onCreate(db);
    }

    // Mark a lesson as completed
    // In DatabaseHelper.java
    public boolean markLessonAsCompleted(String lessonId, int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_COMPLETED, 1); // 1 = completed
        values.put(COLUMN_SCORE, score);
        values.put(COLUMN_COMPLETION_DATE, System.currentTimeMillis()); // Current timestamp

        // Add more logging to track the update
        Log.d(TAG, "Attempting to mark lesson as completed: " + lessonId + " with score: " + score);

        int rowsAffected = db.update(TABLE_LESSONS, values, COLUMN_ID + " = ?", new String[]{lessonId});
        Log.d(TAG, "markLessonAsCompleted: " + lessonId + " affected rows: " + rowsAffected);

        // Verify the update worked
        boolean verified = isLessonCompleted(lessonId);
        Log.d(TAG, "Verification after update: Lesson " + lessonId + " is completed: " + verified);

        db.close();
        return rowsAffected > 0;
    }
    // Check if a lesson is completed
    public boolean isLessonCompleted(String lessonId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_LESSONS,
                new String[]{COLUMN_COMPLETED},
                COLUMN_ID + " = ?",
                new String[]{lessonId},
                null, null, null);

        boolean completed = false;
        if (cursor != null && cursor.moveToFirst()) {
            completed = cursor.getInt(0) == 1;
            cursor.close();
        }
        db.close();

        return completed;
    }

    // Get the score for a completed lesson
    public int getLessonScore(String lessonId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_LESSONS,
                new String[]{COLUMN_SCORE},
                COLUMN_ID + " = ?",
                new String[]{lessonId},
                null, null, null);

        int score = 0;
        if (cursor != null && cursor.moveToFirst()) {
            score = cursor.getInt(0);
            cursor.close();
        }
        db.close();

        return score;
    }
}