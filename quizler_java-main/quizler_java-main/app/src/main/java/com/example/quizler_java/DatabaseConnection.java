package com.example.quizler_java;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseConnection extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "quiz_app.db";
    private static final int DATABASE_VERSION = 1;

    //for creating the quiz_name table
    private static final String TABLE_NAME = "quiz_list";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_QUIZ_NAME = "quiz_name";

    //for creating the questions and answers table
    private static final String TABLE_NAME_QUESTIONS = "quiz_questions";
    private static final String COLUMN_QUIZ_QUESTION_FOR = "quiz_name";
    private static final String COLUMN_QUIZ_QUESTION = "quiz_question";
    private static final String COLUMN_QUIZ_CORRECT_ANSWER = "quiz_correct_answer";
    private static final String COLUMN_QUIZ_WRONG_1 = "quiz_wrong_answer_1";
    private static final String COLUMN_QUIZ_WRONG_2 = "quiz_wrong_answer_2";
    private static final String COLUMN_QUIZ_WRONG_3 = "quiz_wrong_answer_3";
    private static final String RETENTION = "retention";


    DatabaseConnection(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_QUIZ_NAME + " TEXT)";
        db.execSQL(query);
        String query2 = "CREATE TABLE " + TABLE_NAME_QUESTIONS +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_QUIZ_QUESTION_FOR + " INTEGER, " +
                COLUMN_QUIZ_QUESTION + " TEXT, " +
                COLUMN_QUIZ_CORRECT_ANSWER + " TEXT, " +
                COLUMN_QUIZ_WRONG_1 + " TEXT, " +
                COLUMN_QUIZ_WRONG_2 + " TEXT, " +
                COLUMN_QUIZ_WRONG_3 + " TEXT, " +
                RETENTION  + " INTEGER)";
        db.execSQL(query2);
        Log.d("Databasess", "Table created: " + TABLE_NAME_QUESTIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_QUESTIONS);
        onCreate(db);
    }


    void add_quiz(String quiz_name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_QUIZ_NAME, quiz_name);
        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor read_quiz_name(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;

    }
    Cursor read_quiz_question(String id){
        String query = "SELECT * FROM " + TABLE_NAME_QUESTIONS + " WHERE " + COLUMN_QUIZ_QUESTION_FOR + " = " + Integer.parseInt(id);
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;

    }
    Cursor read_questions(int id){
        Log.d("test222", String.valueOf(id));
        String query = "SELECT * FROM " + TABLE_NAME_QUESTIONS + " WHERE quiz_name = '" + id + "'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            Log.d("test222", "worked");
            cursor = db.rawQuery(query, null);
        }
        return cursor;

    }
    void update_quiz_name(String quiz_name, String id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_QUIZ_NAME,quiz_name);

        long result = db.update(TABLE_NAME,cv, "_id=?", new String[]{id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }
    }
    void delete_quiz(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
        long result2 = db.delete(TABLE_NAME_QUESTIONS, "quiz_name=?", new String[]{row_id});
        if(result2 == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }



    void add_quiz_question(Integer quiz_name_id,String question,String correct,
                           String wrong_1,String wrong_2,String wrong_3, Integer retention){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_QUIZ_QUESTION_FOR, quiz_name_id);
        cv.put(COLUMN_QUIZ_QUESTION, question);
        cv.put(COLUMN_QUIZ_CORRECT_ANSWER, correct);
        cv.put(COLUMN_QUIZ_WRONG_1, wrong_1);
        cv.put(COLUMN_QUIZ_WRONG_2, wrong_2);
        cv.put(COLUMN_QUIZ_WRONG_3, wrong_3);
        cv.put(RETENTION, retention);
        long result = db.insert(TABLE_NAME_QUESTIONS,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }


    void update_question(int row_id, String question, String answer, String wrong1, String wrong2, String wrong3){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_QUIZ_QUESTION, question);
        cv.put(COLUMN_QUIZ_CORRECT_ANSWER, answer);
        cv.put(COLUMN_QUIZ_WRONG_1 , wrong1);
        cv.put(COLUMN_QUIZ_WRONG_2 , wrong2);
        cv.put(COLUMN_QUIZ_WRONG_3 , wrong3);

        long result = db.update(TABLE_NAME_QUESTIONS, cv, "_id=?", new String[]{String.valueOf(row_id)});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();

        }

    }
    void delete_question(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME_QUESTIONS, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    void update_retention(String question_id, int retention){
        int retention2 = retention + 1;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(RETENTION, retention2);


        long result = db.update(TABLE_NAME_QUESTIONS, cv, "_id=?", new String[]{String.valueOf(question_id)});

        if(result == -1){
            //Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            //Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();

        }

    }
}



