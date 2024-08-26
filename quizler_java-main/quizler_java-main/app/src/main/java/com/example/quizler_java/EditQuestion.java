package com.example.quizler_java;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class EditQuestion extends AppCompatActivity {
String quiz_id,test_name_id;
int currentIndex = 0;
Cursor cursor;
TextView question,answer,wrong1,wrong2,wrong3;
    String data1,data2,data3,data4,data5,data6;
DatabaseConnection db;
Button update,delete,cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_question);
        db = new DatabaseConnection(EditQuestion.this);
        Intent intent = getIntent();
        quiz_id = intent.getStringExtra("quiz_id");
        test_name_id = intent.getStringExtra("test_name_id");
        question = findViewById(R.id.question);
        answer = findViewById(R.id.answer);
        wrong1 = findViewById(R.id.wrongAnswer1);
        wrong2 = findViewById(R.id.wrongAnswer2);
        wrong3 = findViewById(R.id.wrongAnswer3);
        display_data();

    }
    @SuppressLint("Range")
    private void display_data() {
        DatabaseConnection database = new DatabaseConnection(this);
        SQLiteDatabase db = database.getReadableDatabase();

        //columns ung kukunan na data tpos ung selection ung where quiz_name = blablabla
        String selection = "_id = ?";
        String[] selectionArgs = {quiz_id};
        String[] columns = {"quiz_question", "quiz_correct_answer","quiz_wrong_answer_1",
                "quiz_wrong_answer_2","quiz_wrong_answer_3","retention"};
        Cursor cursor = db.query("quiz_questions", columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToPosition(currentIndex)) {
            data1 = cursor.getString(cursor.getColumnIndex("quiz_question"));
            data2 = cursor.getString(cursor.getColumnIndex("quiz_correct_answer"));
            data3 = cursor.getString(cursor.getColumnIndex("quiz_wrong_answer_1"));
            data4 = cursor.getString(cursor.getColumnIndex("quiz_wrong_answer_2"));
            data5 = cursor.getString(cursor.getColumnIndex("quiz_wrong_answer_3"));
            data6 = cursor.getString(cursor.getColumnIndex("retention"));


            question.setText(data1);
            answer.setText(data2);
            wrong1.setText(data3);
            wrong2.setText(data4);
            wrong3.setText(data5);

        }

        currentIndex++;
    }
    public void cancel_question(View v){
        Intent intent = new Intent(getApplicationContext(), QuestionList.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("test_id",test_name_id);
        startActivity(intent);
        finish();

    }
    public void update_question(View v){

        db.update_question(Integer.parseInt(quiz_id),
                question.getText().toString(),
                answer.getText().toString(),
                wrong1.getText().toString(),
                wrong2.getText().toString(),
                wrong3.getText().toString());
        Intent intent = new Intent(getApplicationContext(), QuestionList.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("test_id",test_name_id);
        startActivity(intent);
        finish();

    }
    public void delete_question(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation")
                .setMessage("Are you sure you want to delete this question?")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.delete_question(quiz_id);
                        Intent intent = new Intent(getApplicationContext(), QuestionList.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("quiz_id",quiz_id);
                        intent.putExtra("test_id",test_name_id);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Perform the action when "Cancel" button is clicked
                        // Add your code here
                    }
                })
                .show();


    }
}